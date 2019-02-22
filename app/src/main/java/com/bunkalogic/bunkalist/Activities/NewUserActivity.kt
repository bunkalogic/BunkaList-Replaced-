package com.bunkalogic.bunkalist.Activities

import android.media.Image
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.bunkalogic.bunkalist.Activities.LoginActivities.LoginActivity
import com.bunkalogic.bunkalist.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_new_user.*
import org.jetbrains.anko.*


/**
 *  Created by @author Naim Dridi on 22/02/19
 */

class NewUserActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var currentUser: FirebaseUser

    private val requestImageProfile = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)
        clickListeners()
    }





    // Todo saving username and image profile cant implements , profileImage: Uri
    private fun saveProfileNameAndImageProfile(userName: String, image: String){
        val userProfile = UserProfileChangeRequest
            .Builder()
            .setDisplayName(userName)
            .setPhotoUri(Uri.parse(image))
            .build()

        currentUser.updateProfile(userProfile).addOnCompleteListener(this){task ->
            if (task.isSuccessful){
                toast(R.string.add_correct_username_and_image_profile)
            }else{
                toast(R.string.login_error)
            }

        }

    }

    private fun getProfileImage(image: String){

        startActivityForResult(intentFor<MediaStore.Images.Media>(), requestImageProfile)
        MediaStore.Images.Media.getContentUri(image)
    }

    private fun clickListeners(){

        imageButtonProfile.setOnClickListener {
            val imageProfile = imageButtonProfile.image.toString()
            //getProfileImage(imageProfile)
        }

        buttonGoTo.setOnClickListener {
            val username = editTextUserName.text.toString()
            val imageProfile = imageButtonProfile.image.toString()

            saveProfileNameAndImageProfile(username, imageProfile)

            startActivity(intentFor<MainActivity>().clearTask().newTask())
        }
    }
}
