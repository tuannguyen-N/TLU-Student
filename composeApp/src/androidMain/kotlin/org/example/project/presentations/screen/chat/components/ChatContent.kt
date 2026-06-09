package org.example.project.presentations.screen.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mikepenz.markdown.m3.Markdown
import com.mikepenz.markdown.m3.markdownColor
import com.mikepenz.markdown.m3.markdownTypography
import org.example.project.R
import org.example.project.domain.model.ChatMessage
import org.example.project.presentations.components.StatusBarStyle
import org.example.project.presentations.screen.chat.ChatState
import org.example.project.presentations.theme.LocalExtendedColors


@Composable
fun ChatContent(
    uiState: ChatState,
    onPromptChange: (String) -> Unit,
    onSendClick: () -> Unit,
    onBack: () -> Unit = {}
) {
    val color = LocalExtendedColors.current
    val listState = rememberLazyListState()

    LaunchedEffect(uiState.messages.size) {
        if (uiState.messages.isNotEmpty()) {
            listState.animateScrollToItem(0)
        }
    }

    StatusBarStyle(darkIcons = true)

    Box(
        modifier = Modifier
            .statusBarsPadding()
            .imePadding()
            .background(color.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (uiState.messages.isEmpty()) {
                Text(
                    text = "Xin chào,\nTôi có thể giúp gì cho bạn ?",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(15.dp),
                    style = MaterialTheme.typography.titleLarge,
                    color = LocalExtendedColors.current.mainBlue
                )
            }

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp),
                reverseLayout = true
            ) {
                item {
                    if (uiState.isLoading) {
                        TypingIndicator(modifier = Modifier.padding(15.dp))
                    }
                }

                items(uiState.messages.reversed()) { message ->
                    MessageItem(message)
                }
            }

            MessageInputField(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(15.dp),
                text = uiState.prompt,
                onTextChange = onPromptChange,
                onSendClick = {
                    onSendClick()
                },
                enabled = !uiState.isLoading
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 10.dp)
                .align(Alignment.TopStart)
        ) {
            IconButton(onClick = onBack) {
                Box(
                    modifier = Modifier
                        .background(color.white, shape = CircleShape)
                        .size(40.dp)
                )
                Icon(
                    painter = painterResource(R.drawable.icon_back),
                    contentDescription = null,
                    tint = color.blackBackground
                )
            }
        }
    }
}

@Composable
fun MessageItem(message: ChatMessage) {
    val color = LocalExtendedColors.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 4.dp),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        if (message.text.isEmpty()) return@Row
        Box(
            modifier = Modifier
                .padding(
                    start = if (message.isUser) 10.dp else 0.dp,
                    end = if (!message.isUser) 10.dp else 0.dp
                )
                .background(
                    color = if (message.isUser) color.mainBlue else color.white,
                    shape = RoundedCornerShape(18.dp)
                )
                .padding(vertical = 10.dp, horizontal = 15.dp)
        ) {
            if (message.isUser) {
                Text(
                    text = message.text,
                    fontSize = 16.sp,
                    color = color.white,
                )
            } else {
                Markdown(
                    content = message.text,
                    colors = markdownColor(
                        text = color.blackBackground,
                        codeBackground = color.background
                    ),
                    typography = markdownTypography(
                        text = androidx.compose.ui.text.TextStyle(fontSize = 16.sp)
                    ),
                )
            }
        }
    }
}

@Preview
@Composable
fun TypingIndicator(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.typing_indicator)
    )
    Box(
        modifier = modifier.size(50.dp)
    ) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .fillMaxSize()
                .scale(2.5f)
        )
    }
}