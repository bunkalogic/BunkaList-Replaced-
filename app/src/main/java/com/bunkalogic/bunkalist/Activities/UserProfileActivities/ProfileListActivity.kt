package com.bunkalogic.bunkalist.Activities.UserProfileActivities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import com.bunkalogic.bunkalist.Adapters.ListTabProfileAdapter
import com.bunkalogic.bunkalist.R
import kotlinx.android.synthetic.main.activity_profile_list.*

class ProfileListActivity : AppCompatActivity() {

    private lateinit var toolbar: android.support.v7.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_list)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setUpTablayout()
        onClick()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun onClick(){
        fabFilter.setOnClickListener {

        }
    }

    private fun setUpTablayout(){
        val tabLayoutFilter = findViewById<TabLayout>(R.id.tabLayoutFilter)
        tabLayoutFilter.addTab(tabLayoutFilter.newTab().setText(R.string.profile_list_activity_tab_all))
        tabLayoutFilter.addTab(tabLayoutFilter.newTab().setText(R.string.profile_list_activity_tab_movie))
        tabLayoutFilter.addTab(tabLayoutFilter.newTab().setText(R.string.profile_list_activity_tab_serie))
        tabLayoutFilter.addTab(tabLayoutFilter.newTab().setText(R.string.profile_list_activity_tab_anime))
        tabLayoutFilter.tabGravity = TabLayout.GRAVITY_FILL

        val Adapter = ListTabProfileAdapter(this, supportFragmentManager, tabLayoutFilter.tabCount)
        viewPagerFilter.adapter = Adapter

        viewPagerFilter.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayoutFilter))

        tabLayoutFilter.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPagerFilter.currentItem = tab!!.position
            }

        })

    }









}
