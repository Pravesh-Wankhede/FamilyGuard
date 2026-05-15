package com.example.familyprotector.ui.theme

import android.app.Application

class MyFamilyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPreference.init(this)   // 🔥 MUST
    }
}