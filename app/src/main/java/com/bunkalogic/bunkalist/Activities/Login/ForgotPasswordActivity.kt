package com.bunkalogic.bunkalist.Activities.Login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bunkalogic.bunkalist.Others.isValidEmail
import com.bunkalogic.bunkalist.Others.validate
import com.bunkalogic.bunkalist.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*
import org.jetbrains.anko.*

/**
 *  Created by @author Naim Dridi on 19/02/19
 */

class ForgotPasswordActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        validateEmail()
        clickListener()
    }

    // checked is the email is valid
    private fun validateEmail(){
        editTextEmailReset.validate {
            editTextEmailReset.error = if (isValidEmail(it)) null else "Email is not valid"
        }
    }

    private fun clickListener(){

        // passing the email for reset password
        buttonForgot.setOnClickListener {
            val email = editTextEmailReset.text.toString()
            if (isValidEmail(email)){
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(this){
                    toast(R.string.email_reset_to_password)
                  startActivity(intentFor<LoginActivity>().newTask().clearTask())
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
            }else{
                toast(R.string.email_reset_to_password_failed)
            }
        }

        buttonGoLogIn.setOnClickListener {
            startActivity(intentFor<LoginActivity>().newTask().clearTask())
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }


}
