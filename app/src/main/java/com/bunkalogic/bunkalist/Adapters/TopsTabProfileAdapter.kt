package com.bunkalogic.bunkalist.Adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import com.bunkalogic.bunkalist.Fragments.ListProfileFragment
import com.bunkalogic.bunkalist.Others.Constans
import org.jetbrains.anko.toast

class TopsTabProfileAdapter(fm: FragmentManager, private var totalTabs: Int): FragmentPagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                ListProfileFragment.newInstance(Constans.MOVIE_TOP)
            }

            1 ->{
                ListProfileFragment.newInstance(Constans.SERIE_TOP)
            }

            else -> ListProfileFragment.newInstance(Constans.ANIME_TOP)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }

}