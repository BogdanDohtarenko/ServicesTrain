package com.ideasapp.servicestrain

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ideasapp.servicestrain.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.simpleService.setOnClickListener {
            startService(MyService.newIntent(this))
        }
        binding.foregroundService.setOnClickListener {
            requestNotificationPermission()
        }
    }

    private fun requestNotificationPermission() {
        if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                .putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
            startActivity(intent)
        } else {
            showNotification()
        }
    }

    private fun showNotification() {
        val channelId = "my_channel_id"
        val channelName = "My Notification Channel"
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "This is a sample notification channel."
        }
        notificationManager.createNotificationChannel(channel)

        val notification = Notification.Builder(this, channelId)
            .setContentTitle("Notification Title")
            .setContentText("This is a simple notification.")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        notificationManager.notify(1, notification)
    }
}