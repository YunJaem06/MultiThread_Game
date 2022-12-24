package com.example.multithread_game

import android.app.Application
import android.content.SharedPreferences

class GlobalApplication : Application() {

    companion object {
        lateinit var pref : SharedPreferences
    }

    override fun onCreate() {

        pref = applicationContext.getSharedPreferences("score", MODE_PRIVATE)

        super.onCreate()
    }
}