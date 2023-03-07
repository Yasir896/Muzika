package com.techlads.muzika.features.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.R
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
import com.techlads.muzika.R.*

@Composable
fun HomeTopBar(
    color: Color = MaterialTheme.colors.primary,
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier
) {
    TopAppBar(
        backgroundColor = color,
        elevation = 0.dp,
        modifier = modifier
            .fillMaxWidth()
            .zIndex(1f)
    ) {

        //TopAppBar Content
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
                            painter = painterResource(id = drawable.ic_menu),
                            contentDescription = "menu",
                            tint = MaterialTheme.colors.secondary,
                        )
                    }
                }
            }

            Row(modifier = Modifier.align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically) {
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.high,
                ) {
                    IconButton(
                        onClick = onMenuClick,
                        enabled = true,
                    ) {
                        Icon(
                            painter = painterResource(id = drawable.ic_search),
                            contentDescription = "search",
                            tint = MaterialTheme.colors.secondary,
                        )
                    }
                }
            }
        }
    }
}