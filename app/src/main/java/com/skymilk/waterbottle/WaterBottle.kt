package com.skymilk.waterbottle

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun WaterBottle(
    modifier: Modifier = Modifier,
    totalWaterAmount: Int, //물 최대 양
    drinkWaterAmount: Int, //현재 마신 물의 양
    unit: String, //물 용량 단위
    waterColor: Color = Color(0xff279eff), // 물 색상
    bottleColor: Color = Color.White, // 물병 색상
    capColor: Color = Color(0xff0065b9) // 물병 뚜껑 색상
) {
    //최대 용량 대비 마신 물의 비율
    val waterPercentage by animateFloatAsState(
        targetValue = drinkWaterAmount.toFloat() / totalWaterAmount.toFloat(),
        label = "마신 물의 비율",
        animationSpec = tween(durationMillis = 1000)
    )
    //마신 물의 양 증가 애니메이션
    val drinkWaterAmountAnimation by animateIntAsState(
        targetValue = drinkWaterAmount,
        label = "마신 물 실시간 상승 효과",
        animationSpec = tween(durationMillis = 1000)
    )

    Box(
        modifier = modifier
            .width(200.dp)
            .height(600.dp)
    ) {
        Canvas(modifier = modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            val capWidth = width * 0.5f
            val capHeight = height * 0.1f

            //물병 그리기 패스
            val bottleBodyPath = Path().apply {
                //물병 좌측 라인
                //==========================================
                //병 좌측 상단 부분 뚜껑과 맞닿는 곳 시작
                moveTo(
                    x = width * 0.3f, y = height * 0.1f
                )

                //병 목 라인
                lineTo(
                    x = width * 0.3f, y = height * 0.2f
                )

                //병 어깨? 곡선
                quadraticBezierTo(
                    x1 = 0f, y1 = height * 0.3f,
                    x2 = 0f, y2 = height * 0.4f
                )

                //병 바디 라인
                lineTo(
                    x = 0f, y = height * 0.95f
                )

                //병 바닥 곡선 형태
                quadraticBezierTo(
                    x1 = 0f, y1 = height,
                    x2 = width * 0.05f, y2 = height
                )
                //==========================================

                //병 밑바닥 라인
                lineTo(
                    x = width * 0.95f, y = height
                )

                //물병 우측 라인
                //==========================================
                //병 우측 하단 부분 곡선 형태부터 시작
                //병 바닥 곡선 형태
                quadraticBezierTo(
                    x1 = width, y1 = height,
                    x2 = width, y2 = height * 0.95f
                )

                //병 바디 라인
                lineTo(
                    x = width, y = height * 0.4f
                )

                //병 어깨? 곡선
                quadraticBezierTo(
                    x1 = width, y1 = height * 0.3f,
                    x2 = width * 0.7f, y2 = height * 0.2f
                )

                //병 목 라인
                lineTo(
                    x = width * 0.7f, y = height * 0.1f
                )
                //==========================================

                //완료
                close()
            }

            //물병 그리기
            clipPath(bottleBodyPath) {
                drawRect(
                    color = bottleColor,
                    size = size,
                )

                //현재 마신 물의 높이 위치
                val waterWavesYPosition = (1 - waterPercentage) * size.height

                //마신 물의 양 채워 그리기
                val waterPath = Path().apply {
                    //좌측 상단 물 그리기 시작
                    moveTo(
                        x = 0f, y = waterWavesYPosition
                    )

                    //마신 물 우측 상단
                    lineTo(
                        x = size.width,
                        y = waterWavesYPosition
                    )

                    //마신 물 우측 하단
                    lineTo(
                        x = size.width,
                        y = size.height
                    )

                    //마신 물 좌측 하단
                    lineTo(
                        x = 0f,
                        y = size.height
                    )
                    close()
                }

                //마신 물 그리기
                drawPath(
                    path = waterPath,
                    color = waterColor
                )
            }

            //물병 뚜껑 그리기
            drawRoundRect(
                color = capColor,
                size = Size(width = capWidth, height = capHeight),
                topLeft = Offset(size.width / 2 - capWidth / 2f, 0f), // 물병 중앙에 위치할 수 있도록
                cornerRadius = CornerRadius(45f, 45f)
            )
        }

        //마신 물 텍스트 속성 설정
        val drinkText = buildAnnotatedString {
            //마신 물의 양 텍스트 표시
            withStyle(
                style = SpanStyle(
                    color = if (waterPercentage > 0.5f) bottleColor else waterColor,
                    fontSize = 40.sp
                )
            ) {
                append(drinkWaterAmountAnimation.toString())
            }

            //단위 추가(ml, l 등)
            withStyle(
                style = SpanStyle(
                    color = if (waterPercentage > 0.5f) bottleColor else waterColor,
                    fontSize = 20.sp
                )
            ) {
                append(" ")
                append(unit)
            }
        }

        //텍스트 중앙에 추가
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = drinkText)
        }
    }
}