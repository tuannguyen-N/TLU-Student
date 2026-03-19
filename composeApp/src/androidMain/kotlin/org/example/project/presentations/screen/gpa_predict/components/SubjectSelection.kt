package org.example.project.presentations.screen.gpa_predict.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.domain.model.SubjectItem
import org.example.project.domain.model.SubjectScore
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun SubjectListSection(
    modifier: Modifier = Modifier,
    subjects: List<SubjectItem>,
    scores: Map<String, SubjectScore>,
    failedSubjects: List<String>,
    popupSubjectCode: String?,
    onTogglePopup: (String?) -> Unit,
    onMidtermChange: (String, String) -> Unit,
    onFinalChange: (String, String) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SubjectSectionHeader(subjectCount = subjects.size)

        subjects.forEach { subject ->
            val score = scores[subject.code] ?: SubjectScore()
            SubjectCard(
                subject = subject,
                subjectScore = score,
                isFailed = subject.code in failedSubjects,
                showPopup = popupSubjectCode == subject.code,
                onTogglePopup = { onTogglePopup(subject.code) },
                onMidtermChange = { onMidtermChange(subject.code, it) },
                onFinalChange = { onFinalChange(subject.code, it) },
            )
        }
    }
}

@Composable
fun SubjectCard(
    modifier: Modifier = Modifier,
    subject: SubjectItem,
    subjectScore: SubjectScore,
    isFailed: Boolean = false,
    showPopup: Boolean,
    onMidtermChange: (String) -> Unit,
    onFinalChange: (String) -> Unit,
    onTogglePopup: () -> Unit
) {
    val color = LocalExtendedColors.current

    Card(
        shape = RoundedCornerShape(14.dp),
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = color.white),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = subject.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = color.textPrimary,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 10.dp)
                )

                if (isFailed) {
                    Box {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = null,
                            tint = color.red,
                            modifier = Modifier
                                .size(16.dp)
                                .clickable { onTogglePopup() }
                        )

                        if (showPopup) {
                            PopupAlertGpa(onDismiss = { onTogglePopup() })
                        }
                    }
                }
            }

            Text(
                text = "${subject.credits} TÍN CHỈ • MÃ: ${subject.code}",
                style = MaterialTheme.typography.labelMedium,
                color = color.gray
            )

            Spacer(Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ScoreInputField(
                    label = "ĐIỂM GIỮA KỲ",
                    value = subjectScore.midterm,
                    onValueChange = {
                        onMidtermChange(it)
                    },
                    modifier = Modifier.weight(1f)
                )
                ScoreInputField(
                    label = "ĐIỂM CUỐI KỲ",
                    value = subjectScore.final,
                    onValueChange = {
                        onFinalChange(it)
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun ScoreInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val color = LocalExtendedColors.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.5.sp
            ),
            color = color.grayNavy
        )

        Spacer(Modifier.height(8.dp))

        BasicTextField(
            value = value,
            onValueChange = { input ->
                if (input.isEmpty() || input.matches(Regex("^\\d{0,2}(\\.\\d?)?$"))) {
                    onValueChange(input)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            textStyle = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = color.red
            ),
            decorationBox = { innerTextField ->
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = color.background,
                    border = BorderStroke(1.dp, color.grayNavy.copy(alpha = 0.15f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        if (value.isEmpty() && !isFocused) {
                            Text(
                                text = "0.0",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                color = color.gray
                            )
                        }
                        innerTextField()
                    }
                }
            }
        )
    }
}