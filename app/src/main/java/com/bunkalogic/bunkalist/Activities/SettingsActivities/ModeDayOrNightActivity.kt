package com.bunkalogic.bunkalist.Activities.SettingsActivities


import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import com.bunkalogic.bunkalist.Activities.OtherActivities.ToolbarActivity
import com.bunkalogic.bunkalist.BuildConfig
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.SharedPreferences.preferences
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.activity_mode_day_or_night.*


/**
 *  Created by @author Naim dridi on 02/03/19
 */

class ModeDayOrNightActivity : ToolbarActivity() {

    private lateinit var toolbar: android.support.v7.widget.Toolbar

    private val MODE_DAY = 1
    private val MODE_NIGHT = 2
    private val MODE_CUSTOM = 3

    lateinit var mAdView : AdView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mode_day_or_night)
        //Instantiate the toolbar
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        addBannerAds()
        //onRadioButtonClicked()
        clicksListener()
    }

    //initializing the banner in this activity
    private fun addBannerAds(){
        mAdView = findViewById(R.id.adViewBannerActivityMode)
        // Ad request to show on the banner
        val adRequest = AdRequest.Builder().build()
        // Associate the request to the banner
        mAdView.loadAd(adRequest)
    }


    // function that contain the clickListener of elements of the graphical interface
    private fun clicksListener(){

        radioButtonLight.setOnClickListener {
            preferences.mode = MODE_DAY
            preferences.editCurrentUser()
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        radioButtonDark.setOnClickListener {
            preferences.mode = MODE_NIGHT
            preferences.editCurrentUser()
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        radioButtonCustom.setOnClickListener {
            preferences.mode = MODE_CUSTOM
            preferences.editCurrentUser()
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO)
        }
    }
}
