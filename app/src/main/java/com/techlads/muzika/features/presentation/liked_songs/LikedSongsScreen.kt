package com.techlads.muzika.features.presentation.liked_songs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techlads.muzika.features.presentation.HomeTopBar
import com.techlads.muzika.features.presentation.components.MediumSpacer
import com.techlads.muzika.features.presentation.components.TopBarWithNav

@Composable
fun LikedSongsScreen() {
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
                onMenuClick = {  },
                onFilterClick = {  },
                showFilterIcon = true,
                modifier = Modifier.fillMaxWidth()
            )
            MediumSpacer()

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Liked Songs",
                fontSize = 24.sp,
                fontWeight = FontWeight.W700,
            )
        }
    }
}