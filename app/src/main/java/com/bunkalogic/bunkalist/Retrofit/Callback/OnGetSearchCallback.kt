package com.bunkalogic.bunkalist.Retrofit.Callback

import android.os.AsyncTask.execute
import com.bunkalogic.bunkalist.Retrofit.Response.ResultSearchAll
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody


interface OnGetSearchCallback {

    fun onSuccess(all: List<ResultSearchAll>)

    fun onError()
}



