package com.bunkalogic.bunkalist.SharedPreferences

import android.content.Context
import android.content.SharedPreferences


class UserSharedPreferences(context: Context){

    private val fileUser = "user_preferences"

    private val prefsUser = context.getSharedPreferences(fileUser, Context.MODE_PRIVATE)



    var userMode: Boolean = prefsUser.getBoolean("switchKey", false)




    //fun editUserMode(mode: Boolean){
    //    userMode = mode
    //    prefsUser.edit().commit()
    //
    //}

    var switchState: Boolean
    get() = prefsUser.getBoolean("service_status", false)
    set(value) = prefsUser.edit().putBoolean("service_status", value).apply()

    var mode: Int
        get() = prefsUser.getInt("day", 0)
        set(value) = prefsUser.edit().putInt("day", value).apply()

    fun editCurrentUser(){
        prefsUser.edit().apply()
    }


    fun deleteAll(){
        prefsUser.edit().clear().apply()
    }
}