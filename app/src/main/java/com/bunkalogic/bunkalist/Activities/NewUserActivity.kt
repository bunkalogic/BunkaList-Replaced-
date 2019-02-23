package com.bunkalogic.bunkalist.Activities


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bunkalogic.bunkalist.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
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
        setUpCurrentUser()
    }





    // Changing the username and profile picture of the currentUser
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





    // Handling the clickListeners
    private fun clickListeners(){

        imageButtonProfile.setOnClickListener {
            checkPermissionStorage()

        }

        buttonGoTo.setOnClickListener {
            val username = editTextUserName.text.toString()
            val imageProfile = imageButtonProfile.image.toString()
            val image = Uri.parse(imageProfile)

            saveProfileNameAndImageProfile(username, image)

            if (username.isNotEmpty() && imageProfile.isNotEmpty()){
                startActivity(intentFor<MainActivity>().clearTask().newTask())
            }else{
                toast(R.string.error_new_user)
            }
        }
    }

    // Try the default App of the gallery
    private fun getGallery(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, requestImageProfile)
        }else{
            toast(R.string.login_error)
        }

    }

    // asking for permissions
    private fun checkPermissionStorage() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object: PermissionListener{
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    getGallery()
                }

                override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                    token?.continuePermissionRequest()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    toast(R.string.login_error)
                }

            } )
    }

    // picking up the extra getGallery
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            requestImageProfile -> {

                if (resultCode == Activity.RESULT_OK){

                    val extras = data!!.extras

                    Glide.with(this)
                        .load(extras)
                        .apply(RequestOptions.circleCropTransform()
                            .override(100 ,100))
                        .into(this.imageButtonProfile)
                }else{
                    toast(R.string.login_error)
                }
            }
        }

    }

    // Initializing the currentUser
    private fun setUpCurrentUser(){
        currentUser = mAuth.currentUser!!
    }
}
