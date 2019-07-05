package com.bunkalogic.bunkalist.Others

import android.util.ArrayMap
import com.bunkalogic.bunkalist.BuildConfig

object Constans{

    val API_MOVIE_SERIES_ANIME_BASE_URL = "https://api.themoviedb.org/3/"

    val API_MOVIE_SERIES_ANIME_BASE_URL_IMG_PATH_POSTER = "https://image.tmdb.org/t/p/w780"
    val API_MOVIE_SERIES_ANIME_BASE_URL_IMG_PATH_BACKDROP = "https://image.tmdb.org/t/p/w1280"
    val API_MOVIE_SERIES_ANIME_BASE_URL_IMG_PROFILE = "https://image.tmdb.org/t/p/original"


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
    val typeOuevre = "typeOeuvre"

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

    // id top's movies
    val Popular_LIST_Movies = 1
    val Rated_LIST_Movies = 2
    val Upcoming_LIST_Movies = 3

    // id top's series
    val Popular_LIST_Series = 4
    val Rated_LIST_Series = 5
    val Upcoming_LIST_Series = 6

    // id top's anime
    val Popular_LIST_Anime = 7
    val Rated_LIST_Anime = 8
    val Upcoming_LIST_Anime = 9

    // filter id status
    val filter_status_name = "status"
    val filter_status_complete = 0
    val filter_status_watching = 1
    val filter_status_waiting = 2
    val filter_status_pause = 3
    val filter_status_dropped = 4


    // filter id rating
    val filter_rating_story = 5
    val filter_rating_characters = 6
    val filter_rating_soundtrack = 7
    val filter_rating_photography = 8
    val filter_rating_enjoyment = 9
    val filter_rating_final = 10

    // filter name rating
    val filter_rating_story_name = "historyRate"
    val filter_rating_characters_name = "characterRate"
    val filter_rating_soundtrack_name = "soundtrackRate"
    val filter_rating_photography_name = "effectsRate"
    val filter_rating_enjoyment_name = "enjoymentRate"
    val filter_rating_final_name = "finalRate"


    // filter id order
    val filter_order_ascendant = 11
    val filter_order_descend = 12

    var applied_list_filter: ArrayMap<String, Int> = ArrayMap()


    var applied_search_filter: ArrayMap<String, MutableList<Any>> = ArrayMap()

    val SEARCH_NAME = "SEARCH_TEXT"

    // filter search id type
    val filter_search_type_movie_id = 1
    val filter_search_type_series_id = 2
    val filter_search_type_anime_id = 3

    // filter search id genres Movies
    val filter_search_genres_movies_action = 28
    val filter_search_genres_movies_adventure = 12
    val filter_search_genres_movies_animation = 16
    val filter_search_genres_movies_comedy = 35
    val filter_search_genres_movies_crime = 80
    val filter_search_genres_movies_documentary = 99
    val filter_search_genres_movies_drama = 18
    val filter_search_genres_movies_family = 10751
    val filter_search_genres_movies_fantasy = 14
    val filter_search_genres_movies_history = 36
    val filter_search_genres_movies_horror = 27
    val filter_search_genres_movies_music = 10402
    val filter_search_genres_movies_mistery = 9648
    val filter_search_genres_movies_romance = 10749
    val filter_search_genres_movies_scince_fiction = 878
    //val filter_search_genres_movies_tv = 10770
    val filter_search_genres_movies_thriller = 53
    val filter_search_genres_movies_war = 10752
    val filter_search_genres_movies_western = 37

    // filter search id genres Series
    val filter_search_genres_series_action_adventure = 10759
    // Id de genres repeat
    //val filter_search_genres_series_animation = 16
    //val filter_search_genres_series_comedy = 35
    //val filter_search_genres_series_crime = 80
    //val filter_search_genres_series_documentary = 99
    //val filter_search_genres_series_drama = 18
    //val filter_search_genres_series_family = 10751
    //val filter_search_genres_series_mistery = 9648
    //val filter_search_genres_series_romance = 10749
    val filter_search_genres_series_reality = 10764
    val filter_search_genres_series_scince_fiction_fantasy = 10765
    val filter_search_genres_series_telenovel = 10766
    val filter_search_genres_series_war_politics = 10768
    //val filter_search_genres_series_western = 37

