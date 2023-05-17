package com.techlads.muzika.features.media.exoplayer

import android.content.ComponentName
import android.content.Context
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.compose.runtime.mutableStateOf
import com.techlads.muzika.features.data.model.Audio
import com.techlads.muzika.features.media.constants.Constant
import com.techlads.muzika.features.media.service.MediaPlayerService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MediaPlayerServiceConnection @Inject constructor(@ApplicationContext context: Context
) {

    private val _playbackState: MutableStateFlow<PlaybackStateCompat?> =
        MutableStateFlow(null)

    val playbackState: StateFlow<PlaybackStateCompat?>
        get() = _playbackState

    private val _isConnected: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean>
        get() = _isConnected

    val currentPlayingAudio = mutableStateOf<Audio?>(null)

    lateinit var mediaControllerCompat: MediaControllerCompat

    private val mediaBrowserServiceCallback = MediaBrowserConnectionCallback(context)

    private val mediaBrowser = MediaBrowserCompat(
        context,
        ComponentName(context, MediaPlayerService::class.java),
        mediaBrowserServiceCallback,
        null
    ).apply {
        connect()
    }

    private var audioList = listOf<Audio>()

    val rootMediaId: String
        get() = mediaBrowser.root

    val transportControl: MediaControllerCompat.TransportControls
        get() = mediaControllerCompat.transportControls


    fun playAudio(audios: List<Audio>) {
        audioList = audios
        mediaBrowser.sendCustomAction(Constant.START_MEDIA_PLAY_ACTION, null, null)
    }

    fun fastForward(seconds: Int = 10) {
        playbackState.value?.currentPosition?.let {
            transportControl.seekTo( it + seconds * 1000)
        }
    }

    fun rewind(seconds: Int = 10) {
        playbackState.value?.currentPosition?.let {
            transportControl.seekTo( it - seconds * 1000)
        }
    }

    fun skipToNext() {
        transportControl.skipToNext()
    }

    fun skipToPrevious() {
        transportControl.skipToPrevious()
    }
    fun subscribe(
        parentId: String,
        callBack: MediaBrowserCompat.SubscriptionCallback
    ) {
        mediaBrowser.subscribe(parentId, callBack)
    }

    fun unSubscribe(
        parentId: String,
        callBack: MediaBrowserCompat.SubscriptionCallback
    ) {
        mediaBrowser.unsubscribe(parentId, callBack)
    }

    fun refreshMediaBrowserChildren(){
        mediaBrowser.sendCustomAction(Constant.REFRESH_MEDIA_PLAY_ACTION, null, null)
    }

    private inner class MediaBrowserConnectionCallback(
        private val context: Context
    ): MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {
            _isConnected.value = true
            mediaControllerCompat = MediaControllerCompat(
                context,
                mediaBrowser.sessionToken
            ).apply {
                registerCallback(MediaControllerCallback())
            }
        }

        override fun onConnectionSuspended() {
            _isConnected.value = false
        }

        override fun onConnectionFailed() {
            _isConnected.value = false
        }
    }




    private inner class MediaControllerCallback: MediaControllerCompat.Callback() {
        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            super.onPlaybackStateChanged(state)
            _playbackState.value = state
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            super.onMetadataChanged(metadata)
            currentPlayingAudio.value = metadata?.let { data ->
                audioList.find { it ->
                    it.id.toString() == data.description.mediaId
                }
            }
        }

        override fun onSessionDestroyed() {
            super.onSessionDestroyed()
            mediaBrowserServiceCallback.onConnectionSuspended()
        }
    }
}