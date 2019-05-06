package com.bunkalogic.bunkalist.Activities.OtherActivities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bunkalogic.bunkalist.Activities.Login.LoginActivity
import com.bunkalogic.bunkalist.Activities.BaseActivity
import com.bunkalogic.bunkalist.R
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class MainEmptyActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()


    // This activity is responsible for when the App is opened, for the transition to the LoginActivity or BaseActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this, "")
        if (mAuth.currentUser == null){
            startActivity(intentFor<LoginActivity>().newTask())
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }else{
            startActivity(intentFor<BaseActivity>().newTask())
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }

}
