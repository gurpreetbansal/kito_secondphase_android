package com.diggytech.kinoplasticpremios

import android.app.Application
import android.content.res.Configuration
import com.facebook.drawee.backends.pipeline.Fresco
import java.util.*

class MyApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)

    }
}