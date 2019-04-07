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

    val TYPE_LIST = "LIST_TYPE"
    val MOVIE_LIST = 0
    val SERIE_LIST = 1
    val ANIME_LIST = 2
    val TOP_LIST = 3




}