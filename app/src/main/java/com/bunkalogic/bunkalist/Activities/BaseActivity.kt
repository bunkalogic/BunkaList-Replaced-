package com.bunkalogic.bunkalist.Activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.bunkalogic.bunkalist.Adapters.PagerAdapter
import com.bunkalogic.bunkalist.BuildConfig
import com.bunkalogic.bunkalist.Fragments.*
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.R.color.colorBackgroundPrimary
import com.bunkalogic.bunkalist.SharedPreferences.preferences
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.InstallState










/**
 *  Created by @author Naim Dridi on 18/02/19
 */

class BaseActivity : AppCompatActivity() {

    private var prevBottomSelected: MenuItem? = null

    private var RC_APP_UPDATE = 25

    private lateinit var appUpdateManager : AppUpdateManager
    private var installStateUpdatedListener : InstallStateUpdatedListener? = null

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var currentUser: FirebaseUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        whatIsMode()
        setUpViewPager(getPagerAdapter())
        setUpBottomNavigationBar()
        setUpCurrentUser()
        checkNewUpdates()



    }

    // Initializing the currentUser
    private fun setUpCurrentUser(){
        currentUser = mAuth.currentUser!!
    }


    // Collect all fragment implements in the adapter
    private fun getPagerAdapter(): PagerAdapter{
        val adapter = PagerAdapter(supportFragmentManager)
        adapter.addFragment(TimeLineFragment())
        adapter.addFragment(TopsAndReviewFragment())
        adapter.addFragment(SearchFragment())
        adapter.addFragment(ProfileFragment())
        adapter.addFragment(SettingsFragment())

        return adapter
    }

    private fun setUpViewPager(adapter: PagerAdapter){
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = adapter.count
        viewPager.addOnPageChangeListener(object: androidx.viewpager.widget.ViewPager.OnPageChangeListener{
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
                    viewPager.currentItem = 0;true
                }

                R.id.bottom_nav_top_and_review -> {
                    viewPager.currentItem = 1;true
                }

                R.id.bottom_nav_search -> {

                    viewPager.currentItem = 2;true

                }

                R.id.bottom_nav_profile -> {
                    viewPager.currentItem = 3;true
                }

                R.id.bottom_nav_settings -> {
                    viewPager.currentItem = 4;true
                }
                else -> false
            }
        }
    }

    // Check if the user has chosen some theme mode in the ModeDayOrNightActivity
    private fun whatIsMode(){
        val mode_light = 1
        val mode_dark = 2
        val mode_custom = 3
        if (preferences.mode == mode_light){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.statusBarColor = resources.getColor(colorBackgroundPrimary, theme)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
        if (preferences.mode == mode_dark){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.statusBarColor = resources.getColor(colorBackgroundPrimary, theme)
            }
        }
        if (preferences.mode == mode_custom){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO)
        }
        AppCompatDelegate.getDefaultNightMode()

    }

    // Check if there are any updates available
    private fun checkNewUpdates(){
        appUpdateManager = AppUpdateManagerFactory.create(this)

        appUpdateManager.registerListener(installStateUpdatedListener)// Error: that was originally registered here. Are you missing a call to unregisterReceiver()?

        appUpdateManager.appUpdateInfo.addOnSuccessListener {
            if (it.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && it.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)){
                try {
                    appUpdateManager.startUpdateFlowForResult(it, AppUpdateType.FLEXIBLE, this, RC_APP_UPDATE)
                }catch (e : IntentSender.SendIntentException){
                    e.printStackTrace()
                }
            }else if (it.installStatus() == InstallStatus.DOWNLOADED){
                popupSnackbarForCompleteUpdate()
            }else{
                Log.d("BaseActivity", "checkForAppUpdateAvailability: something else")
            }
        }

        // listen to update
        installStateUpdatedListener = InstallStateUpdatedListener { state ->
            when {
                state.installStatus() == InstallStatus.DOWNLOADED -> popupSnackbarForCompleteUpdate()
                state.installStatus() == InstallStatus.INSTALLED -> appUpdateManager.unregisterListener(installStateUpdatedListener)
                else -> Log.d("BaseActivity", "InstallStateUpdatedListener: state: " + state.installStatus())
            }
        }


    }


    @SuppressLint("ResourceAsColor")
    private fun popupSnackbarForCompleteUpdate(){
        val snackbar = Snackbar.make(ConstraintBase, R.string.app_is_update, Snackbar.LENGTH_INDEFINITE)

        snackbar.setAction("Restart") { appUpdateManager.completeUpdate() }
        snackbar.setActionTextColor(R.color.colorAccentDark)
        snackbar.show()

    }

    override fun onResume() {
        super.onResume()

        appUpdateManager.appUpdateInfo.addOnSuccessListener {
            if (it.installStatus() == InstallStatus.DOWNLOADED){
            popupSnackbarForCompleteUpdate()
            }else{
            Log.d("BaseActivity", "checkForAppUpdateAvailability: something else")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_APP_UPDATE){
            if (resultCode != Activity.RESULT_OK){
                Log.d("BaseActivity", "onActivityResult: app download failed")
            }
        }
    }


}
