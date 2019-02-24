package com.bunkalogic.bunkalist.Activities.NewUser


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bunkalogic.bunkalist.Activities.MainActivity
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
        setUpCurrentUser()
        clickListeners()
    }

    // Initializing the currentUser
    private fun setUpCurrentUser(){
        currentUser = mAuth.currentUser!!
    }

    //private fun setUpUserDB(){
    //    userDBRef = store.collection("users")
    //}

    //private fun saveNewUsernameAndImageProfile(user: UserProfile){
    //    val newUserProfile = HashMap<String, Any>()
    //    newUserProfile["userId"] = user.userId
    //    newUserProfile["ProfileImgURL"] = user.profileImgURL
    //
    //    userDBRef.add(newUserProfile).addOnCompleteListener {
    //        this.toast(R.string.add_correct_username_and_image_profile)
    //    }.addOnFailureListener {
    //        this.toast(R.string.error_new_user)
    //    }
    //
    //
    //
    //}



    // TODO: Changing the username and profile picture of the currentUser, the image is not saved
   private fun saveProfileNameAndImageProfile(userName: String, image: String){
       val userProfile = UserProfileChangeRequest.Builder()
           .setDisplayName(userName)
           .setPhotoUri(Uri.parse(image))
           .build()

       currentUser.updateProfile(userProfile).addOnCompleteListener(this){task ->
           if (task.isSuccessful){
               toast(R.string.add_correct_username_and_image_profile)
           }

       }.addOnFailureListener {
           toast(R.string.login_error)
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



            if (username.isEmpty() && imageProfile.isEmpty()){
                toast(R.string.error_new_user)
            }else{
                saveProfileNameAndImageProfile(username, imageProfile)
                startActivity(intentFor<MainActivity>().clearTask().newTask())
            }
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
            .withListener(object: PermissionListener{
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
        if (resultCode == Activity.RESULT_OK){
            when(requestCode){
                requestImageProfile ->{
                     val selectImage = data!!.data
                    imageButtonProfile.setImageURI(selectImage)
                }
            }
        }



    }
}
