package org.example.project.presentations.screen.news.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.presentations.components.TopScreenBar
import org.example.project.presentations.screen.news.NewsState
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun NewsContent(
    uiState: NewsState, onOpenNews: (String) -> Unit, onBack: () -> Unit
) {
    val color = LocalExtendedColors.current

    Scaffold(
        containerColor = color.background, contentWindowInsets = WindowInsets(0), topBar = {
            TopScreenBar<String>(
                title = "Tin tức và sự kiện",
                onBack = onBack,
                contentColor = color.blackBackground,
                backgroundColor = color.white
            )
        }) { innerPadding ->
        if (uiState.isLoading) {
            NewsLayout(innerPadding = innerPadding, topContent = {
                FeaturedNewsCardShimmer()
            }, middleContent = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SmallNewsCardShimmer(
                        modifier = Modifier
                            .weight(5f)
                            .fillMaxHeight()
                    )

                    Column(
                        modifier = Modifier
                            .weight(6f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        SmallNewsCardShimmer(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        )
                        SmallNewsCardShimmer(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        )
                    }
                }
            }, bottomContent = {
                FeaturedNewsCardShimmer()
            }, listContent = {}
            )
        } else {
            val news = uiState.news
            if (news.isNotEmpty()) {
                NewsLayout(innerPadding = innerPadding, topContent = {
                    FeaturedNewsCard(
                        item = news.first(), onClick = onOpenNews, color = color
                    )
                }, middleContent = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        SmallNewsCard(
                            color = color,
                            item = news[1],
                            modifier = Modifier
                                .weight(5f)
                                .fillMaxHeight(),
                            onClick = onOpenNews
                        )

                        Column(
                            modifier = Modifier
                                .weight(6f)
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            SmallNewsCard(
                                color = color,
                                item = news[2],
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                onClick = onOpenNews
                            )
                            SmallNewsCard(
                                color = color,
                                item = news[3],
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                onClick = onOpenNews
                            )
                        }
                    }
                }, bottomContent = {
                    FeaturedNewsCard(item = news[4], onClick = onOpenNews, color = color)
                }, listContent = {
                    val remainingNews = if (news.size > 5) news.drop(5) else emptyList()
                    items(remainingNews.chunked(2)) { rowItems ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            rowItems.forEach { item ->
                                SmallNewsCard(
                                    color = color,
                                    item = item,
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f),
                                    onClick = onOpenNews
                                )
                            }

                            if (rowItems.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                })
            }
        }
    }
}

@Composable
private fun NewsLayout(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    topContent: @Composable () -> Unit,
    middleContent: @Composable () -> Unit,
    bottomContent: @Composable () -> Unit,
    listContent: LazyListScope.() -> Unit
) {
    LazyColumn(
        modifier = modifier
            .padding(innerPadding)
            .fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { topContent() }
        item { middleContent() }
        item { bottomContent() }

        listContent()
    }
}