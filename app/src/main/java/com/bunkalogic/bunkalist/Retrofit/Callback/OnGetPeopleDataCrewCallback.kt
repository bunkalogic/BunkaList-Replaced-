package com.bunkalogic.bunkalist.Retrofit.Callback

import com.bunkalogic.bunkalist.Retrofit.Response.People.CrewResult

interface OnGetPeopleDataCrewCallback {

        fun onSuccess(peopleCrew: List<CrewResult>)

        fun onError()

}