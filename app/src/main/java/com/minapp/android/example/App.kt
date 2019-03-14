package com.minapp.android.example

import android.app.Application
import com.minapp.android.sdk.Baas

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Baas.init(Const.clientId, Const.clientSecret)
    }

}