package com.haythamayyash.mstarttask.common

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho

class MyApplication : Application() {
    companion object {
        private var instance: MyApplication? = null
        fun getContext(): Context? {
            return instance
        }
    }
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        instance = this
    }


}