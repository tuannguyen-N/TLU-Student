package org.example.project.presentations.utils

import java.text.Normalizer

/**
 * Bỏ dấu tiếng Việt + lowercase + trim, dùng để so khớp không phân biệt dấu.
 * Ví dụ: "Thời Khóa Biểu" -> "thoi khoa bieu"
 */
fun String.normalizeVi(): String {
    val decomposed = Normalizer.normalize(this, Normalizer.Form.NFD)
    val noDiacritics = decomposed.replace(Regex("\\p{InCombiningDiacriticalMarks}+"), "")
    return noDiacritics
        .replace('đ', 'd').replace('Đ', 'D')
        .lowercase()
        .trim()
}

/** Levenshtein distance: số phép thêm/xóa/thay ký tự tối thiểu để biến a thành b. */
fun levenshtein(a: String, b: String): Int {
    val dp = Array(a.length + 1) { IntArray(b.length + 1) }
    for (i in 0..a.length) dp[i][0] = i
    for (j in 0..b.length) dp[0][j] = j
    for (i in 1..a.length) {
        for (j in 1..b.length) {
            dp[i][j] = if (a[i - 1] == b[j - 1]) {
                dp[i - 1][j - 1]
            } else {
                1 + minOf(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1])
            }
        }
    }
    return dp[a.length][b.length]
}

/** Điểm tương đồng 0..1 dựa trên Levenshtein (1 = giống hoàn toàn, dùng để chịu lỗi gõ sai). */
fun similarity(a: String, b: String): Double {
    val maxLen = maxOf(a.length, b.length)
    if (maxLen == 0) return 1.0
    return 1.0 - levenshtein(a, b).toDouble() / maxLen
}

/**
 * Chấm điểm 0..1 mức độ khớp giữa câu query người dùng nhập và 1 feature,
 * dựa trên 2 tín hiệu rồi lấy giá trị lớn hơn:
 * 1) containment: query là chuỗi con của keyword (hoặc ngược lại) -> khớp mạnh
 * 2) token similarity: so từng từ trong query với từ gần nhất trong keyword,
 *    cho phép sai chính tả nhẹ (vd "diem damh" vẫn ra "điểm danh")
 */
fun scoreFeature(query: String, feature: SearchableFeature): Double {
    val normQuery = query.normalizeVi()
    if (normQuery.isBlank()) return 0.0

    val queryTokens = normQuery.split(" ").filter { it.isNotBlank() }

    var best = 0.0
    for (keyword in feature.keywords) {
        val normKeyword = keyword.normalizeVi()

        val containsScore =
            if (normKeyword.contains(normQuery) || normQuery.contains(normKeyword)) 0.9 else 0.0

        val keywordTokens = normKeyword.split(" ").filter { it.isNotBlank() }
        val tokenScore = queryTokens.map { qt ->
            keywordTokens.maxOfOrNull { kt -> similarity(qt, kt) } ?: 0.0
        }.average()

        val combined = maxOf(containsScore, tokenScore)
        if (combined > best) best = combined
    }
    return best
}