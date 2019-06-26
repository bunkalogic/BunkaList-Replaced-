package com.bunkalogic.bunkalist.Dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.RxBus.RxBus
import com.bunkalogic.bunkalist.db.NewReview
import com.bunkalogic.bunkalist.db.NewReviewEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.dialog_new_review.view.*
import org.jetbrains.anko.support.v4.toast
import java.util.*

class ReviewDialog: androidx.fragment.app.DialogFragment(){

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var currentUser: FirebaseUser

    private fun setUpCurrentUser() {
        currentUser = mAuth.currentUser!!
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setUpCurrentUser()
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_new_review, null)

        return AlertDialog
            .Builder(context!!)
            .setTitle(R.string.dialog_review_title)
            .setView(view)
            .setPositiveButton(getString(R.string.dialog_review_positive_button)){ _, _ ->
                val title = view.editTextTittleReview.text.toString()
                val review = view.editTextContentReview.text.toString()
                val isSpoiler = view.checkBoxReviewContainsSpoiler.isChecked


                if (review.isEmpty() && review != "text" && review == "text1234"){
                    toast("The review is empty")
                }else{
                    val reviewExts = NewReview(currentUser.uid, currentUser.displayName!!, currentUser.photoUrl.toString(), Date(), title, review, isSpoiler)

                    RxBus.publish(NewReviewEvent(reviewExts))
                }




            }.setNegativeButton(getString(R.string.dialog_cancel_timeline)){_, _ ->
                dialog.dismiss()
            }
            .create()
    }

}