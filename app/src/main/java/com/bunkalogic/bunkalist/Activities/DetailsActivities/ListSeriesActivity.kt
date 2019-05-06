package com.bunkalogic.bunkalist.Activities.DetailsActivities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.util.Log
import com.bunkalogic.bunkalist.Adapters.ListTabSeriesAdapter
import com.bunkalogic.bunkalist.Fragments.ListSeriesFragment
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.R
import kotlinx.android.synthetic.main.activity_list_series.*

class ListSeriesActivity : AppCompatActivity() {

    private lateinit var toolbar: android.support.v7.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_series)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        setUpTabLayoutList()

        supportActionBar!!.title  = getString(R.string.title_toolbar_tops_Series)

    }

    //private fun whatTypeListIs(){
    //    val extrasPopular = intent.extras?.getInt("popular")
    //    val extrasRated = intent.extras?.getInt("rated")
    //    val extrasUpcoming = intent.extras?.getInt("upcoming")
//
    //    when {
    //        extrasPopular == 0 -> ListSeriesFragment.newInstance(Constans.Popular_LIST)
    //        extrasRated == 1 -> ListSeriesFragment.newInstance(Constans.Rated_LIST)
    //        extrasUpcoming == 2 -> ListSeriesFragment.newInstance(Constans.Upcoming_LIST)
    //    }
//
    //}

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setUpTabLayoutList(){
        val tabLayoutFilter = findViewById<TabLayout>(R.id.tabLayoutListSeries)

        tabLayoutFilter.addTab(tabLayoutFilter.newTab().setText(R.string.profile_list_activity_tab_popular))
        tabLayoutFilter.addTab(tabLayoutFilter.newTab().setText(R.string.profile_list_activity_tab_rated))
        tabLayoutFilter.addTab(tabLayoutFilter.newTab().setText(R.string.profile_list_activity_tab_upcoming))

        tabLayoutFilter.tabGravity = TabLayout.GRAVITY_FILL

        val Adapter = ListTabSeriesAdapter(supportFragmentManager, tabLayoutFilter.tabCount)
        viewPagerListSeries.adapter = Adapter

        viewPagerListSeries.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayoutFilter))

        tabLayoutFilter.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                var position = tab!!.position
                viewPagerListSeries.currentItem = position
                Log.d("ListSeriesActivity", "List: $position")
            }

        })

    }
}
