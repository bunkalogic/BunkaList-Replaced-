package com.bunkalogic.bunkalist.Activities.LoginActivities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bunkalogic.bunkalist.R
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.intentFor

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        buttonCreateAccount.setOnClickListener { startActivity(intentFor<SignUpActivity>()) }
    }
}
