package com.techlads.muzika.featu

import com.techlads.muzika.features.media.constants.Constant
import com.techlads.muzika.features.media.exoplayer.currentPosition
import com.techlads.muzika.features.media.service.MediaPlayerService
import kotlinx.coroutines.delay
import android.support.v4.media.MediaBrowserCompat
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techlads.muzika.features.data.model.Audio
import com.techlads.muzika.features.data.repository.AudioRepository
import com.techlads.muzika.features.media.exoplayer.MediaPlayerServiceConnection
import com.techlads.muzika.features.media.exoplayer.isPlaying
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioViewModel @Inject constructor(
    private val repository: AudioRepository,
    serviceConnection: MediaPlayerServiceConnection
):ViewModel() {

     val audioList = mutableStateListOf<Audio>()
    val currentPlayingAudio = serviceConnection.currentPlayingAudio
    private val isConnected = serviceConnection.isConnected
    lateinit var rootMediaId: String
    var currentPlaybackPosition by mutableStateOf(0L)
    private var updatePosition = true
    private val playbackState = serviceConnection.playbackState
    val isAudioPlaying: Boolean
        get() = playbackState.value?.isPlaying == true

    private val subscriptionCallback
    = object : MediaBrowserCompat.SubscriptionCallback() {
        override fun onChildrenLoaded(
            parentId: String,
            children: MutableList<MediaBrowserCompat.MediaItem>
        ) {
            super.onChildrenLoaded(parentId, children)
        }
    }

    private val serviceConnection = serviceConnection.also {
        updatePlayBack()
    }

    val currentDuration: Long
        get() = MediaPlayerService.currentDuration


    var currentAudioProgress = mutableStateOf(0f)

    init {
        viewModelScope.launch {
            audioList += getAndFormatAudioData()
            isConnected.collect {
                if (it){
                    rootMediaId = serviceConnection.rootMediaId
                    serviceConnection.playbackState.value?.apply {
                        currentPlaybackPosition = position
                    }
                    serviceConnection.subscribe(rootMediaId, subscriptionCallback)

                }
            }
        }
    }

    private suspend fun getAndFormatAudioData(): List<Audio> {
        return repository.getAudioData().map {
            val displayName = it.displayName.substringBefore(".")
            val artis = if (it.artist.contains("<unkown>"))
                "Unkown Artist" else it.artist
            it.copy(displayName = displayName, artist = artis)
        }
    }

    fun playAudio(currentAudio: Audio) {
        serviceConnection.playAudio(audioList)
        if(currentAudio.id == currentPlayingAudio.value?.id){
             if (isAudioPlaying) {
                 serviceConnection.transportControl.pause()
             } else {
                 serviceConnection.transportControl.play()
             }
        } else {
            serviceConnection.transportControl
                .playFromMediaId(
                    currentAudio.id.toString(),
                    null
                )
        }

    }

    fun stopPlayBack() {
        serviceConnection.transportControl.stop()
    }

    fun fastForward() {
        serviceConnection.fastForward()
    }

    fun rewind() {
        serviceConnection.rewind()
    }

    fun skipToNext() {
        serviceConnection.skipToNext()
    }

    fun skipToPrevious() {
        serviceConnection.skipToPrevious()
    }
    fun seekTo(value: Float) {
        serviceConnection.transportControl.seekTo(
            (currentDuration * value / 100f).toLong()
        )
    }

    private fun updatePlayBack() {
        viewModelScope.launch {
            val position = playbackState.value?.currentPosition ?: 0

            if (currentPlaybackPosition != position) {
                currentPlaybackPosition = position
            }

            if (currentDuration > 0) {
                currentAudioProgress.value = (
                        currentPlaybackPosition.toFloat()
                        / currentDuration.toFloat() * 100f
                        )
            }
            delay(Constant.PLAYBACK_UPDATE_INTERVAL)
            if (updatePosition) {
                updatePlayBack()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        serviceConnection.unSubscribe(
            Constant.MEDIA_ROOT_ID,
            object: MediaBrowserCompat.SubscriptionCallback() {}
        )
        updatePosition = false
    }
}