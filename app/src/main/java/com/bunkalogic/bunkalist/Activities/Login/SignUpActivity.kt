package com.bunkalogic.bunkalist.Activities.Login


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.SpannableString
import android.text.style.ClickableSpan
import com.bunkalogic.bunkalist.Others.isValidConfirmPassword
import com.bunkalogic.bunkalist.Others.isValidEmail
import com.bunkalogic.bunkalist.Others.isValidPassword
import com.bunkalogic.bunkalist.Others.validate
import com.bunkalogic.bunkalist.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.jetbrains.anko.*
import android.view.View
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod


/**
 *  Created by @author Naim Dridi on 21/02/19
 */

class SignUpActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        acceptPolicyPrivacy()
        clickListeners()

    }

    // creating an account with email and sending the confirmation by email
    private fun signUpByEmail(email: String, password: String){
       mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){ task ->
           if (task.isSuccessful){
               mAuth.currentUser!!.sendEmailVerification().addOnCompleteListener(this){
                   toast(R.string.confirm_email_send)

                   startActivity(intentFor<LoginActivity>().newTask().clearTask())
                   overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
               }
           }else{
               toast(R.string.login_error)
           }

       }

    }



    // Collecting all the click events
    private fun clickListeners() {



        buttonSingUP.setOnClickListener {
            val email = editTextEmailSignUp.text.toString()
            val password = editTextPasswordSignUp.text.toString()
            val corfirmPassword = editTextConfirmPassword.text.toString()

            if (checkBoxSignUpTermsAndUse.isChecked){

                if ( isValidEmail(email) && isValidPassword(password) && isValidConfirmPassword(password, corfirmPassword)) {
                    signUpByEmail(email, password)
                    startActivity(intentFor<LoginActivity>().clearTask().newTask())
                }else{
                    toast(R.string.signUp_data_incorrect)
                }
            }else{
                toast(R.string.sign_up_accept_terms_and_use)
            }




            editTextEmailSignUp.validate {

                editTextEmailSignUp.error = if (isValidEmail(it)) null else " Email is not valid "
            }

            editTextPasswordSignUp.validate {

                editTextPasswordSignUp.error = if (isValidPassword(it)) null else " Password should contain 1 lowercase, 1 uppercase, 1 number, 8 characters length at least"
            }

            editTextConfirmPassword.validate {

                editTextConfirmPassword.error = if (isValidConfirmPassword(editTextConfirmPassword.text.toString(), it)) null else " Confirm Password does not match with Password"
            }
        }

        buttonGoLogIn.setOnClickListener {
            startActivity(intentFor<LoginActivity>().clearTask().newTask())
        }
    }

    private fun acceptPolicyPrivacy(){
        val string = getString(R.string.sign_up_terms_anduse)

        val ss = SpannableString(string)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.iubenda.com/privacy-policy/33068007")))
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = ContextCompat.getColor(this@SignUpActivity, R.color.fui_linkColor)
            }
        }

        ss.setSpan(clickableSpan, 13, 28, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        checkBoxSignUpTermsAndUse.text = ss
        checkBoxSignUpTermsAndUse.movementMethod = LinkMovementMethod.getInstance()

    }
}
