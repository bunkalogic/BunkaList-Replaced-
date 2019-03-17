package com.bunkalogic.bunkalist.Dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.RxBus.RxBus
import com.bunkalogic.bunkalist.SharedPreferences.preferences
import com.bunkalogic.bunkalist.db.NewTimeLineEvent
import com.bunkalogic.bunkalist.db.TimelineMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.dialog_timeline.*
import kotlinx.android.synthetic.main.dialog_timeline.view.*
import kotlinx.android.synthetic.main.fragment_timeline_item.*
import kotlinx.android.synthetic.main.fragment_timeline_item.view.*
import org.jetbrains.anko.support.v4.toast
import java.util.*

class TimeLineDialog : DialogFragment(){

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var currentUser: FirebaseUser

    private fun setUpCurrentUser() {
        currentUser = mAuth.currentUser!!
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setUpCurrentUser()
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_timeline, null)


        return AlertDialog
            .Builder(context!!)
            .setTitle(R.string.dialog_timeline)
            .setView(view)
            .setPositiveButton(getString(R.string.send_opinion)) { _, _ ->

                val textNameOeuvre = view.multiAutoCompleteTextViewOeuvre.text.toString()
                val textSeason = view.editTextSelectSeason.text.toString()
                val textChapter = view.editTextChapter.text.toString()
                val textContent = view.editTextContent.text.toString()


                if(textContent.isNotEmpty()){

                    val tlmessage = TimelineMessage(currentUser.uid, currentUser.displayName!!, currentUser.photoUrl.toString(), Date(), textNameOeuvre, textSeason, textChapter, textContent, "0" ,"0" )

                    RxBus.publish(NewTimeLineEvent(tlmessage))
                }
            }.setNegativeButton(getString(R.string.dialog_cancel_timeline)){ _, _ ->
                toast("Pressed Cancel")
            }
            .create()
    }

}