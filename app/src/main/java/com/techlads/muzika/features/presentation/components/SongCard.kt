package com.techlads.muzika.features.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techlads.muzika.R
import com.techlads.muzika.features.data.model.Song

@Composable
fun SongCard(
    modifier: Modifier = Modifier,
    song: Song,
    onItemClick: () -> Unit,
    aspectRatio: Float = 0.8f
) {

    Card(
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        backgroundColor = MaterialTheme.colors.primary,
        modifier = modifier
            .aspectRatio(aspectRatio)
            .padding(horizontal = 8.dp)
            .clickable {
                onItemClick()
            }
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painterResource(id = song.image),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth())

            SmallSpacer()
            
            Text(text = song.songName,
                fontSize = 16.sp,
                color = MaterialTheme.colors.secondary,
                fontWeight = FontWeight.W500)

            SmallSpacer()

            Text(text = song.singerName,
                fontSize = 10.sp,
                color = Color(0x5FA5C0FF),
                fontWeight = FontWeight.W400)
        }
    }
}

@Composable
@Preview
fun PreviewSongCard() {
    MaterialTheme() {
        val song = Song(image = R.drawable.card_placeholder, songName = "Monsters Go Bump", singerName = "ERIKA RECINOS")

        SongCard(song = song, onItemClick = {  }, )
    }
}