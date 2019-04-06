package com.bunkalogic.bunkalist.db

class UserComplete{

    var userId : String? = null
    var userName : String? = null
    var userImg : String? = null
    var userFollows: ArrayList<Users>? = null
    var userFollowers: ArrayList<Users>? = null

    constructor(){}

    constructor(
        userId: String?,
        userName: String?,
        userImg: String?,
        userFollows: ArrayList<Users>?,
        userFollowers: ArrayList<Users>?
    ) {
        this.userId = userId
        this.userName = userName
        this.userImg = userImg
        this.userFollows = userFollows
        this.userFollowers = userFollowers
    }

    constructor(
        userName: String,
        userImg: String,
        userFollows: ArrayList<Users>?,
        userFollowers: ArrayList<Users>?
    ) {
        this.userName = userName
        this.userImg = userImg
        this.userFollows = userFollows
        this.userFollowers = userFollowers
    }

    fun toMap(): HashMap<String, Any>{


        val result = HashMap<String, Any>()

        result["username"] = userName!!
        result["userImg"] = userImg!!
        result["userFollows"] = userFollows!!
        result["userFollowers"] = userFollowers!!

        return result
    }


}