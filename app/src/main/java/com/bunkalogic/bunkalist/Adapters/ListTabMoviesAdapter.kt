package com.bunkalogic.bunkalist.Adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.bunkalogic.bunkalist.Fragments.ListMovieFragment
import com.bunkalogic.bunkalist.Others.Constans

class ListTabMoviesAdapter(fm: FragmentManager, private var totalTabs: Int): FragmentPagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                ListMovieFragment.newInstance(Constans.Popular_LIST)
            }

            1 ->{
                ListMovieFragment.newInstance(Constans.Rated_LIST)
            }

            else -> ListMovieFragment.newInstance(Constans.Upcoming_LIST)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }

}