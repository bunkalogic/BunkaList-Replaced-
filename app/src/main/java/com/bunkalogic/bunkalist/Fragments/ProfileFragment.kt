package com.bunkalogic.bunkalist.Fragments


import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.SharedPreferences.preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_profile.view.*

/**
 *  Created by @author Naim Dridi on 25/02/19
 */


class ProfileFragment : Fragment() {

    private lateinit var _view: View

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var currentUser: FirebaseUser

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _view = inflater.inflate(R.layout.fragment_profile, container, false)
        setUpCurrentUser()
        setUpCurrentUserUI()

        return _view
    }

    // Initializing the currentUser
    private fun setUpCurrentUser(){
        currentUser = mAuth.currentUser!!
    }

    private fun setUpCurrentUserUI(){

        _view.userNameProfile.text = currentUser.displayName

            Glide.with(this)
                .load(preferences.imageProfilePath)
                .apply(RequestOptions.circleCropTransform()
                    .override(150, 150))
                .into(_view.userImageProfile)







    }


}
