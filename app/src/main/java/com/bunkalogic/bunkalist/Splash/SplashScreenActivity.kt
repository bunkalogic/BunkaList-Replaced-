package com.bunkalogic.bunkalist.Splash

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bunkalogic.bunkalist.Activities.LoginActivities.LoginActivity
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.startActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        goToActivity()

    }

    private fun goToActivity(){
        startActivity(intentFor<LoginActivity>())
        finish()
    }
}
