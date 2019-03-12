package com.bunkalogic.bunkalist.Activities.SettingsActivities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bunkalogic.bunkalist.Activities.BaseActivity
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.SharedPreferences.preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_edit_profile.*
import org.jetbrains.anko.*

class EditProfileActivity : AppCompatActivity() {

    private lateinit var toolbar: android.support.v7.widget.Toolbar

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var currentUser: FirebaseUser

    private val requestImageProfile = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setUpCurrentUser()
        clicksListeners()

    }


    // Initializing the currentUser
    private fun setUpCurrentUser(){
        currentUser = mAuth.currentUser!!
    }

    private fun clicksListeners(){

        imageViewProfile.setOnClickListener {
            checkPermissionStorage()
            val image = imageViewProfile.image
            Glide.with(this).load(image)
                .apply(RequestOptions.circleCropTransform().override(150, 150))
                .into(this.imageViewProfile)
        }

        buttonApplyChanges.setOnClickListener {
            val username = edit_Text_Username.text.toString()
            preferences.userName = username
            val imageUser: Uri = Uri.EMPTY
            val imageProfile = imageUser
            imageViewProfile.setImageURI(imageUser)


            if (username.isNotEmpty()){
                //TODO: To be able to take the route of the image and be able to reload it
                preferences.imageProfilePath = currentUser.photoUrl.toString()
                preferences.editCurrentUser()
                saveProfileNameAndImageProfile(username, imageProfile)
                toast(R.string.add_correct_username_and_image_profile)
            }

        }
    }

    private fun saveProfileNameAndImageProfile(userName: String, image: Uri){
        val userProfile = UserProfileChangeRequest.Builder()
            .setDisplayName(userName)
            .setPhotoUri(image)
            .build()

        currentUser.updateProfile(userProfile).addOnCompleteListener(this){task ->
            if (task.isSuccessful){
                toast(R.string.add_correct_username_and_image_profile)
            }

        }.addOnFailureListener {
            toast(R.string.login_error)
        }

    }


    // Try the default App of the gallery
    private fun getGallery(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_PICK
        startActivityForResult(intent, requestImageProfile)
    }

    // asking for permissions
    private fun checkPermissionStorage() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object: PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    getGallery()
                }

                override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                    token?.continuePermissionRequest()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    if (response.isPermanentlyDenied){
                        longToast(R.string.error_new_user)
                    }else{
                        toast(R.string.accept_the_permissions)
                    }
                }

            }).check()
    }

    // picking up the extra getGallery
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                requestImageProfile -> {
                    val selectImage = data!!.data
                    imageViewProfile.setImageURI(selectImage)
                    val path=  selectImage!!.toString()
                    preferences.imageProfilePath = path
                }
            }
        }
    }

}
