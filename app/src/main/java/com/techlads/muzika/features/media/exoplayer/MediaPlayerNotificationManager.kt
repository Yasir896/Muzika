package com.techlads.muzika.features.media.exoplayer

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.techlads.muzika.R
import com.techlads.muzika.features.media.constants.Constant

internal class MediaPlayerNotificationManager(
    context: Context,
    sessionToken: MediaSessionCompat.Token,
    notificationListener: PlayerNotificationManager.NotificationListener
) {

    private val notificationManager: PlayerNotificationManager

    init {
        val mediaController = MediaControllerCompat(context, sessionToken)

        val builder = PlayerNotificationManager.Builder(
            context,
            Constant.PLAYBACK_NOTIFICATION_ID,
            Constant.PLAYBACK_NOTIFICATION_CHANNEL_ID
        )

        with(builder){
            setMediaDescriptionAdapter(DescriptionAdapter(mediaController))
            setNotificationListener(notificationListener)
            setChannelNameResourceId(R.string.notification_channel)
            setChannelDescriptionResourceId(R.string.notification_channel_description)
        }


        notificationManager = builder.build()
        with(notificationManager){
            setMediaSessionToken(sessionToken)
            setSmallIcon(R.drawable.baseline_music_note_24)
            setUseRewindAction(false)
            setUseFastForwardAction(false)

        }
    }

    fun hideNotification() {
        notificationManager.setPlayer(null)
    }

    fun showNotification(player: Player) {
        notificationManager.setPlayer(player)
    }

    inner class DescriptionAdapter(private val controller: MediaControllerCompat) :
            PlayerNotificationManager.MediaDescriptionAdapter{
        override fun getCurrentContentTitle(player: Player):
                CharSequence = controller.metadata.description.title.toString()

        override fun createCurrentContentIntent(player: Player): PendingIntent? =
            controller.sessionActivity

        override fun getCurrentContentText(player: Player): CharSequence? =
            controller.metadata.description.subtitle

        override fun getCurrentLargeIcon(
            player: Player,
            callback: PlayerNotificationManager.BitmapCallback
        ): Bitmap? {
            return null
        }

    }
}