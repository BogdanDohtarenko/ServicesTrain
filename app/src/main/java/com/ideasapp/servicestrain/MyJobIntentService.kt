package com.ideasapp.servicestrain

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService

class MyJobIntentService: JobIntentService() {

    override fun onCreate() {
        Log.d("MyJobIntent", "OnCreate")
        super.onCreate()
    }

    override fun onHandleWork(intent: Intent) {
        var timerCount= 0
        for(i in 0..100 step 2) {
            Thread.sleep(500)
            timerCount += 1
            Log.d("MyJobIntent", "$timerCount")
        }
    }

    override fun onDestroy() {
        Log.d("MyJobIntent", "OnCreate")
        super.onDestroy()
    }

    companion object {
        const val NAME = "MyJobIntent"
        const val JOB_ID = 111

        fun enqueue(context: Context) {
            enqueueWork(
                context,
                MyJobIntentService::class.java,
                JOB_ID,
                newIntent(context)
            )
        }

        private fun newIntent(context:Context):Intent {
            return Intent(context, ForegroundService::class.java)
        }
    }
}