package com.techlads.muzika.features.presentation.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.techlads.muzika.features.presentation.components.MediumSpacer
import com.techlads.muzika.features.presentation.components.TopBarWithNav

@Composable
fun PlayerScreen(
    onNavigationClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary)
        ) {

            TopBarWithNav(
                title = "Playing Now",
                onNavigationClick = onNavigationClick,
                onFilterClick = { },
                modifier = Modifier.fillMaxWidth()
            )
            MediumSpacer()
        }
    }
}