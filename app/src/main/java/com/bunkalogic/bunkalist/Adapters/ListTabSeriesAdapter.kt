package com.bunkalogic.bunkalist.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bunkalogic.bunkalist.Fragments.ListSeriesFragment
import com.bunkalogic.bunkalist.Others.Constans

class ListTabSeriesAdapter(fm: androidx.fragment.app.FragmentManager, private var totalTabs: Int): androidx.fragment.app.FragmentPagerAdapter(fm){

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
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