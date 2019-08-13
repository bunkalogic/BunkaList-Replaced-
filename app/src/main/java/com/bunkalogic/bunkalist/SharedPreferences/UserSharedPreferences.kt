package com.bunkalogic.bunkalist.SharedPreferences

import android.content.Context
import android.util.ArrayMap


class UserSharedPreferences(context: Context){

    private val fileUser = "user_preferences"

    private val prefsUser = context.getSharedPreferences(fileUser, Context.MODE_PRIVATE)


    // data current user
    var userId: String?
    get() = prefsUser.getString("userId", "")
    set(value) = prefsUser.edit().putString("userId",value).apply()


    var userIdDatabase: String?
        get() = prefsUser.getString("userIdDatabase", "")
        set(value) = prefsUser.edit().putString("userIdDatabase",value).apply()



    var userName: String?
        get() = prefsUser.getString("userName", "")
        set(value) = prefsUser.edit().putString("userName",value).apply()


    var mode: Int
        get() = prefsUser.getInt("day", 0)
        set(value) = prefsUser.edit().putInt("day", value).apply()

    var imageProfilePath: String?
    get() = prefsUser.getString("profilePath", "")
    set(value) = prefsUser.edit().putString("profilePath", value).apply()

    // data movie, series and anime

    var itemName: String?
        get() = prefsUser.getString("itemName", "")
        set(value) = prefsUser.edit().putString("itemName",value).apply()

    var itemID: Int
        get() = prefsUser.getInt("itemId", 0)
        set(value) = prefsUser.edit().putInt("itemId", value).apply()

    // follow and followers

    var follows: Int
        get() = prefsUser.getInt("follows", 0)
        set(value) = prefsUser.edit().putInt("follows", value).apply()

    var followers: Int
        get() = prefsUser.getInt("followers", 0)
        set(value) = prefsUser.edit().putInt("followers", value).apply()

    // data profile list
    var sizeMovies: Int
        get() = prefsUser.getInt("totalMovies", 0)
        set(value) = prefsUser.edit().putInt("totalMovies", value).apply()

    var sizeSeries: Int
        get() = prefsUser.getInt("totalSeries", 0)
        set(value) = prefsUser.edit().putInt("totalSeries", value).apply()

    var sizeAnime: Int
        get() = prefsUser.getInt("totalAnime", 0)
        set(value) = prefsUser.edit().putInt("totalAnime", value).apply()


    // other data user

    var OtherUserId: String?
        get() = prefsUser.getString("OtherUserId", "")
        set(value) = prefsUser.edit().putString("OtherUserId",value).apply()

    var OtherUserIdBatabase: String?
        get() = prefsUser.getString("OtherUsername", "")
        set(value) = prefsUser.edit().putString("OtherUsername",value).apply()

    // collect the type of rating that is
    var ratingId: Int
        get() = prefsUser.getInt("ratingId", 0)
        set(value) = prefsUser.edit().putInt("ratingId", value).apply()

    // data current user
    var userGuestSesionId: String?
        get() = prefsUser.getString("userGuestSesionId", "")
        set(value) = prefsUser.edit().putString("userGuestSesionId",value).apply()

    // is First Open?
    var isFirstLaunch: Boolean
        get() = prefsUser.getBoolean("isFirstLaunch", true)
        set(value) = prefsUser.edit().putBoolean("isFirstLaunch",value).apply()

    // is Night mode
    var isNightMode: Boolean
        get() = prefsUser.getBoolean("isNightMode", false)
        set(value) = prefsUser.edit().putBoolean("isNightMode",value).apply()


    fun editCurrentUser(){
        prefsUser.edit().apply()
    }


    fun deleteAll(){
        prefsUser.edit().clear().apply()
    }
    fun deleteOtherUser(){
        prefsUser.edit().remove("OtherUserId").apply()
        prefsUser.edit().remove("OtherUsername").apply()
    }
}