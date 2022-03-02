package com.keelim.free.ui.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.keelim.free.R
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    isLoading: Boolean,
    onTimeout: () -> Unit
) {
    val currentOnTimeout by rememberUpdatedState(onTimeout)

    SplashScreen(isLoading = isLoading)

    if (!isLoading) {
        LaunchedEffect(true) {
            delay(500L)
            currentOnTimeout()
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean
) {
    Box(modifier = modifier.fillMaxSize()) {
        Text(
            text = "URLS",
            modifier = Modifier.align(Alignment.Center),
            color = Color(R.color.main_color),
            fontWeight = FontWeight.Bold,
            fontSize = 50.sp
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(alignment = Alignment.BottomCenter)
        ) {
            Text(

                text = "똑똑하게 정보를 저장하는 방법",
                color = Color.Black,
                fontSize = 10.sp,
            )

            Spacer(modifier = Modifier.height(140.dp))

            Text(
                text = if (isLoading) {
                    "준비중이야... 기다려..."
                } else {
                    "다됐어... 이제 갈거야..."
                },
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            AnimatedVisibility(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                visible = isLoading
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp),
                    color = MaterialTheme.colors.secondary,
                    strokeWidth = ProgressIndicatorDefaults.StrokeWidth * 1.5f
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }

        Text(
            text = "Team Eagle Eyes",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 8.sp,
        )
    }
}

@Preview(
    widthDp = 320,
    heightDp = 640
)
@Composable
fun PreviewIntroUi() {
    SplashScreen(isLoading = true)
}