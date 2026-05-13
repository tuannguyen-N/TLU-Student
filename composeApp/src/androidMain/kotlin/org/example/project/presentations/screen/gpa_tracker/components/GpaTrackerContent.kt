package org.example.project.presentations.screen.gpa_tracker.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.presentations.components.LabelView
import org.example.project.presentations.theme.LocalExtendedColors
import org.example.project.presentations.utils.gpaToAcademicRank
import org.example.project.presentations.utils.toColor

data class GpaSemester(val label: String, val gpa: Double)

@Composable
fun GpaTrackerContent(
    modifier: Modifier = Modifier,
    currentGpa: Double = 3.82,
    gpaDelta: Double = 0.15,
    highestGpa: Double = 3.95,
    highestSemester: String = "Kv 2 2023",
    lowestGpa: Double = 3.40,
    lowestSemester: String = "Kv 1 2022",
    semesterData: List<GpaSemester>
) {
    val color = LocalExtendedColors.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        CurrentGpaCard(currentGpa, gpaDelta)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            GpaStatCard(
                modifier = Modifier.weight(1f),
                title = "GPA Cao Nhất",
                value = highestGpa,
                semester = highestSemester,
                isHighest = true
            )
            GpaStatCard(
                modifier = Modifier.weight(1f),
                title = "GPA Thấp Nhất",
                value = lowestGpa,
                semester = lowestSemester,
            )
        }

        TrendCard(semesterData)
    }
}

@Composable
private fun CurrentGpaCard(gpa: Double, delta: Double) {
    val color = LocalExtendedColors.current
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color.white),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = "GPA HIỆN TẠI",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = color.gray,
                    letterSpacing = 0.8.sp,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "%.2f".format(gpa),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = gpa.gpaToAcademicRank().toColor(),
                )
            }

            val isIncrease = delta > 0
            val isDecrease = delta < 0

            val sign = when {
                isIncrease -> "+"
                isDecrease -> "-"
                else -> ""
            }

            val arrow = when {
                isDecrease -> "↘"
                else -> "↗"
            }

            val backgroundColor = when {
                isIncrease -> color.green.copy(alpha = 0.1f)
                isDecrease -> color.red.copy(alpha = 0.1f)
                else -> color.orange.copy(alpha = 0.1f)
            }

            val textColor = when {
                isIncrease -> color.green
                isDecrease -> color.red
                else -> color.orange
            }

            LabelView(
                text = "$arrow $sign${"%.2f".format(kotlin.math.abs(delta))}",
                backgroundColor = backgroundColor,
                textColor = textColor
            )
        }
    }
}

@Composable
private fun GpaStatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: Double,
    semester: String,
    isHighest: Boolean = false,
) {
    val color = LocalExtendedColors.current
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color.white),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                fontSize = 13.sp,
                color = color.gray,
                fontWeight = FontWeight.Medium,
            )
            Spacer(Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "%.2f".format(value),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isHighest) LocalExtendedColors.current.green else LocalExtendedColors.current.blackBackground,
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = semester,
                    fontSize = 12.sp,
                    color = color.grayNavy,
                    modifier = Modifier.padding(bottom = 3.dp),
                )
            }
        }
    }
}

