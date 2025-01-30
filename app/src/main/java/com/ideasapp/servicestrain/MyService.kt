package com.ideasapp.servicestrain

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyService: Service() {

    val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        Log.d("MyService", "OnCreate")
        super.onCreate()
    }

    override fun onStartCommand(intent:Intent?,flags:Int,startId:Int):Int {
        var timerCount: Int = 0
        coroutineScope.launch {
            for(i in 0 until 100) {
                delay(1000)
                timerCount += 1
                Log.d("MyService", "$timerCount")
            }
        }
        return super.onStartCommand(intent,flags,startId)
    }

    override fun onDestroy() {
        Log.d("MyService", "OnCreate")
        super.onDestroy()
    }

    override fun onBind(intent:Intent?):IBinder? {
        TODO("Not yet implemented")
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MyService::class.java)
        }
    }
}