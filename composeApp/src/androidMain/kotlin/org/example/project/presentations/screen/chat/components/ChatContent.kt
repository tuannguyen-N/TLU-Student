package org.example.project.presentations.screen.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.example.project.R
import org.example.project.domain.model.ChatMessage
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun ChatContent(
    onBack: () -> Unit = {}
) {
    val color = LocalExtendedColors.current
    val messages = remember {
        mutableStateListOf(
            ChatMessage("Hello 👋", false),
            ChatMessage("Hi AI", true)
        )
    }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = color.background,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 10.dp)
                    .statusBarsPadding()
            ) {
                IconButton(onClick = onBack) {
                    Box(
                        modifier = Modifier
                            .background(color.white, shape = CircleShape)
                            .fillMaxSize()
                    )
                    Icon(
                        painter = painterResource(R.drawable.icon_back),
                        contentDescription = null,
                        tint = color.blackBackground
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp)
                    .imePadding(),
                reverseLayout = true
            ) {
                items(messages.reversed()) { message ->
                    MessageItem(message)
                }
            }

            MessageInputField(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .imePadding()
                    .padding(15.dp),
                onSendClick = { text ->
                    messages.add(ChatMessage(text, true))
                    scope.launch {
                        listState.animateScrollToItem(0)
                    }
                }
            )
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
        Box(
            modifier = Modifier
                .background(
                    color = if (message.isUser) color.mainBlue else color.white,
                    shape = RoundedCornerShape(18.dp)
                )
                .padding(vertical = 10.dp, horizontal = 15.dp)
        ) {
            Text(
                text = message.text,
                fontSize = 16.sp,
                color = if (message.isUser) color.white else color.blackBackground
            )
        }
    }
}