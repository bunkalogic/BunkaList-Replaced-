package com.bunkalogic.bunkalist.Adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.bunkalogic.bunkalist.Fragments.ListProfileFragment
import com.bunkalogic.bunkalist.Others.Constans

class ListTabProfileAdapter(val ctx: Context, fm: FragmentManager, internal var totalTabs: Int): FragmentPagerAdapter(fm){

    override fun getItem(position: Int): Fragment? {
        when(position){
            0 ->{
                return ListProfileFragment.newInstance(Constans.ALL_LIST)
            }
            1 ->{
                return ListProfileFragment.newInstance(Constans.MOVIE_LIST)
            }
            2 ->{
                return ListProfileFragment.newInstance(Constans.SERIE_LIST)
            }
            3 ->{
                return ListProfileFragment.newInstance(Constans.ANIME_LIST)
            }

            else -> return null
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }

}