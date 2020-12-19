package com.example.telemedicine

import android.app.Application
import android.content.Context

class MainApplication : Application()
{
    init {
        instance = this
    }

    companion object {
        private lateinit var instance: MainApplication

        val context: Context
            get() = instance.applicationContext
    }
}