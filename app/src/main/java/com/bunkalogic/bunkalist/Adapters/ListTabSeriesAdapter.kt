package com.bunkalogic.bunkalist.Adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import com.bunkalogic.bunkalist.Fragments.ListProfileFragment
import com.bunkalogic.bunkalist.Fragments.ListSeriesFragment
import com.bunkalogic.bunkalist.Others.Constans
import org.jetbrains.anko.toast

class ListTabSeriesAdapter(fm: FragmentManager, private var totalTabs: Int): FragmentPagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                ListSeriesFragment.newInstance(Constans.Popular_LIST)
            }

            1 ->{
                ListSeriesFragment.newInstance(Constans.Rated_LIST)
            }

            else -> ListSeriesFragment.newInstance(Constans.Upcoming_LIST)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }

}