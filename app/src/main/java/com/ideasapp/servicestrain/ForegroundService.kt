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
    private val builder by lazy {
        createNotificationBuilder()
    }
    private val notificationManager by lazy {
        getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onCreate() {
        Log.d("ForegroundService", "OnCreate")
        super.onCreate()
        createNotificationChannel()
        startForeground(1, builder.build())
    }

    override fun onStartCommand(intent:Intent?,flags:Int,startId:Int):Int {
        var timerCount= 0
        coroutineScope.launch {
            for(i in 0..100 step 2) {
                delay(500)
                val notification = builder.setProgress(100, i, false).build()
                notificationManager.notify(NOTIFICATION_ID, notification)
                timerCount += 1
                Log.d("ForegroundService", "$timerCount")
            }
            stopSelf()
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

        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "ForegroundService"
        }
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotificationBuilder(): Notification.Builder {
       return Notification.Builder(this, "my_channel_id")
            .setContentTitle("Notification")
            .setContentText("Your foreground service is working")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setProgress(100, 0, false)
            .setOnlyAlertOnce(true)
    }

    companion object {
        private const val NOTIFICATION_ID = 1
        fun newIntent(context: Context): Intent {
            return Intent(context, ForegroundService::class.java)
        }
    }
}