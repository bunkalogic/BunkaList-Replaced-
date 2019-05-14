package com.bunkalogic.bunkalist.Retrofit.Callback

import com.bunkalogic.bunkalist.Retrofit.Response.People.ResultPeople


interface OnGetPeopleDataCallback {

    fun onSuccess(people: ResultPeople)

    fun onError()
}