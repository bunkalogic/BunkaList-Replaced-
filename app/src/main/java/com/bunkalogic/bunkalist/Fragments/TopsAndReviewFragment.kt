package com.bunkalogic.bunkalist.Fragments


import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bunkalogic.bunkalist.Adapters.TopsOuevreAdapter
import com.bunkalogic.bunkalist.Models.TopsCardItem
import com.bunkalogic.bunkalist.Others.Constans

import com.bunkalogic.bunkalist.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.fragment_topandreview.view.*


/**
 *  Created by @author Naim Dridi on 25/02/19
 */

class TopsAndReviewFragment : androidx.fragment.app.Fragment() {

    private lateinit var _view: View
    lateinit var mAdView : AdView

    private lateinit var adapterMovies: TopsOuevreAdapter
    private lateinit var adapterSeries: TopsOuevreAdapter
    private lateinit var adapterAnime: TopsOuevreAdapter

    val listTopMovies: ArrayList<TopsCardItem> by lazy { getMoviesCardItem() }
    val listTopSeries: ArrayList<TopsCardItem> by lazy { getSeriesCardItem() }
    val listTopAnime: ArrayList<TopsCardItem> by lazy { getAnimeCardItem() }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _view = inflater.inflate(R.layout.fragment_topandreview, container, false)

        addBannerAds()
        setUpContainerMovies()
        setUpContainerSeries()
        setUpContainerAnime()

