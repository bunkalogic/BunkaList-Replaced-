package com.bunkalogic.bunkalist.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bunkalogic.bunkalist.Fragments.ListMovieFragment
import com.bunkalogic.bunkalist.Others.Constans

class ListTabMoviesAdapter(fm: androidx.fragment.app.FragmentManager, private var totalTabs: Int): androidx.fragment.app.FragmentPagerAdapter(fm){

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
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