package com.techlads.muzika.features.presentation.home

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.techlads.muzika.featu.AudioViewModel
import com.techlads.muzika.features.data.model.Audio
import com.techlads.muzika.features.presentation.PreviewUtils
import com.techlads.muzika.features.presentation.PreviewUtils.dummyAudioList
import com.techlads.muzika.ui.theme.MuzikaTheme


@ExperimentalMaterialApi
@Composable
fun HomeScreenNew(
    progress: Float,
    onProgressChange: (Float) -> Unit,
    isAudioPlaying: Boolean,
    audioList: List<Audio>,
    currentAudioPlaying: Audio?,
    onStart: (Audio) -> Unit,
    onItemClicked: (Audio) -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {

    val scaffoldState = rememberBottomSheetScaffoldState()

    val animatedHeight by animateDpAsState(
        targetValue = if (currentAudioPlaying == null) 0.dp
        else BottomSheetScaffoldDefaults.SheetPeekHeight
    )

    BottomSheetScaffold(
        sheetContent = {
            currentAudioPlaying?.let { currentAudioPlaying ->
                BottomBarPlayer(
                    progress = progress,
                    onProgressChange = onProgressChange,
                    audio = currentAudioPlaying,
                    isAudioPlaying = isAudioPlaying,
                    onStart = { onStart.invoke(currentAudioPlaying) },
                    onNext = { onNext.invoke() },
                    onPrevious = { onPrevious.invoke() }
                )

            }
        },
        scaffoldState = scaffoldState,

        sheetPeekHeight = animatedHeight) {
        
        LazyColumn(
            flingBehavior = ScrollableDefaults.flingBehavior(),
            userScrollEnabled = true,
            contentPadding = PaddingValues(bottom = 56.dp)) {
            items( count = audioList.size,
                key = {audioList[it].id},
            itemContent = {index ->
                val audio = audioList[index]
                AudioItem(
                    audio = audio,
                    onItemClick = { onItemClicked.invoke(audio)})
            })

        }
    }
}

@Composable
fun AudioItem(
    audio: Audio,
    onItemClick: (id: Long) -> Unit,
) {

    Card(
        //shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clickable {
                onItemClick.invoke(audio.id)
            },

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = audio.displayName,
                    style = MaterialTheme.typography.h6,
                    overflow = TextOverflow.Clip,
                    maxLines = 1,
                    color = MaterialTheme.colors.secondary
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = audio.artist,
                    style = MaterialTheme.typography.subtitle1,
                    overflow = TextOverflow.Clip,
                    maxLines = 1,
                    color = MaterialTheme.colors.secondary
                )
            }

            Text(
                text = timeStampToDuration(audio.duration.toLong()),
                style = MaterialTheme.typography.subtitle1,
                overflow = TextOverflow.Clip,
                maxLines = 1,
                color = MaterialTheme.colors.secondary
            )
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}

private fun timeStampToDuration(position: Long): String {
    val totalSeconds = kotlin.math.floor(position / 1E3).toInt()
    val minutes = totalSeconds / 60
    val remainingSeconds = totalSeconds - (minutes * 60)

    return if (position < 0) "--:--"
    else "%d:%02d".format(minutes, remainingSeconds)
}

@Composable
fun BottomBarPlayer(
    progress: Float,
    onProgressChange: (Float) -> Unit,
    audio: Audio,
    isAudioPlaying: Boolean,
    onStart: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
) {
    Column() {
        Row(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ArtistInfo(
                audio = audio,
                modifier = Modifier.weight(1f)
            )

            MediaPlayerController(
                isAudioPlaying = isAudioPlaying,
                onStart = { onStart.invoke() },
                onNext = { onNext.invoke() },
                onPrevious = { onPrevious.invoke() }
            )
        }
        Slider(
            value = progress,
            onValueChange = { onProgressChange.invoke(it) },
            valueRange = 0f..100f,
            colors = SliderDefaults.colors(
                thumbColor = Color.Black,
                disabledThumbColor = Color.Blue.copy(0.5f),
                activeTrackColor = Color.Black.copy(0.8f),
                inactiveTrackColor = Color.Black.copy(0.2f),
                activeTickColor = Color.Black.copy(0.8f),
                inactiveTickColor = Color.Blue.copy(0.5f),
            )
        )
    }
}

@Composable
fun ArtistInfo(
    modifier: Modifier = Modifier,
    audio: Audio
) {
    Row(
        modifier = modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PlayerIconItem(
            icon = Icons.Default.MusicNote,
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colors.onSurface
            ),
            onClick = {})

        Spacer(modifier = Modifier.size(4.dp))

        Column {
            Text(
                text = audio.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h6,
                overflow = TextOverflow.Clip,
                maxLines = 1,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = audio.title,
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.subtitle1,
                overflow = TextOverflow.Clip,
                maxLines = 1,
                modifier = Modifier.weight(1f)
            )
        }

    }
}

@Composable
fun PlayerIconItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    border: BorderStroke? = null,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = MaterialTheme.colors.onSurface,
    onClick: () -> Unit
) {
    Surface(
        shape = CircleShape,
        border = border,
        contentColor = contentColor,
        color = backgroundColor,
        modifier = Modifier
            .clip(CircleShape)
            .clickable {
                onClick.invoke()
            }
    ) {
        Box(
            modifier = modifier.padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = ""
            )
        }
    }
}

@Composable
fun MediaPlayerController(
    isAudioPlaying: Boolean,
    onStart: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(56.dp)
            .padding(4.dp)
    ) {

        Icon(imageVector = Icons.Default.SkipPrevious,
            contentDescription = "",
            modifier = Modifier.clickable {
                onPrevious.invoke()
            })
        Spacer(modifier = Modifier.size(6.dp))
        PlayerIconItem(
            icon = if (isAudioPlaying) Icons.Default.Pause
            else Icons.Default.PlayArrow,
            backgroundColor = MaterialTheme.colors.primary,

            ) {
            onStart.invoke()
        }
        Spacer(modifier = Modifier.size(6.dp))
        Icon(imageVector = Icons.Default.SkipNext,
            contentDescription = "",
            modifier = Modifier.clickable {
                onNext.invoke()
            })
    }
}

