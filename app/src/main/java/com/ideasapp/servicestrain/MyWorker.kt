package com.ideasapp.servicestrain

import android.content.Context
import android.util.Log
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(
    context:Context,
    workerParameters:WorkerParameters
): Worker(context, workerParameters) {
    override fun doWork():Result {
        var timerCount= 0
        for(i in 0 until 100) {
            Thread.sleep(1000)
            timerCount += 1
            Log.d("MyJobIntent", "$timerCount")
        }
        return Result.success()
    }

    companion object {
        fun getOneTimeWorkRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<MyWorker>().build()
        }
    }
}