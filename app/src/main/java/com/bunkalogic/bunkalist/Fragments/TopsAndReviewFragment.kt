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
                add(TopsCardItem(Constans.Popular_LIST_Movies, getString(R.string.fragment_tops_review_button_top_movies_popular), R.drawable.topspopularmovies))
                add(TopsCardItem(Constans.Rated_LIST_Movies, getString(R.string.fragment_tops_review_button_top_movies_rated), R.drawable.topsratedmovies))
                add(TopsCardItem(Constans.Upcoming_LIST_Movies, getString(R.string.fragment_tops_review_button_top_movies_upcoming),R.drawable.topsupcomingmovies))
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
                add(TopsCardItem(Constans.Popular_LIST_Series, getString(R.string.fragment_tops_review_button_top_series_popular), R.drawable.topspopularseries))
                add(TopsCardItem(Constans.Rated_LIST_Series, getString(R.string.fragment_tops_review_button_top_series_rated), R.drawable.topsratedseries))
                add(TopsCardItem(Constans.Upcoming_LIST_Anime, getString(R.string.fragment_tops_review_button_top_series_upcoming), R.drawable.topupcomingseries))
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
                add(TopsCardItem(Constans.Popular_LIST_Anime, getString(R.string.fragment_tops_review_button_top_anime_popular), R.drawable.topspopularanime))
                add(TopsCardItem(Constans.Rated_LIST_Anime, getString(R.string.fragment_tops_review_button_top_anime_rated), R.drawable.topsratedanime))
                add(TopsCardItem(Constans.Upcoming_LIST_Anime, getString(R.string.fragment_tops_review_button_top_anime_upcoming), R.drawable.topsupcominganime))
            }
        }
    }



}
