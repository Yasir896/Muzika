package com.techlads.muzika.features.presentation.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.techlads.muzika.R
import com.techlads.muzika.features.presentation.components.MediumSpacer
import com.techlads.muzika.features.presentation.components.PlayerSlider
import com.techlads.muzika.features.presentation.components.TopBarWithNav

@ExperimentalPagerApi
@Composable
fun PlayerScreen(
    onNavigationClick: () -> Unit
) {

    val sliderList = arrayOf(
        R.drawable.believer,
        R.drawable.card_placeholder,
        R.drawable.moment_apart,
        R.drawable.believer,
        R.drawable.card_placeholder,

        )

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

            PlayerSlider(sliderList)
        }
    }
}