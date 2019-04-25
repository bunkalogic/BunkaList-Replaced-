package com.bunkalogic.bunkalist.Activities.DetailsActivities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.util.Log
import com.bunkalogic.bunkalist.Adapters.ListTabMoviesAdapter
import com.bunkalogic.bunkalist.Fragments.ListMovieFragment
import com.bunkalogic.bunkalist.Fragments.ListSeriesFragment
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.R
import kotlinx.android.synthetic.main.activity_list_movie.*
import kotlinx.android.synthetic.main.activity_list_series.*

class ListMovieActivity : AppCompatActivity() {

    private lateinit var toolbar: android.support.v7.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_movie)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setUpTabLayoutList()
        whatTypeListIs()
    }

    private fun whatTypeListIs(){
        val extrasPopular = intent.extras?.getInt("popular")
        val extrasRated = intent.extras?.getInt("rated")
        val extrasUpcoming = intent.extras?.getInt("upcoming")

        when {
            extrasPopular == 0 -> ListMovieFragment.newInstance(Constans.Popular_LIST)
            extrasRated == 1 -> ListMovieFragment.newInstance(Constans.Rated_LIST)
            extrasUpcoming == 2 -> ListMovieFragment.newInstance(Constans.Upcoming_LIST)
        }

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setUpTabLayoutList(){
        val tabLayoutFilter = findViewById<TabLayout>(R.id.tabLayoutListMovie)

        tabLayoutFilter.addTab(tabLayoutFilter.newTab().setText(R.string.profile_list_activity_tab_popular))
        tabLayoutFilter.addTab(tabLayoutFilter.newTab().setText(R.string.profile_list_activity_tab_rated))
        tabLayoutFilter.addTab(tabLayoutFilter.newTab().setText(R.string.profile_list_activity_tab_upcoming))

        tabLayoutFilter.tabGravity = TabLayout.GRAVITY_FILL

        val Adapter = ListTabMoviesAdapter(supportFragmentManager, tabLayoutFilter.tabCount)
        viewPagerListMovie.adapter = Adapter

        viewPagerListMovie.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayoutFilter))

        tabLayoutFilter.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                var position = tab!!.position
                viewPagerListMovie.currentItem = position
                Log.d("ListMoviesActivity", "List: $position")
            }

        })

    }
}
