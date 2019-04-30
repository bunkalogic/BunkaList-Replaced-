package com.bunkalogic.bunkalist.Adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.bunkalogic.bunkalist.Fragments.ListSeriesFragment
import com.bunkalogic.bunkalist.Others.Constans

class ListTabSeriesAdapter(fm: FragmentManager, private var totalTabs: Int): FragmentPagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                ListSeriesFragment.newInstance(Constans.Popular_LIST_Series)
            }

            1 ->{
                ListSeriesFragment.newInstance(Constans.Rated_LIST_Series)
            }

            else -> ListSeriesFragment.newInstance(Constans.Upcoming_LIST_Series)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }

}