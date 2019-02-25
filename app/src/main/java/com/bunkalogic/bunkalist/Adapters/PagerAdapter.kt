package com.bunkalogic.bunkalist.Adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.ArrayList

/**
 *  Created by @author Naim Dridi on 25/02/19
 */

class PagerAdapter(fmanager: FragmentManager): FragmentPagerAdapter(fmanager){

    private val fragmentList = ArrayList<Fragment>()

    override fun getItem(position: Int) = fragmentList[position]

    override fun getCount() = fragmentList.size

    fun addFragment(fragment: Fragment) = fragmentList.add(fragment)

}