package com.bunkalogic.bunkalist.Adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import com.bunkalogic.bunkalist.Fragments.TimeLineFragment
import com.bunkalogic.bunkalist.Others.Constans
import kotlinx.coroutines.channels.consumesAll

/**
class TimeLineTabAdapter(fm: FragmentManager, private var totalTabs: Int): FragmentPagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                TimeLineFragment.newInstance(Constans.TIMELINE_GLOBAL)
            }

            else -> TimeLineFragment.newInstance(Constans.TIMELINE_PERSONAL)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }

}
        **/