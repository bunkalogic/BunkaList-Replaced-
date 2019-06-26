package com.bunkalogic.bunkalist.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
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