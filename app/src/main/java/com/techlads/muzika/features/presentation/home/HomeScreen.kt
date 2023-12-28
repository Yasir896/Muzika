package com.techlads.muzika.features.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techlads.muzika.features.data.model.Song
import com.techlads.muzika.features.presentation.components.MediumSpacer
import com.techlads.muzika.features.presentation.components.SongCard
import com.techlads.muzika.R
import com.techlads.muzika.features.presentation.HomeTopBar
import com.techlads.muzika.features.presentation.components.SmallSpacer

@Composable
fun HomeScreen(
    onMenuClick: () -> Unit,
    onSongItemClick: () -> Unit
) {

    val songs = arrayOf(
        Song(
            image = R.drawable.card_placeholder,
            songName = "Monsters Go Bump",
            singerName = "ERIKA RECINOS"
        ),
        Song(image = R.drawable.moment_apart, songName = "Moment Apart", singerName = "ODESZA"),
        Song(image = R.drawable.believer, songName = "Believer", singerName = "IMAGINE DRAGON"),
        Song(
            image = R.drawable.card_placeholder,
            songName = "Monsters Go Bump",
            singerName = "ERIKA RECINOS"
        ),
        Song(image = R.drawable.moment_apart, songName = "Moment Apart", singerName = "ODESZA"),
        Song(image = R.drawable.believer, songName = "Believer", singerName = "IMAGINE DRAGON"),
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
            HomeTopBar(
                onMenuClick = { onMenuClick() },
                onSearchClick = { },
                modifier = Modifier.fillMaxWidth()
            )

            MediumSpacer()

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Recommended for you",
                fontSize = 24.sp,
                fontWeight = FontWeight.W700,
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                Modifier
                    .aspectRatio(1.5f, false)
                    .padding(horizontal = 8.dp)
            ) {
                items(songs.size) { song ->
                    SongCard(song = songs[song], onItemClick = onSongItemClick)
                }
            }

            MediumSpacer()

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "My Playlist",
                fontSize = 24.sp,
                fontWeight = FontWeight.W700,
            )
            SmallSpacer()
            LazyRow(
                Modifier
                    .aspectRatio(1.5f, false)
                    .padding(horizontal = 8.dp)
            ) {
                items(songs.size) { song ->
                    SongCard(song = songs[song], onItemClick = { })
                }
            }
        }
    }
}