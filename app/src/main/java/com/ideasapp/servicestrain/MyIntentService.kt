package com.ideasapp.servicestrain

import android.app.IntentService
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.CoroutineScope

class MyIntentService: IntentService(NAME) {

    override fun onCreate() {
        Log.d("MyIntent", "OnCreate")
        super.onCreate()
        createNotificationChannel()
        startForeground(1, createNotification())
    }

    override fun onHandleIntent(intent:Intent?) {
        var timerCount= 0
        for(i in 0 until 100) {
            Thread.sleep(1000)
            timerCount += 1
            Log.d("MyIntent", "$timerCount")
        }
    }

    override fun onDestroy() {
        Log.d("MyIntent", "OnCreate")
        super.onDestroy()
    }

    private fun createNotificationChannel() {
        val channelId = "my_channel_id"
        val channelName = "My Notification Channel"
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "MyIntent"
        }
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification(): Notification {
        return Notification.Builder(this, "my_channel_id")
            .setContentTitle("Notification")
            .setContentText("Your MyIntent service is working")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
    }

    companion object {
        const val NAME = "MyIntentService"

        fun newIntent(context:Context):Intent {
            return Intent(context, ForegroundService::class.java)
        }
    }
}