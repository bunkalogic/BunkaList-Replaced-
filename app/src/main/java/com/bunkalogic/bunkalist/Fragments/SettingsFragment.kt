package com.bunkalogic.bunkalist.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatDelegate
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.bunkalogic.bunkalist.Activities.Login.LoginActivity
import com.bunkalogic.bunkalist.Activities.MainActivity
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.SharedPreferences.UserSharedPreferences
import com.bunkalogic.bunkalist.SharedPreferences.preferences
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.view.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.newTask
import org.jetbrains.anko.noAnimation
import org.jetbrains.anko.support.v4.intentFor

/**
 *  Created by @author Naim Dridi on 25/02/19
 */

class SettingsFragment : Fragment() {

    private lateinit var _view: View



    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _view =  inflater.inflate(R.layout.fragment_settings, container, false)
        clicksListeners()




        return _view
    }



    private fun clicksListeners(){

        // Todo : changed for button what do go to ActivityModeDayOrNight
        //_view.switchMode.setOnCheckedChangeListener { buttonView, _ ->
        //    if(buttonView.isChecked){
        //        switchMode.textOn
        //        preferences.editUserMode(true)
        //        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        //        startActivity(intentFor<MainActivity>().noAnimation())
        //    }else{
        //        switchMode.textOff
        //        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        //        startActivity(intentFor<MainActivity>().noAnimation())
        //    }
        //    preferences.editUserMode(false)
        //}
        //_view.switchMode.setOnClickListener {
        //    switchMode.isChecked = preferences.switchState
        //    preferences.switchState = true
        //    if (preferences.switchState){
        //        switchMode.textOn
        //        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        //        startActivity(intentFor<MainActivity>().noAnimation())
        //    }else{
        //        switchMode.textOff
        //        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        //        startActivity(intentFor<MainActivity>().noAnimation())
        //    }
        //}







        _view.buttonSignOut.setOnClickListener {
            preferences.deleteAll()
            mAuth.signOut()
            startActivity(intentFor<LoginActivity>().newTask().clearTask())
        }
    }


}
