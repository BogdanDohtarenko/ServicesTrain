package com.ideasapp.servicestrain

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service.NOTIFICATION_SERVICE
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.getSystemService

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context : Context?, intent : Intent?) {
        context?.let {
            val notificationManager =
                getSystemService(it, NotificationManager::class.java) as NotificationManager

            createNotificationChannel(notificationManager)

            val notificationBuilder =  Notification.Builder(it, "my_channel_id")
                    .setContentTitle("Notification")
                    .setContentText("Your foreground service is working")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)

            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
        }
    }

    private fun createNotificationChannel(notificationManager : NotificationManager) {
        val channelId = "my_channel_id"
        val channelName = "My Notification Channel"

        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "ForegroundService"
        }
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        private const val NOTIFICATION_ID = 1
        fun newIntent(context: Context): Intent {
            return Intent(context, AlarmReceiver::class.java)
        }
    }
}