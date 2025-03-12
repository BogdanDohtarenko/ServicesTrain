package com.ideasapp.servicestrain

import android.app.Service
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.ideasapp.servicestrain.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val serviceConnection = object: ServiceConnection {
        override fun onServiceConnected(name : ComponentName?, service : IBinder?) {
            val binder = (service as? ForegroundService.LocalBinder) ?: return
            val foregroundService = binder.getInstance()
            foregroundService.progressBarChanged =  { progress ->
                binding.progressBarLoading.progress = progress
            }
        }

        override fun onServiceDisconnected(name : ComponentName?) {
            TODO("Not yet implemented")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.simpleService.setOnClickListener {
            startService(MyService.newIntent(this))
        }
        binding.foregroundService.setOnClickListener {
            startForegroundService(ForegroundService.newIntent(this))
        }
        binding.intentService.setOnClickListener {
            startForegroundService(MyIntentService.newIntent(this))
        }
        binding.jobScheduler.setOnClickListener {
            val componentName = ComponentName(this, MyJobService::class.java)

            val jobInfo = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                JobInfo.Builder(MyJobService.JOB_ID, componentName)
                    .setRequiresCharging(true)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                    .build()
            } else {
                JobInfo.Builder(MyJobService.JOB_ID, componentName)
                    .setRequiresCharging(true)
                    .build()
            }

            val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheduler.schedule(jobInfo)
        }
        binding.jobIntentService.setOnClickListener {
            MyJobIntentService.enqueue(this)
        }
        binding.workManager.setOnClickListener {
            val workManager = WorkManager.getInstance(applicationContext)
            workManager.enqueueUniqueWork(
                "Work",
                ExistingWorkPolicy.KEEP,
                MyWorker.getOneTimeWorkRequest()
            )
        }
    }

    override fun onStart() {
        super.onStart()
        bindService(ForegroundService.newIntent(this), serviceConnection, 0)
    }

    override fun onStop() {
        super.onStop()
        unbindService(serviceConnection)
    }

}