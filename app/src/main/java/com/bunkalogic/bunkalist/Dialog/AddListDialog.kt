package com.bunkalogic.bunkalist.Dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.SharedPreferences.preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AddListDialog : DialogFragment(){

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var currentUser: FirebaseUser

    private fun setUpCurrentUser() {
        currentUser = mAuth.currentUser!!
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setUpCurrentUser()
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_add_list, null)
        Log.d("AddListDialog", "${preferences.itemID}")

        return AlertDialog.Builder(context!!)
            .setTitle(getString(R.string.dialog_add_list_title))
            .setView(view)
            .setPositiveButton(getString(R.string.dialog_add_list_add_positive_button)) {_, _ ->


            }.setNegativeButton(getString(R.string.dialog_add_list_add_negative_button)){_, _ ->
                Log.d("AddListDialog", "cancel")
            }
            .create()
    }

}