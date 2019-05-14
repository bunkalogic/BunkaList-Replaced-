package com.bunkalogic.bunkalist.Retrofit.Callback

import com.bunkalogic.bunkalist.Retrofit.Response.People.PeopleSocialMediaResponse


interface OnGetPeopleSocialMediaCallback {
    fun onSuccess(peopleSocial: PeopleSocialMediaResponse)

    fun onError()
}