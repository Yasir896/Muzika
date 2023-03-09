package com.techlads.muzika.features.presentation.liked_songs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techlads.muzika.R
import com.techlads.muzika.features.data.Song
import com.techlads.muzika.features.presentation.components.MediumSpacer
import com.techlads.muzika.features.presentation.components.SmallSpacer
import com.techlads.muzika.features.presentation.components.SongCard
import com.techlads.muzika.features.presentation.components.TopBarWithNav

@Composable
fun LikedSongsScreen(
    onNavigationClick: () -> Unit,
    onSongItemClick: () -> Unit,
) {

    val songs = arrayOf(
        Song(image = R.drawable.card_placeholder, songName = "Monsters Go Bump", singerName = "ERIKA RECINOS"),
        Song(image = R.drawable.moment_apart, songName = "Moment Apart", singerName = "ODESZA"),
        Song(image = R.drawable.believer, songName = "Believer", singerName = "IMAGINE DRAGON"),
        Song(image = R.drawable.card_placeholder, songName = "Monsters Go Bump", singerName = "ERIKA RECINOS"),
        Song(image = R.drawable.moment_apart, songName = "Moment Apart", singerName = "ODESZA"),
        Song(image = R.drawable.card_placeholder, songName = "Monsters Go Bump", singerName = "ERIKA RECINOS"),
        Song(image = R.drawable.moment_apart, songName = "Moment Apart", singerName = "ODESZA"),
        Song(image = R.drawable.believer, songName = "Believer", singerName = "IMAGINE DRAGON"),
        Song(image = R.drawable.card_placeholder, songName = "Monsters Go Bump", singerName = "ERIKA RECINOS"),
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
                onNavigationClick = onNavigationClick,
                onFilterClick = { },
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
             SmallSpacer()

            LazyVerticalGrid(
                modifier = Modifier.testTag("songs_grid_view"),
                columns = GridCells.Fixed(2),
                content = { items(songs.size) {
                    val song = songs[it]
                    SongCard(song = song,
                        onItemClick = onSongItemClick)
                }

                }
            )
        }
    }
}