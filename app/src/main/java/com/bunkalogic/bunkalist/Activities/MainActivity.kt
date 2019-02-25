package com.bunkalogic.bunkalist.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.bunkalogic.bunkalist.Adapters.PagerAdapter
import android.view.MenuItem
import com.bunkalogic.bunkalist.Fragments.*
import com.bunkalogic.bunkalist.R
import kotlinx.android.synthetic.main.activity_main.*


/**
 *  Created by @author Naim Dridi on 18/02/19
 */

class MainActivity : AppCompatActivity() {


    private var prevBottomSelected: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpViewPager(getPagerAdapter())
        setUpBottomNavigationBar()
    }

    // Collect all fragment implements in the adapter
    private fun getPagerAdapter(): PagerAdapter{
        val adapter = PagerAdapter(supportFragmentManager)
        adapter.addFragment(TimeLineFragment())
        adapter.addFragment(ChatFragment())
        adapter.addFragment(SearchFragment())
        adapter.addFragment(ProfileFragment())
        adapter.addFragment(SettingsFragment())

        return adapter
    }

    private fun setUpViewPager(adapter: PagerAdapter){
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = adapter.count
        viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(position: Int) {

                if (prevBottomSelected == null){
                    btn_nav_View.menu.getItem(0).isChecked = false
                }else{
                    prevBottomSelected!!.isChecked = false
                }
                btn_nav_View.menu.getItem(position).isChecked = true
                prevBottomSelected = btn_nav_View.menu.getItem(position)

            }

        })
    }

    private fun setUpBottomNavigationBar(){
        btn_nav_View.setOnNavigationItemSelectedListener {Item ->
            when(Item.itemId){
                R.id.bottom_nav_timeline -> {
                    viewPager.currentItem = 0; true
                }

                R.id.bottom_nav_chat -> {
                    viewPager.currentItem = 1; true
                }

                R.id.bottom_nav_search -> {
                    viewPager.currentItem = 2; true
                }

                R.id.bottom_nav_profile -> {
                    viewPager.currentItem = 3; true
                }

                R.id.bottom_nav_settings -> {
                    viewPager.currentItem = 4; true
                }
                else -> false
            }
        }
    }








}
