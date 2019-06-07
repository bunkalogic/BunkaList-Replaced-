package com.bunkalogic.bunkalist.Activities.UserProfileActivities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.util.ArrayMap
import android.util.Log
import com.bunkalogic.bunkalist.Adapters.ListTabProfileAdapter
import com.bunkalogic.bunkalist.Adapters.TopsTabProfileAdapter
import com.bunkalogic.bunkalist.Fragments.FabFilter.FabFilterListFragment
import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.bunkalogic.bunkalist.Fragments.ListProfileFragment
import com.bunkalogic.bunkalist.R
import kotlinx.android.synthetic.main.activity_profile_list.*



class ProfileListActivity : AppCompatActivity(){


    private lateinit var toolbar: android.support.v7.widget.Toolbar

    val clickList = 1
    val clickTop = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_list)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        isListOrTop()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    private fun isListOrTop(){
        val list = intent.extras?.getInt("list")
        val top = intent.extras?.getInt("top")

        if (list == clickList){
            supportActionBar!!.title = getString(R.string.btn_list_all_movies)
            setUpTabLayoutList()

        }else if(top == clickTop){
            supportActionBar!!.title = getString(R.string.btn_top_movie)
            setUpTabLayoutTops()

        }
    }


    private fun setUpTabLayoutList(){
        val tabLayoutFilter = findViewById<TabLayout>(R.id.tabLayoutFilter)

        tabLayoutFilter.addTab(tabLayoutFilter.newTab().setText(R.string.profile_list_activity_tab_movie))
        tabLayoutFilter.addTab(tabLayoutFilter.newTab().setText(R.string.profile_list_activity_tab_serie))
        tabLayoutFilter.addTab(tabLayoutFilter.newTab().setText(R.string.profile_list_activity_tab_anime))

        tabLayoutFilter.tabGravity = TabLayout.GRAVITY_FILL

        val Adapter = ListTabProfileAdapter(supportFragmentManager, tabLayoutFilter.tabCount)
        viewPagerFilter.adapter = Adapter

        viewPagerFilter.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayoutFilter))

        tabLayoutFilter.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                var position = tab!!.position
                viewPagerFilter.currentItem = position
                Log.d("ProfileListActivity", "List: $position")
            }

        })

    }


    private fun setUpTabLayoutTops(){
        val tabLayoutFilter = findViewById<TabLayout>(R.id.tabLayoutFilter)

        tabLayoutFilter.addTab(tabLayoutFilter.newTab().setText(R.string.profile_list_activity_tab_movie))
        tabLayoutFilter.addTab(tabLayoutFilter.newTab().setText(R.string.profile_list_activity_tab_serie))
        tabLayoutFilter.addTab(tabLayoutFilter.newTab().setText(R.string.profile_list_activity_tab_anime))

        tabLayoutFilter.tabGravity = TabLayout.GRAVITY_FILL

        val Adapter = TopsTabProfileAdapter(supportFragmentManager, tabLayoutFilter.tabCount)
        viewPagerFilter.adapter = Adapter

        viewPagerFilter.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayoutFilter))

        tabLayoutFilter.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                var position = tab!!.position
                viewPagerFilter.currentItem = position
                Log.d("ProfileListActivity", "Tops: $position")
            }

        })

    }













}
