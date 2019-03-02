package com.bunkalogic.bunkalist.SharedPreferences

import android.app.Application

val preferences: UserSharedPreferences by lazy { UserApp.prefs!! }


class UserApp : Application(){

    companion object {
        var prefs: UserSharedPreferences? = null
    }

    override fun onCreate() {
        super.onCreate()
        prefs = UserSharedPreferences(applicationContext)
    }

}