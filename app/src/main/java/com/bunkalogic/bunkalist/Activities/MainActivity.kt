package com.bunkalogic.bunkalist.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bunkalogic.bunkalist.Activities.LoginActivities.LoginActivity
import com.bunkalogic.bunkalist.Models.UserProfile
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.RxBus.RxBus
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*

/**
 *  Created by @author Naim Dridi on 18/02/19
 */

class MainActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var currentUser: FirebaseUser
    //private val user = FirebaseAuth.getInstance().currentUser

    private val store: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var userDBRef: CollectionReference

    private lateinit var userBusListener: Disposable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpUserDB()
        setUpCurrentUser()
        getUsernameAndImage()
        getUsernameAndImageProfileFromGmail()




        buttonSignOut.setOnClickListener {
            mAuth.signOut()
            startActivity(intentFor<LoginActivity>().clearTask().newTask())
        }


    }

    private fun setUpCurrentUser(){
        currentUser = mAuth.currentUser!!
    }

    private fun setUpUserDB(){
        userDBRef = store.collection("users")
    }

    private fun getUsernameAndImage(){
        userBusListener = RxBus.listen(UserProfile::class.java).subscribe {
            textViewName.text = "${it.username}"
            val imageProfile = "${it.profileImgURL}"
                let { Glide.with(this).load(imageProfile)
                .apply(RequestOptions.circleCropTransform()
                    .override(150, 150))
                .into(this.imageButtonProfile) }
        }
    }

    private fun getUsernameAndImageProfileFromGmail(){

        textViewName.text = currentUser.displayName
            ?.let { currentUser.displayName }
            ?: run {getString(R.string.signUp_data_incorrect)}

        currentUser.photoUrl?.let {
            Glide.with(this).load(currentUser.photoUrl)
                .apply(RequestOptions.circleCropTransform()
                    .override(150, 150))
                .into(this.imageButtonProfile)
        }   ?: run { toast(R.string.error_new_user) }

    }
}
