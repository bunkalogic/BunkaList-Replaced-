package com.bunkalogic.bunkalist.Activities.UserProfileActivities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bunkalogic.bunkalist.Fragments.ListProfileFragment
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
        setUpFragment()
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

    private fun setUpFragment(){
        supportFragmentManager
            .beginTransaction()
            .add(R.id.containerList, ListProfileFragment())
            .commit()

    }


}
