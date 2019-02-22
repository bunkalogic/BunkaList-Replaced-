package com.bunkalogic.bunkalist.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bunkalogic.bunkalist.Activities.LoginActivities.LoginActivity
import com.bunkalogic.bunkalist.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast


/**
 *  Created by @author Naim Dridi on 22/02/19
 */

class NewUserActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)

        buttonSignOut.setOnClickListener {
            mAuth.signOut()
            startActivity(intentFor<LoginActivity>())
        }
    }





    // Todo saving username and image profile cant implements , profileImage: Uri
    private fun saveProfileNameAndImageProfile(userName: String){
        val user = FirebaseAuth.getInstance().currentUser

        val userProfile = UserProfileChangeRequest
            .Builder()
            .setDisplayName(userName)
            .build()

        user?.updateProfile(userProfile)?.addOnCompleteListener(this){task ->
            if (task.isSuccessful){
                toast(R.string.add_correct_username_and_image_profile)
            }else{
                toast(R.string.login_error)
            }

        }

    }

    private fun getProfileImage(){
        //startActivityForResult(intentFor<MediaStore.Images>(), requestImageProfile)
    }
}
