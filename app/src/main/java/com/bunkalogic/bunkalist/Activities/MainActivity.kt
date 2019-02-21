package com.bunkalogic.bunkalist.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bunkalogic.bunkalist.Activities.LoginActivities.LoginActivity
import com.bunkalogic.bunkalist.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor

/**
 *  Created by @author Naim Dridi on 18/02/19
 */

class MainActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    // private lateinit var currentUser: FirebaseUser
    //val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setUpCurrentUser()
        //textViewName.text = user!!.displayName?.let { user.displayName } ?: run {getString(R.string.signUp_data_incorrect)}

        buttonSignOut.setOnClickListener {
            mAuth.signOut()
            startActivity(intentFor<LoginActivity>())
        }


    }

    //private fun setUpCurrentUser(){
    //    currentUser = mAuth.currentUser!!
    //}
}
