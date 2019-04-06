package com.bunkalogic.bunkalist.db

class Users{

    var userId: String? = null
    var username: String? = null
    var userPhoto: String? = null

    constructor(){}


    constructor(userId: String, username: String, userPhoto: String) {
        this.userId = userId
        this.username = username
        this.userPhoto = userPhoto
    }

    constructor(username: String, userPhoto: String) {
        this.username = username
        this.userPhoto = userPhoto
    }

    fun toMap(): Map<String, Any>{

        val result = HashMap<String, Any>()
        result["username"] = username!!
        result["userPhoto"] = userPhoto!!


        return result
    }


}