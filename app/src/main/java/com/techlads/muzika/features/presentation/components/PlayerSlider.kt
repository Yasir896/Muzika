package com.techlads.muzika.features.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlin.math.absoluteValue
import com.techlads.muzika.R


@ExperimentalPagerApi
@Composable
fun PlayerSlider(sliderList: Array<Int>) {
    val pagerState = rememberPagerState(
        initialPage = 1 )

    HorizontalPager(
        count = sliderList.size,
        state = pagerState,
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(horizontal = 50.dp)
    ) {  page ->
        Card(
            backgroundColor = MaterialTheme.colors.background,
            shape = RoundedCornerShape(10.dp),
            elevation = 2.dp,
            modifier = Modifier.graphicsLayer {

                val pageOffSet = calculateCurrentOffsetForPage(page).absoluteValue

                lerp(
                    start = 0.85f,
                    stop = 1f,
                    fraction = 1f - pageOffSet.coerceIn(0f, 1f)
                ).also { scale ->
                    scaleX = scale
                    scaleY = scale
                }
                alpha = lerp(
                    start = 0.7f,
                    stop = 1f,
                    fraction = 1f - pageOffSet.coerceIn(0f, 1f)
                )
            }
        ) {
            Image(
                painterResource(id = sliderList[page]),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset {
                        // Calculate the offset for the current page from the
                        // scroll position
                        val pageOffSet = this@HorizontalPager.calculateCurrentOffsetForPage(page)
                        // Then use it as a multiplier to apply an offset
                        IntOffset(
                            x = (70.dp * pageOffSet).roundToPx(),
                            y = 0,
                        )
                    })
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
@Preview
fun PreviewSlider() {
    MaterialTheme() {
        Box(modifier = Modifier.padding(vertical = 16.dp)) {
            val sliderList = arrayOf(
                R.drawable.believer,
                R.drawable.card_placeholder,
                R.drawable.moment_apart,
                R.drawable.believer,
                R.drawable.card_placeholder,

                )
            PlayerSlider(sliderList)
        }

    }
}