    // filter search id year
    val filter_search_year_1900 = 1900
    val filter_search_year_1901 = 1901
    val filter_search_year_1902 = 1902
    val filter_search_year_1903 = 1903
    val filter_search_year_1904 = 1904
    val filter_search_year_1905 = 1905
    val filter_search_year_1906 = 1906
    val filter_search_year_1907 = 1907
    val filter_search_year_1908 = 1908
    val filter_search_year_1909 = 1909
    val filter_search_year_1910 = 1910
    val filter_search_year_1911 = 1911
    val filter_search_year_1912 = 1912
    val filter_search_year_1913 = 1913
    val filter_search_year_1914 = 1914
    val filter_search_year_1915 = 1915
    val filter_search_year_1916 = 1916
    val filter_search_year_1917 = 1917
    val filter_search_year_1918 = 1918
    val filter_search_year_1919 = 1919
    val filter_search_year_1920 = 1920
    val filter_search_year_1921 = 1921
    val filter_search_year_1922 = 1922
    val filter_search_year_1923 = 1923
    val filter_search_year_1924 = 1924
    val filter_search_year_1925 = 1925
    val filter_search_year_1926 = 1926
    val filter_search_year_1927 = 1927
    val filter_search_year_1928 = 1928
    val filter_search_year_1929 = 1929
    val filter_search_year_1930 = 1930
    val filter_search_year_1931 = 1931
    val filter_search_year_1932 = 1932
    val filter_search_year_1933 = 1933
    val filter_search_year_1934 = 1934
    val filter_search_year_1935 = 1935
    val filter_search_year_1936 = 1936
    val filter_search_year_1937 = 1937
    val filter_search_year_1938 = 1938
    val filter_search_year_1939 = 1939
    val filter_search_year_1940 = 1940
    val filter_search_year_1941 = 1941
    val filter_search_year_1942 = 1942
    val filter_search_year_1943 = 1943
    val filter_search_year_1944 = 1944
    val filter_search_year_1945 = 1945
    val filter_search_year_1946 = 1946
    val filter_search_year_1947 = 1947
    val filter_search_year_1948 = 1948
    val filter_search_year_1949 = 1949
    val filter_search_year_1950 = 1950
    val filter_search_year_1951 = 1951
    val filter_search_year_1952 = 1952
    val filter_search_year_1953 = 1953
    val filter_search_year_1954 = 1954
    val filter_search_year_1955 = 1955
    val filter_search_year_1956 = 1956
    val filter_search_year_1957 = 1957
    val filter_search_year_1958 = 1958
    val filter_search_year_1959 = 1959
    val filter_search_year_1960 = 1960
    val filter_search_year_1961 = 1961
    val filter_search_year_1962 = 1962
    val filter_search_year_1963 = 1963
    val filter_search_year_1964 = 1964
    val filter_search_year_1965 = 1965
    val filter_search_year_1966 = 1966
    val filter_search_year_1967 = 1967
    val filter_search_year_1968 = 1968
    val filter_search_year_1969 = 1969
    val filter_search_year_1970 = 1970
    val filter_search_year_1971 = 1971
    val filter_search_year_1972 = 1972
    val filter_search_year_1973 = 1973
    val filter_search_year_1974 = 1974
    val filter_search_year_1975 = 1975
    val filter_search_year_1976 = 1976
    val filter_search_year_1977 = 1977
    val filter_search_year_1978 = 1978
    val filter_search_year_1979 = 1979
    val filter_search_year_1980 = 1980
    val filter_search_year_1981 = 1981
    val filter_search_year_1982 = 1982
    val filter_search_year_1983 = 1983
    val filter_search_year_1984 = 1984
    val filter_search_year_1985 = 1985
    val filter_search_year_1986 = 1986
    val filter_search_year_1987 = 1987
    val filter_search_year_1988 = 1988
    val filter_search_year_1989 = 1989
    val filter_search_year_1990 = 1990
    val filter_search_year_1991 = 1991
    val filter_search_year_1992 = 1992
    val filter_search_year_1993 = 1993
    val filter_search_year_1994 = 1994
    val filter_search_year_1995 = 1995
    val filter_search_year_1996 = 1996
    val filter_search_year_1997 = 1997
    val filter_search_year_1998 = 1998
    val filter_search_year_1999 = 1999
    val filter_search_year_2000 = 2000
    val filter_search_year_2001 = 2001
    val filter_search_year_2002 = 2002
    val filter_search_year_2003 = 2003
    val filter_search_year_2004 = 2004
    val filter_search_year_2005 = 2005
    val filter_search_year_2006 = 2006
    val filter_search_year_2007 = 2007
    val filter_search_year_2008 = 2008
    val filter_search_year_2009 = 2009
    val filter_search_year_2010 = 2010
    val filter_search_year_2011 = 2011
    val filter_search_year_2012 = 2012
    val filter_search_year_2013 = 2013
    val filter_search_year_2014 = 2014
    val filter_search_year_2015 = 2015
    val filter_search_year_2016 = 2016
    val filter_search_year_2017 = 2017
    val filter_search_year_2018 = 2018
    val filter_search_year_2019 = 2019


    // filter search id sort movies
    val filter_search_sort_populary_asc = "popularity.asc"
    val filter_search_sort_populary_desc = "popularity.desc"
    val filter_search_sort_release_date_asc = "release_date.asc"
    val filter_search_sort_release_date_desc = "release_date.desc"
    val filter_search_sort_voted_average_asc = "vote_average.asc"
    val filter_search_sort_voted_average_desc = "vote_average.desc"

    // filter search id sort series
    val filter_search_sort_series_first_air_date_asc = "first_air_date.asc"
    val filter_search_sort_series_first_air_date_desc= "first_air_date.desc"



















}