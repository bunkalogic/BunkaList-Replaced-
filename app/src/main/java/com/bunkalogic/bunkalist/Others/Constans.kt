package com.bunkalogic.bunkalist.Others

import com.bunkalogic.bunkalist.BuildConfig

object Constans{

    val API_MOVIE_SERIES_ANIME_BASE_URL = "https://api.themoviedb.org/3/"

    val API_MOVIE_SERIES_ANIME_BASE_URL_IMG_PATH_POSTER = "https://image.tmdb.org/t/p/w780"
    val API_MOVIE_SERIES_ANIME_BASE_URL_IMG_PATH_BACKDROP = "https://image.tmdb.org/t/p/w1280"


    val YOUTUBE_VIDEO_URL = "http://www.youtube.com/watch?v=%s"
    val YOUTUBE_THUMBNAIL_URL = "http://img.youtube.com/vi/%s/0.jpg"

    const val API_KEY = "7bcf40aff5d7be80e294d763234a6930"

    val requestToken = BuildConfig.movieandseriesApiRequestID

    val sessionId = BuildConfig.sesionIDMovieDatatbase

    // list fragment
    val TYPE_LIST = "LIST_TYPE"
    val MOVIE_LIST = 0
    val SERIE_LIST = 1
    val ANIME_LIST = 2

    // top fragment

    val TYPE_TOP = "TOP_TYPE"
    val MOVIE_TOP = 3
    val SERIE_TOP = 4
    val ANIME_TOP = 5


    // fragment follows
    val USER_PROFILE = "userProfileNewInstance"

    val USER_FOLLOW = "USER_FOLLOW"

    val USER_LIST_FOLLOWS = 1
    val USER_LIST_FOLLOWERS = 2


    // fragment timeline
    val personal = "Personal"

    val TIMELINE_GLOBAL = 1
    val TIMELINE_PERSONAL = 2

    // list fragment
    val TYPE_LIST_TOP_MOVIES = "LIST_TYPE_TOP_MOVIES"
    val TYPE_LIST_TOP_SERIES = "LIST_TYPE_TOP_SERIES"
    val Popular_LIST = "popular"
    val Rated_LIST = "rated"
    val Upcoming_LIST = "upcoming"

    val Popular_LIST_Series = 3
    val Rated_LIST_Series = 4
    val Upcoming_LIST_Series = 5









}