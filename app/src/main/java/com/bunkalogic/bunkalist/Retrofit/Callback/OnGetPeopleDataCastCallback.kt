package com.bunkalogic.bunkalist.Retrofit.Callback

import com.bunkalogic.bunkalist.Retrofit.Response.People.CastResult
import com.bunkalogic.bunkalist.Retrofit.Response.People.CrewResult

interface OnGetPeopleDataCastCallback {

    fun onSuccess(peopleCast: List<CastResult>, peopleCrew: List<CrewResult>)

    fun onError()
}