@Composable
private fun TrendCard(data: List<GpaSemester>) {
    val color = LocalExtendedColors.current
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color.white),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Xu hướng GPA",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1C1C1E),
                )
                Text(
                    text = "${data.size} Học kỳ gần nhất",
                    fontSize = 13.sp,
                    color = color.gray,
                )
            }

            Spacer(Modifier.height(16.dp))

            AnimatedLineChart(
                data = data,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
            )

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                data.forEach { sem ->
                    Text(
                        text = sem.label,
                        fontSize = 11.sp,
                        color = color.grayNavy,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

@Composable
private fun AnimatedLineChart(
    data: List<GpaSemester>,
    modifier: Modifier = Modifier,
) {
    val color = LocalExtendedColors.current
    val progress = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1200, easing = EaseInOutCubic),
        )
    }

    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    var tooltipJob by remember { mutableStateOf<Job?>(null) }
    val scope = rememberCoroutineScope()
    val pointsRef = remember { mutableStateOf<List<Offset>>(emptyList()) }

    val yAxisLabels = listOf("4.0", "3.0", "2.0", "1.0", "0.0")
    val yAxisWidth = 32.dp

    Row(modifier = modifier) {
        Column(
            modifier = Modifier
                .width(yAxisWidth)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            yAxisLabels.forEach { label ->
                Text(
                    text = label,
                    fontSize = 10.sp,
                    color = color.gray,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(data) {
                        detectTapGestures { tapOffset ->
                            val points = pointsRef.value
                            val touchRadius = 24.dp.toPx()
                            val hit = points.indexOfFirst { pt ->
                                (tapOffset - pt).getDistance() <= touchRadius
                            }
                            if (hit >= 0) {
                                selectedIndex = hit
                                tooltipJob?.cancel()
                                tooltipJob = scope.launch {
                                    delay(2000)
                                    selectedIndex = null
                                }
                            }
                        }
                    }
            ) {
                if (data.isEmpty()) return@Canvas

                val minGpa = 0f
                val maxGpa = 4f
                val rangeGpa = maxGpa - minGpa

                val paddingTop = 6.dp.toPx()
                val paddingBottom = 6.dp.toPx()
                val drawHeight = size.height - paddingTop - paddingBottom

                val stepX = if (data.size > 1) size.width / (data.size - 1).toFloat() else 0f

                fun xOf(i: Int) = if (data.size > 1) i * stepX else size.width / 2
                fun yOf(gpa: Float) = paddingTop + drawHeight * (1f - (gpa - minGpa) / rangeGpa)

                val points = data.mapIndexed { i, sem -> Offset(xOf(i), yOf(sem.gpa.toFloat())) }
                pointsRef.value = points

                val gridSteps = 4
                for (s in 0..gridSteps) {
                    val y = paddingTop + drawHeight * s / gridSteps
                    drawLine(
                        color = Color(0xFFE5E5EA),
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = 1.dp.toPx(),
                    )
                }

                val clipRight = size.width * progress.value

                if (data.size >= 2) {
                    val areaPath = Path().apply {
                        moveTo(points[0].x, points[0].y)
                        points.drop(1).forEach { lineTo(it.x, it.y) }
                        lineTo(points.last().x, size.height)
                        lineTo(points.first().x, size.height)
                        close()
                    }

                    clipRect(right = clipRight) {
                        drawPath(
                            path = areaPath,
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    color.mainRed.copy(alpha = 0.18f),
                                    Color.Transparent
                                ),
                                startY = paddingTop,
                                endY = size.height,
                            ),
                        )
                    }

                    val linePath = Path().apply {
                        moveTo(points[0].x, points[0].y)
                        points.drop(1).forEach { lineTo(it.x, it.y) }
                    }

                    clipRect(right = clipRight) {
                        drawPath(
                            path = linePath,
                            color = color.mainRed,
                            style = Stroke(
                                width = 2.5.dp.toPx(),
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round
                            ),
                        )
                    }
                }

                points.forEachIndexed { i, pt ->
                    val pointProgress = if (data.size > 1) i / (data.size - 1).toFloat() else 0f
                    if (progress.value >= pointProgress) {
                        val isSelected = selectedIndex == i
                        if (isSelected) {
                            drawCircle(
                                color = color.mainRed.copy(alpha = 0.2f),
                                radius = 12.dp.toPx(),
                                center = pt,
                            )
                        }
                        drawCircle(color = color.white, radius = 6.dp.toPx(), center = pt)
                        drawCircle(
                            color = color.mainRed,
                            radius = if (isSelected) 6.dp.toPx() else 5.dp.toPx(),
                            center = pt,
                        )
                    }
                }
            }

            selectedIndex?.let { idx ->
                val points = pointsRef.value
                if (idx < points.size) {
                    val pt = points[idx]
                    val sem = data[idx]

                    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                        val canvasWidthPx = constraints.maxWidth.toFloat()
                        val canvasHeightPx = constraints.maxHeight.toFloat()

                        val xDp = (pt.x / canvasWidthPx * maxWidth.value).dp
                        val yDp = (pt.y / canvasHeightPx * maxHeight.value).dp

                        val tooltipWidthDp = 72.dp
                        val tooltipHeightDp = 40.dp
                        val arrowSizeDp = 6.dp

                        val clampedX = xDp
                            .coerceAtLeast(tooltipWidthDp / 2)
                            .coerceAtMost(maxWidth - tooltipWidthDp / 2)

                        val offsetY = yDp - tooltipHeightDp - arrowSizeDp - 4.dp

                        Column(
                            modifier = Modifier
                                .offset(
                                    x = clampedX - tooltipWidthDp / 2,
                                    y = offsetY.coerceAtLeast(0.dp),
                                )
                                .width(tooltipWidthDp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = color.mainRed,
                                shadowElevation = 4.dp,
                            ) {
                                Column(
                                    modifier = Modifier.padding(
                                        horizontal = 10.dp,
                                        vertical = 6.dp
                                    ),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Text(
                                        text = "%.2f".format(sem.gpa),
                                        fontSize = 15.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                            }

                            Canvas(modifier = Modifier.size(arrowSizeDp * 2, arrowSizeDp)) {
                                val path = Path().apply {
                                    moveTo(0f, 0f)
                                    lineTo(size.width, 0f)
                                    lineTo(size.width / 2, size.height)
                                    close()
                                }
                                drawPath(path, color = color.mainRed)
                            }
                        }
                    }
                }
            }
        }
    }
}