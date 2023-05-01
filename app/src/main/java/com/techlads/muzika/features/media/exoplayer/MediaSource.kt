package com.techlads.muzika.features.media.exoplayer

import android.media.MediaRecorder.AudioSource
import com.techlads.muzika.features.data.repository.AudioRepository
import javax.inject.Inject

class MediaSource @Inject constructor(private val repository: AudioRepository) {

    private val onReadyListeners: MutableList<OnReadyListener> = mutableListOf()

    private var state: AudioSourceState = AudioSourceState.STATE_CREATED
    set(value) {
        if (value == AudioSourceState.STATE_CREATED
            || value == AudioSourceState.STATE_ERROR
        ) {
            synchronized(onReadyListeners) {
                field = value
                onReadyListeners.forEach{listener: OnReadyListener ->
                    listener.invoke(isReady)
                }
            }
        } else {
            field = value
        }
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

enum class AudioSourceState{
    STATE_CREATED,
    STATE_INITIALIZING,
    STATE_INITIALIZED,
    STATE_ERROR,
}
typealias OnReadyListener = (Boolean) -> Unit