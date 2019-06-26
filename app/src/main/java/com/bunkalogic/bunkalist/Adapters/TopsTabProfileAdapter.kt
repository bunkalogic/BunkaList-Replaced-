package com.bunkalogic.bunkalist.Adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import android.util.Log
import com.bunkalogic.bunkalist.Fragments.ListProfileFragment
import com.bunkalogic.bunkalist.Others.Constans
import org.jetbrains.anko.toast

class TopsTabProfileAdapter(fm: androidx.fragment.app.FragmentManager, private var totalTabs: Int): androidx.fragment.app.FragmentPagerAdapter(fm){

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
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