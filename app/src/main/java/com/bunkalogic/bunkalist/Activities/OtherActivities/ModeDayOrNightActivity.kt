package com.bunkalogic.bunkalist.Activities.OtherActivities


import android.os.Bundle
import com.bunkalogic.bunkalist.R

/**
 *  Created by @author Naim dridi on 02/03/19
 */

class ModeDayOrNightActivity : ToolbarActivity() {

    private lateinit var toolbar: android.support.v7.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mode_day_or_night)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)




    }
}
