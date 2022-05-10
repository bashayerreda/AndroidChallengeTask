package com.example.taskinstabug

import android.app.Application

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class BaseApplication : Application() {
    val executorService: ExecutorService = Executors.newFixedThreadPool(4)
    override fun onCreate() {
        super.onCreate()

    }
}