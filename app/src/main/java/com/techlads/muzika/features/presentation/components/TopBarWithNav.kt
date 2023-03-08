package com.techlads.muzika.features.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.techlads.muzika.R

@Composable
fun TopBarWithNav(
    title: String = "",
    color: Color = MaterialTheme.colors.primary,
    onMenuClick: () -> Unit,
    onFilterClick: () -> Unit,
    showFilterIcon: Boolean = false,
    modifier: Modifier
) {

    TopAppBar(
        backgroundColor = color,
        elevation = 0.dp,
        modifier = modifier
            .fillMaxWidth()
            .zIndex(1f)
    ) {
        Box(modifier.height(40.dp)) {

            //Navigation Icon
            Row(verticalAlignment = Alignment.CenterVertically) {
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.high,
                ) {
                    IconButton(
                        onClick = onMenuClick,
                        enabled = true,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_menu),
                            contentDescription = "menu",
                            tint = MaterialTheme.colors.secondary,
                        )
                    }
                }
            }

            //Title
            Row(
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically) {

                ProvideTextStyle(value = MaterialTheme.typography.h6) {
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.high,
                    ){
                        Text(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 80.dp),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.secondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            text = title
                        )
                    }
                }
            }

            if (showFilterIcon) {
                Row(modifier = Modifier.align(Alignment.CenterEnd),
                    verticalAlignment = Alignment.CenterVertically) {
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.high,
                    ) {
                        IconButton(
                            onClick = onFilterClick,
                            enabled = true,
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_adjust_horizontal),
                                contentDescription = "search",
                                tint = MaterialTheme.colors.secondary,
                            )
                        }
                    }
                }
            }
        }
    }
}