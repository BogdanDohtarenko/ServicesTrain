package com.ideasapp.servicestrain

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
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
}