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
import com.bunkalogic.bunkalist.Models.UserProfile
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.RxBus.RxBus
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_new_user.*
import org.jetbrains.anko.*
import java.io.InputStream


/**
 *  Created by @author Naim Dridi on 22/02/19
 */

class NewUserActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var currentUser: FirebaseUser

    private val store: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var userDBRef: CollectionReference

    private val requestImageProfile = 100

    private lateinit var userBusListener: Disposable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)
        setUpUserDB()
        setUpCurrentUser()
        clickListeners()
    }

    // Initializing the currentUser
    private fun setUpCurrentUser(){
        currentUser = mAuth.currentUser!!
    }

    private fun setUpUserDB(){
        userDBRef = store.collection("users")
    }

    private fun saveNewUsernameAndImageProfile(user: UserProfile){
        val newUserProfile = HashMap<String, Any>()
        newUserProfile["userId"] = user.userId
        newUserProfile["username"] = user.username
        newUserProfile["ProfileImgURL"] = user.profileImgURL

        userDBRef.add(newUserProfile).addOnCompleteListener {
            this.toast(R.string.add_correct_username_and_image_profile)
        }.addOnFailureListener {
            this.toast(R.string.error_new_user)
        }



    }



    // Changing the username and profile picture of the currentUser
   // private fun saveProfileNameAndImageProfile(userName: String, image: Uri){
   //     val userProfile = UserProfileChangeRequest
   //         .Builder()
   //         .setDisplayName(userName)
   //         .setPhotoUri(image)
   //         .build()
   //
   //     currentUser.updateProfile(userProfile).addOnCompleteListener(this){task ->
   //         if (task.isSuccessful){
   //             toast(R.string.add_correct_username_and_image_profile)
   //         }else{
   //             toast(R.string.login_error)
   //         }
   //
   //     }
   //
   // }





    // Handling the clickListeners
    private fun clickListeners(){

        imageButtonProfile.setOnClickListener {
            checkPermissionStorage()


        }

        buttonGoTo.setOnClickListener {
            val username = editTextUserName.text.toString()
            val imageProfile = imageButtonProfile.image.toString()
            //val image = Uri.parse(imageProfile)
            val newUser = UserProfile(currentUser.uid, username, imageProfile)


            if (username.isEmpty() && imageProfile.isEmpty()){
                toast(R.string.error_new_user)
            }else{
                //saveProfileNameAndImageProfile(username, image)
                saveNewUsernameAndImageProfile(newUser)
                saveTheNewUsers()
                startActivity(intentFor<MainActivity>().clearTask().newTask())
            }
        }
    }

    // Try the default App of the gallery
    private fun getGallery(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, requestImageProfile)
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

                if (requestCode == requestImageProfile && resultCode == Activity.RESULT_OK){

                    if ( data == null){
                        toast(R.string.error_new_user)

                    }else{
                        val extras = data.extras

                        Glide.with(this)
                            .load(extras)
                            .apply(RequestOptions.circleCropTransform()
                                .override(100 , 100))
                            .into(this.imageButtonProfile)
                    }
                }



    }

    private fun saveTheNewUsers(){
        userBusListener = RxBus.listen(UserProfile::class.java).subscribe {
            saveNewUsernameAndImageProfile(it)
        }
    }

    override fun onDestroy() {
        userBusListener.dispose()
        super.onDestroy()
    }



}
