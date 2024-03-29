package com.techlads.muzika.features.media.exoplayer

import android.media.MediaRecorder.AudioSource
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserCompat.MediaItem.FLAG_PLAYABLE
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.techlads.muzika.features.data.repository.AudioRepository
import javax.inject.Inject

class MediaSource @Inject constructor(private val repository: AudioRepository) {

    private val onReadyListeners: MutableList<OnReadyListener> = mutableListOf()
    var audioMediaMetaData: List<MediaMetadataCompat> = emptyList()

    private var state: AudioSourceState = AudioSourceState.STATE_CREATED
        set(value) {
            if (value == AudioSourceState.STATE_CREATED
                || value == AudioSourceState.STATE_ERROR
            ) {
                synchronized(onReadyListeners) {
                    field = value
                    onReadyListeners.forEach { listener: OnReadyListener ->
                        listener.invoke(isReady)
                    }
                }
            } else {
                field = value
            }
        }


    suspend fun load() {
        state = AudioSourceState.STATE_INITIALIZING
        val data = repository.getAudioData()
        audioMediaMetaData = data.map { audio ->
            MediaMetadataCompat.Builder()
                .putString(
                    MediaMetadataCompat.METADATA_KEY_MEDIA_ID,
                    audio.id.toString()
                )
                .putString(
                    MediaMetadataCompat.METADATA_KEY_ALBUM_ARTIST,
                    audio.artist.toString()
                )
                .putString(
                    MediaMetadataCompat.METADATA_KEY_MEDIA_URI,
                    audio.uri.toString()
                )
                .putString(
                    MediaMetadataCompat.METADATA_KEY_TITLE,
                    audio.title.toString()
                )
                .putString(
                    MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE,
                    audio.displayName.toString()
                )
                .putLong(
                    MediaMetadataCompat.METADATA_KEY_DURATION,
                    audio.duration.toLong()
                )
                .build()

        }
        state = AudioSourceState.STATE_INITIALIZED
    }


    fun asMediaSource(dataSource: CacheDataSource.Factory):
            ConcatenatingMediaSource {
        val concatenatingMediaSource = ConcatenatingMediaSource()

        audioMediaMetaData.forEach { mediaMetadataCompat ->
            val mediaItem = MediaItem.fromUri(
                mediaMetadataCompat.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI)
            )

            val mediaSource = ProgressiveMediaSource
                .Factory(dataSource)
                .createMediaSource(mediaItem)

            concatenatingMediaSource.addMediaSource(mediaSource)
        }
        return concatenatingMediaSource
    }

    fun asMediaItem() = audioMediaMetaData.map { metadata ->
        val description = MediaDescriptionCompat.Builder()
            .setTitle(metadata.description.title)
            .setMediaId(metadata.description.mediaId)
            .setSubtitle(metadata.description.subtitle)
            .setMediaUri(metadata.description.mediaUri)
            .build()
        MediaBrowserCompat.MediaItem(description, FLAG_PLAYABLE)
    }.toMutableList()


    fun refresh() {
        onReadyListeners.clear()
        state = AudioSourceState.STATE_CREATED
    }

    fun whenReady(listener: OnReadyListener): Boolean {
        return if (state == AudioSourceState.STATE_CREATED
            || state == AudioSourceState.STATE_INITIALIZING
        ) {
            onReadyListeners += listener
            false
        } else {
            listener.invoke(isReady)
            true
        }
    }

    private val isReady: Boolean
        get() = state == AudioSourceState.STATE_INITIALIZED

}

enum class AudioSourceState {
    STATE_CREATED,
    STATE_INITIALIZING,
    STATE_INITIALIZED,
    STATE_ERROR,
}
typealias OnReadyListener = (Boolean) -> Unit