package br.com.adrianorazzini.widgetchallenge

import android.app.Application

class WidgetApplication : Application() {

    companion object {
        lateinit var appContext: WidgetApplication
    }

    override fun onCreate() {
        super.onCreate()

        appContext = this
    }
}