        return _view
    }


    //initializing the banner in this activity
    private fun addBannerAds(){
        mAdView = _view.findViewById(R.id.adViewBannerTopsReview)
        // Ad request to show on the banner
        val adRequest = AdRequest.Builder().build()
        // Associate the request to the banner
        mAdView.loadAd(adRequest)

    }

    private fun setUpContainerMovies(){
       _view.ContainerTopsMovies.removeAllViews()

        val layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        adapterMovies = TopsOuevreAdapter(context!!, listTopMovies)


        _view.ContainerTopsMovies.layoutManager = layoutManager
        _view.ContainerTopsMovies.setHasFixedSize(true)
        _view.ContainerTopsMovies.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        _view.ContainerTopsMovies.adapter = adapterMovies
    }


    private fun getMoviesCardItem(): ArrayList<TopsCardItem> {
        return object: ArrayList<TopsCardItem>(){
            init {
                add(TopsCardItem(
                    Constans.Popular_LIST_Movies,
                    getString(R.string.fragment_tops_review_button_top_movies_popular),
                    "https://image.tmdb.org/t/p/original/A2oPzeKjSpCovYLqoDDXFbrJgyS.jpg",
                    "https://image.tmdb.org/t/p/original/p3lkc1fDBeX9ZiIQVwRtOnXYENL.jpg",
                    "https://image.tmdb.org/t/p/original/dihW2yTsvQlust7mSuAqJDtqW7k.jpg",
                    "https://image.tmdb.org/t/p/original/jEjyjX4izUucoq7A9jmbcT6q3e1.jpg"
                ))
                add(TopsCardItem(
                    Constans.Rated_LIST_Movies,
                    getString(R.string.fragment_tops_review_button_top_movies_rated),
                    "https://image.tmdb.org/t/p/original/nnMC0BM6XbjIIrT4miYmMtPGcQV.jpg",
                    "https://image.tmdb.org/t/p/original/6xKCYgH16UuwEGAyroLU6p8HLIn.jpg",
                    "https://image.tmdb.org/t/p/original/8BPZO0Bf8TeAy8znF43z8soK3ys.jpg",
                    "https://image.tmdb.org/t/p/original/s2bT29y0ngXxxu2IA8AOzzXTRhd.jpg"
                ))
                add(TopsCardItem(
                    Constans.Upcoming_LIST_Movies,
                    getString(R.string.fragment_tops_review_button_top_movies_upcoming),
                    "https://image.tmdb.org/t/p/original/1TUg5pO1VZ4B0Q1amk3OlXvlpXV.jpg",
                    "https://image.tmdb.org/t/p/original/jZwGBRk16SSlcAt5t5AIU9sUEyl.jpg",
                    "https://image.tmdb.org/t/p/original/qy74lwIBCj50Klz8UskPu0vKy0t.jpg",
                    "https://image.tmdb.org/t/p/original/ddV9cFT75AHxGmJy9QRtjuE1V35.jpg"

                ))
            }
        }
    }

    private fun setUpContainerSeries(){
        _view.ContainerTopsSeries.removeAllViews()

        val layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        adapterSeries = TopsOuevreAdapter(context!!, listTopSeries)


        _view.ContainerTopsSeries.layoutManager = layoutManager
        _view.ContainerTopsSeries.setHasFixedSize(true)
        _view.ContainerTopsSeries.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        _view.ContainerTopsSeries.adapter = adapterSeries
    }

    private fun getSeriesCardItem(): ArrayList<TopsCardItem> {
        return object: ArrayList<TopsCardItem>(){
            init {
                add(TopsCardItem(
                    Constans.Popular_LIST_Series,
                    getString(R.string.fragment_tops_review_button_top_series_popular),
                    "https://image.tmdb.org/t/p/original/rcA17r3hfHtRrk3Xs3hXrgGeSGT.jpg",
                    "https://image.tmdb.org/t/p/original/51z0nVsJ42kxvlb0ITXdAvmaAqu.jpg",
                    "https://image.tmdb.org/t/p/original/omZbhqVDpXsfGMFUyxIjh1huqD.jpg",
                    "https://image.tmdb.org/t/p/original/60Mb7iALmmfqmBz3DFuKIlifDsI.jpg"
                ))
                add(TopsCardItem(
                    Constans.Rated_LIST_Series,
                    getString(R.string.fragment_tops_review_button_top_series_rated),
                    "https://image.tmdb.org/t/p/original/eSzpy96DwBujGFj0xMbXBcGcfxX.jpg",
                    "https://image.tmdb.org/t/p/original/3BXWBm011oMqNniZX5BBymC5q5m.jpg",
                    "https://image.tmdb.org/t/p/original/gX8SYlnL9ZznfZwEH4KJUePBFUM.jpg",
                    "https://image.tmdb.org/t/p/original/9d9Zon66ADvWmHBiiFtHA7FpPJT.jpg"
                ))
                add(TopsCardItem(
                    Constans.Upcoming_LIST_Series,
                    getString(R.string.fragment_tops_review_button_top_series_upcoming),
                    "https://image.tmdb.org/t/p/original/wxVtatnNDJtWRavoWIH7ykHfp4w.jpg",
                    "https://image.tmdb.org/t/p/original/oE3liWJk5j8jBRH0id2IciCPCJf.jpg",
                    "https://image.tmdb.org/t/p/original/yU2ROuLPJnm1dFw3cWs1PzcjGfU.jpg",
                    "https://image.tmdb.org/t/p/original/aPXGLWZql1xsO9Dd3JD9f2VXdj8.jpg"

                ))
            }
        }
    }

    private fun setUpContainerAnime(){
        _view.ContainerTopsAnime.removeAllViews()

        val layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        adapterAnime = TopsOuevreAdapter(context!!, listTopAnime)


        _view.ContainerTopsAnime.layoutManager = layoutManager
        _view.ContainerTopsAnime.setHasFixedSize(true)
        _view.ContainerTopsAnime.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        _view.ContainerTopsAnime.adapter = adapterAnime
    }

    private fun getAnimeCardItem(): ArrayList<TopsCardItem> {
        return object: ArrayList<TopsCardItem>(){
            init {
                add(TopsCardItem(
                    Constans.Popular_LIST_Anime,
                    getString(R.string.fragment_tops_review_button_top_anime_popular),
                    "https://image.tmdb.org/t/p/original/nTvM4mhqNlHIvUkI1gVnW6XP7GG.jpg",
                    "https://image.tmdb.org/t/p/original/zDXuwmqkTi2lGM4AyloAAX4v51P.jpg",
                    "https://image.tmdb.org/t/p/original/vNr7BQHryOfnxXztN7UNy0im9sg.jpg",
                    "https://image.tmdb.org/t/p/original/1Ep6YHL5QcrNC1JN6RYalWRPopi.jpg"
                ))
                add(TopsCardItem(
                    Constans.Rated_LIST_Anime,
                    getString(R.string.fragment_tops_review_button_top_anime_rated),
                    "https://image.tmdb.org/t/p/original/pL2VkIcoHnyX5oLd3IIaANkzB01.jpg",
                    "https://image.tmdb.org/t/p/original/qtmlwuXj0VyJnCjnrvdpDjo15vI.jpg",
                    "https://image.tmdb.org/t/p/original/6G55oomDYY3zhfUjwpFB37vIo9Y.jpg",
                    "https://image.tmdb.org/t/p/original/mQzmkgGYpyMNRvQx9bljEykfnKc.jpg"
                ))
                add(TopsCardItem(
                    Constans.Upcoming_LIST_Anime,
                    getString(R.string.fragment_tops_review_button_top_anime_upcoming),
                    "https://image.tmdb.org/t/p/original/3ILMlmC30QUnYkY3XEBOyJ82Dqu.jpg",
                    "https://image.tmdb.org/t/p/original/xjKEqDce8xG80nCmC47qvstfo4M.jpg",
                    "https://image.tmdb.org/t/p/original/pk9QWagOCXcn72L2oFGadOYNJsc.jpg",
                    "https://image.tmdb.org/t/p/original/xMaImeJgreGxcdK9Gz2EKExdM66.jpg"

                ))
            }
        }
    }



}
