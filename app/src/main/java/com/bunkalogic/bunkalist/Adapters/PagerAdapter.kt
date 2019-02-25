package com.bunkalogic.bunkalist.Adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.ArrayList

class PagerAdapter(fmanager: FragmentManager): FragmentPagerAdapter(fmanager){

    private val fragmentList = ArrayList<Fragment>()

    override fun getItem(position: Int) = fragmentList[position]

    override fun getCount() = fragmentList.size

    fun addFragment(fragment: Fragment) = fragmentList.add(fragment)

}