package com.nttdatamxdx.gymdfm.core

import android.content.Context
import com.google.android.play.core.splitcompat.SplitCompat

class Application : android.app.Application() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }

}