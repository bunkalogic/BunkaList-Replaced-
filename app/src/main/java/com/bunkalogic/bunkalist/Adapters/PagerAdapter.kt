package com.bunkalogic.bunkalist.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.ArrayList

/**
 *  Created by @author Naim Dridi on 25/02/19
 */

class PagerAdapter(fmanager: androidx.fragment.app.FragmentManager): androidx.fragment.app.FragmentPagerAdapter(fmanager){

    private val fragmentList = ArrayList<androidx.fragment.app.Fragment>()

    override fun getItem(position: Int) = fragmentList[position]

    override fun getCount() = fragmentList.size

    fun addFragment(fragment: androidx.fragment.app.Fragment) = fragmentList.add(fragment)

}