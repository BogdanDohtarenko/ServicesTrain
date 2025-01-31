package com.ideasapp.servicestrain

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ForegroundService: Service() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        Log.d("ForegroundService", "OnCreate")
        super.onCreate()
        createNotificationChannel()
        startForeground(1, createNotification())
    }

    override fun onStartCommand(intent:Intent?,flags:Int,startId:Int):Int {
        var timerCount= 0
        coroutineScope.launch {
            for(i in 0 until 100) {
                delay(1000)
                timerCount += 1
                Log.d("ForegroundService", "$timerCount")
            }
        }
        return super.onStartCommand(intent,flags,startId)
    }

    override fun onDestroy() {
        Log.d("ForegroundService", "OnCreate")
        super.onDestroy()
    }

    override fun onBind(intent:Intent?):IBinder? {
        TODO("Not yet implemented")
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
            description = "ForegroundService"
        }
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification(): Notification {
       return Notification.Builder(this, "my_channel_id")
            .setContentTitle("Notification")
            .setContentText("Your foreground service is working")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ForegroundService::class.java)
        }
    }
}