package com.bunkalogic.bunkalist.Dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.RxBus.RxBus
import com.bunkalogic.bunkalist.SharedPreferences.preferences
import com.bunkalogic.bunkalist.db.ItemListRating
import com.bunkalogic.bunkalist.db.NewListRating
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.dialog_add_list.view.*
import org.jetbrains.anko.support.v4.toast
import java.util.*

class AddListDialog : DialogFragment(){

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var currentUser: FirebaseUser

    var typeInt: Int = 0
    var statusInt: Int = 0

    private fun setUpCurrentUser() {
        currentUser = mAuth.currentUser!!
    }



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setUpCurrentUser()
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_add_list, null)
        Log.d("AddListDialog", "${preferences.itemID}")



        // Creating the spinnerStatus adapter
        val adpStatus: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(context!!, R.array.status, android.R.layout.simple_spinner_item)
        adpStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view.spinnerStatus.adapter = adpStatus

        view.spinnerStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("AddListDialog", "No selected")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d("AddListDialog", "Selcted: $position")
                statusInt = position
                Log.d("AddListDialog", "typeInt = $statusInt")
            }

        }

        // Creating the spinnerStatus adapter
        val adpType: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(context!!, R.array.type, android.R.layout.simple_spinner_item)
        adpType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view.spinnerType.adapter = adpType

        view.spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("AddListDialog", "No selected")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d("AddListDialog", "Selcted: $position")
                typeInt = position
                Log.d("AddListDialog", "typeInt = $typeInt")
            }

        }



        return AlertDialog
            .Builder(context!!)
            .setTitle(getString(R.string.dialog_add_list_title))
            .setView(view)
            .setPositiveButton(getString(R.string.dialog_add_list_add_positive_button)) {_, _ ->


                val season = view.editTextDialogAddListSeason.text.toString()
                val episode = view.editTextDialogAddListEpisode.text.toString()
                val ratingHistory = view.ratingBarHistory.rating
                val ratingCharacter = view.ratingBarCharacter.rating
                val ratingEffects = view.ratingBarEffects.rating
                val ratingSoundtrack = view.ratingBarSoundtrack.rating
                val ratingEnjoyment = view.ratingBarEnjoyment.rating

                // we add all the results
                var resultFinalRate = ratingHistory + ratingCharacter + ratingEffects + ratingSoundtrack + ratingEnjoyment
                // we divide to get the average
                val result = resultFinalRate / 5
                Log.d("AddListDialog", "Result final rate = $result")

                val itemRating = ItemListRating(
                    currentUser.uid,
                    statusInt,
                    preferences.itemID,
                    Date(),
                    ratingHistory,
                    ratingCharacter,
                    ratingEffects,
                    ratingSoundtrack,
                    ratingEnjoyment,
                    result,
                    season,
                    episode,
                    typeInt
                )
                RxBus.publish(NewListRating(itemRating))

            }
            .setNegativeButton(getString(R.string.dialog_add_list_add_negative_button)){_, _ ->
                Log.d("AddListDialog", "cancel")
            }
            .create()
    }
}