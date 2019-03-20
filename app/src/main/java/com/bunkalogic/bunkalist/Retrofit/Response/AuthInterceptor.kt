package com.bunkalogic.bunkalist.Retrofit.Response

import com.bunkalogic.bunkalist.Others.Constans
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {
       val token = Constans.requestToken
        val request  = chain.request().newBuilder().addHeader("Authorization", token).build()
        return chain.proceed(request)
    }
}