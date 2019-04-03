package com.bunkalogic.bunkalist.Adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import com.bunkalogic.bunkalist.Fragments.ListProfileFragment
import com.bunkalogic.bunkalist.Others.Constans
import org.jetbrains.anko.toast

class ListTabProfileAdapter(fm: FragmentManager, private var totalTabs: Int): FragmentPagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                ListProfileFragment.newInstance(Constans.MOVIE_LIST)
            }

            1 ->{
                ListProfileFragment.newInstance(Constans.SERIE_LIST)
            }

            2 ->{
                ListProfileFragment.newInstance(Constans.ANIME_LIST)
            }

            else -> ListProfileFragment.newInstance(Constans.ALL_LIST)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }

}