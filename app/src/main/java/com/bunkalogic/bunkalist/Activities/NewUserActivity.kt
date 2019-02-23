package com.bunkalogic.bunkalist.Activities


import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bunkalogic.bunkalist.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
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
    private fun saveProfileNameAndImageProfile(userName: String, image: Uri){
        val userProfile = UserProfileChangeRequest
            .Builder()
            .setDisplayName(userName)
            .setPhotoUri(image)
            .build()

        currentUser.updateProfile(userProfile).addOnCompleteListener(this){task ->
            if (task.isSuccessful){
                toast(R.string.add_correct_username_and_image_profile)
            }else{
                toast(R.string.login_error)
            }

        }

    }

    private fun getProfileImage(){
        val galleryIntent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, requestImageProfile)
    }
    

    private fun clickListeners(){

        imageButtonProfile.setOnClickListener {
            getProfileImage()
        }

        buttonGoTo.setOnClickListener {
            val username = editTextUserName.text.toString()
            val imageProfile = imageButtonProfile.image.toString()

            saveProfileNameAndImageProfile(username, Uri.parse(imageProfile))

            startActivity(intentFor<MainActivity>().clearTask().newTask())
        }
    }
}
