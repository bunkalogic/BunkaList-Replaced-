package com.bunkalogic.bunkalist.db


class Status{
    val complete: Int = 0
    val watching: Int = 1
    val waiting_for_out: Int = 2
    val on_pause: Int = 3
    val dropped: Int = 4
}