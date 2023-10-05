package com.asepssp.aplikasiusergithub.utilities

import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors {

    val disk: Executor = Executors.newSingleThreadExecutor()
    val network: Executor = Executors.newFixedThreadPool(3)
    val mThread: Executor = MainThread()

    private class MainThread : Executor {
        private val mThreadHandler = android.os.Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mThreadHandler.post(command)
        }
    }
}