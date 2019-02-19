package com.bunkalogic.bunkalist.Others

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.EditText
import java.util.regex.Pattern

fun EditText.validate(validation: (String) -> Unit){

    this.addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            validation(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

    })
}

fun Activity.isValidPassword(password: String): Boolean {
    // Nesesita Contener --> 1 Num / 1 Minuscula / 1 Mayuscula / Min. Caracteres 4

    val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+\$).{8,}\$"
    val pattern = Pattern.compile(passwordPattern)
    return pattern.matcher(password).matches()
}

fun Activity.isValidConfirmPassword(password: String, confirmPassword: String): Boolean{
    return password == confirmPassword
}

fun Activity.isValidEmail(email: String): Boolean{
    val pattern = Patterns.EMAIL_ADDRESS
    return pattern.matcher(email).matches()
}