package com.example.instabugtask.utils

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ExecutorPool : Executor {
    private val executor: Executor
    override fun execute(runnable: Runnable) {
        executor.execute(runnable)
    }

    init {
        executor = Executors.newSingleThreadExecutor()
    }
}