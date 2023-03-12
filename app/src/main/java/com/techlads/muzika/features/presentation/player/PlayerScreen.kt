package com.techlads.muzika.features.presentation.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.techlads.muzika.R
import com.techlads.muzika.features.presentation.components.MediumSpacer
import com.techlads.muzika.features.presentation.components.PlayerSlider
import com.techlads.muzika.features.presentation.components.SmallSpacer
import com.techlads.muzika.features.presentation.components.TopBarWithNav
import com.techlads.muzika.ui.theme.SpaceMedium

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

            SmallSpacer()

            SongInfoRow()

            MediumSpacer()

            MediumSpacer()

            PlayerControlRow(modifier = Modifier.padding(horizontal = 16.dp))

            MediumSpacer()

            MediumSpacer()

            SongTimeRow(modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}

@Composable
fun SongInfoRow() {
    Box(
        modifier = Modifier
            .padding(horizontal = SpaceMedium)
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Believer",
                fontSize = 24.sp,
                color = MaterialTheme.colors.secondary,
                fontWeight = FontWeight.W500
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "IMAGINE DRAGON",
                fontSize = 16.sp,
                color = Color(0x5FA5C0FF),
                fontWeight = FontWeight.W400
            )
        }
        IconButton(
            onClick = { },
            enabled = true,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .height(18.dp)
                .width(18.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_heart_outline),
                contentDescription = "search",
                tint = Color(0x5FA5C0FF),
            )
        }
    }
}

@Composable
fun PlayerControlRow(modifier: Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = { },
            enabled = true,
            modifier = Modifier
                .height(18.dp)
                .width(18.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_volume),
                contentDescription = "search",
                tint = Color(0x5FA5C0FF),
            )
        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.wrapContentWidth()
        ) {
            IconButton(
                onClick = { },
                enabled = true,
                modifier = Modifier
                    .height(20.dp)
                    .width(20.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_round_repeat),
                    contentDescription = "search",
                    tint = Color(0x5FA5C0FF),
                )
            }

            IconButton(
                onClick = { },
                enabled = true,
                modifier = Modifier
                    .height(20.dp)
                    .width(20.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_shuffle_outline),
                    contentDescription = "search",
                    tint = Color(0x5FA5C0FF),
                )
            }
        }

    }
}

@Composable
fun SongTimeRow(modifier: Modifier) {

    Row(verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = modifier.fillMaxWidth()) {

        Text(
            text = "00:05",
            fontSize = 12.sp,
            color = Color(0x5FA5C0FF),
            fontWeight = FontWeight.W400
        )

        Text(
            text = "40:00",
            fontSize = 12.sp,
            color = Color(0x5FA5C0FF),
            fontWeight = FontWeight.W400
        )
}
}