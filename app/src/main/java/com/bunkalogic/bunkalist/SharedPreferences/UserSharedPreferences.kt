package com.bunkalogic.bunkalist.SharedPreferences

import android.content.Context


class UserSharedPreferences(context: Context){

    private val fileUser = "user_preferences"

    private val prefsUser = context.getSharedPreferences(fileUser, Context.MODE_PRIVATE)

    var userId: String?
    get() = prefsUser.getString("userId", "")
    set(value) = prefsUser.edit().putString("userId",value).apply()


    var mode: Int
        get() = prefsUser.getInt("day", 0)
        set(value) = prefsUser.edit().putInt("day", value).apply()

    var imageProfilePath: String?
    get() = prefsUser.getString("profilePath", "")
    set(value) = prefsUser.edit().putString("profilePath", value).apply()

    fun editCurrentUser(){
        prefsUser.edit().apply()
    }


    fun deleteAll(){
        prefsUser.edit().clear().apply()
    }
}