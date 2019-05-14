package com.bunkalogic.bunkalist.Retrofit.Callback

import com.bunkalogic.bunkalist.Retrofit.Response.People.Cast
import com.bunkalogic.bunkalist.Retrofit.Response.People.Crew

interface OnGetPeopleCallback {

    fun onSuccess(crew: List<Crew>, cast: List<Cast>)

    fun onError()
}