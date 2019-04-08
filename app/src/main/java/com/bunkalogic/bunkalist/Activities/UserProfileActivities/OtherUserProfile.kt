package com.bunkalogic.bunkalist.Activities.UserProfileActivities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bunkalogic.bunkalist.Fragments.ProfileFragment
import com.bunkalogic.bunkalist.R
import kotlinx.android.synthetic.main.activity_other_user_profile.*

class OtherUserProfile : AppCompatActivity() {

    private lateinit var toolbar: android.support.v7.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_user_profile)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setUPOhterProfile()
    }

    private fun setUPOhterProfile(){
        val userId = intent.extras?.getString("userId")
        val username = intent.extras?.getString("username")
        val userPhoto = intent.extras?.getString("userPhoto")
         supportActionBar!!.title = username

        userNameProfile.text = username

        Glide.with(this)
            .load(userPhoto)
            .override(130, 130)
            .into(userImageProfile)

        //ProfileFragment.newInstance(userId!!, username!!, userPhoto!!)
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
