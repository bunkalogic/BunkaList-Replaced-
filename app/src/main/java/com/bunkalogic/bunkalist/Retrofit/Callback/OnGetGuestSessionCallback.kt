package com.bunkalogic.bunkalist.Retrofit.Callback

interface OnGetGuestSessionCallback {

    fun onSuccess(guestId: String)

    fun onError()
}