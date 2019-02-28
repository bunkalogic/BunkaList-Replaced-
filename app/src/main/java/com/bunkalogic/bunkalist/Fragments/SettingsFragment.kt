package com.bunkalogic.bunkalist.Fragments


import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bunkalogic.bunkalist.Activities.Login.LoginActivity

import com.bunkalogic.bunkalist.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_settings.view.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.newTask
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

        // Todo : changed for switch element
        _view.imageButtonMode.setOnClickListener {
          AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        _view.buttonSignOut.setOnClickListener {
            mAuth.signOut()
            startActivity(intentFor<LoginActivity>().newTask().clearTask())
        }
    }

}
