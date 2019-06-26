package com.bunkalogic.bunkalist.Dialog


import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.appcompat.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.RxBus.RxBus
import com.bunkalogic.bunkalist.SharedPreferences.preferences
import com.bunkalogic.bunkalist.db.ItemListRating
import com.bunkalogic.bunkalist.db.NewListRating
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.dialog_add_list.view.*
import java.util.*





class AddListDialog : androidx.fragment.app.DialogFragment(){

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var currentUser: FirebaseUser

    private lateinit var viewDialog: View

    var typeInt: Int = 0
    var statusInt: Int = 0



    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
        val args = arguments
        val Id = args?.getInt("id")
        val isAnime = args?.getBoolean("anime")
        Log.d("AddListDialog", "$Id")
        Log.d("AddListDialog", "is Anime = $isAnime")



    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        viewDialog = layoutInflater.inflate(R.layout.dialog_add_list, null)
        Log.d("AddListDialog", "${preferences.itemID}")
        setUpCurrentUser()
        setUpToolbar()
        whatTypeIs()
        setUpSpinner()
        onClicks()

        return viewDialog
    }

    private fun setUpCurrentUser() {
        currentUser = mAuth.currentUser!!
    }

    private fun setUpToolbar(){
        val toolbar = viewDialog.findViewById<Toolbar>(R.id.toolbarDialogAddList)
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp)
        toolbar.setNavigationOnClickListener { dismiss() }
        val args = arguments
        val title = args?.getString("title").toString()
        val name = args?.getString("name").toString()
        if (title == "null"){
            toolbar.title = name
        }else{
            toolbar.title = title
        }


    }

    private fun setUpSpinner(){
        // Creating the spinnerStatus adapter
        val adpStatus: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(context!!, R.array.status, android.R.layout.simple_spinner_item)
        adpStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        viewDialog.spinnerStatus.adapter = adpStatus

        viewDialog.spinnerStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d("AddListDialog", "No selected")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d("AddListDialog", "Selected: $position")
                statusInt = position
                Log.d("AddListDialog", "typeInt = $statusInt")
            }

        }

    }

    private fun whatTypeIs(){
        val args = arguments
        val type = args?.getString("type")
        val isAnime = args?.getBoolean("anime")

        if (type == "movie"){
            typeInt = Constans.MOVIE_LIST
            viewDialog.editTextDialogAddListSeason.visibility = View.INVISIBLE
            viewDialog.editTextDialogAddListEpisode.visibility = View.INVISIBLE
        }
        if (type == "tv"){
            typeInt = if (isAnime!!){
                Constans.ANIME_LIST
            }else{
                Constans.SERIE_LIST
            }
        }
    }

    private fun onClicks(){

        viewDialog.checkBoxDetails.setOnClickListener {
            if (viewDialog.checkBoxDetails.isChecked){
                // Hide the general rating
                viewDialog.textViewDialogRatingGeneral.visibility = View.GONE
                viewDialog.ratingBarGeneral.visibility = View.GONE
                // Show the rating in detail
                viewDialog.textViewDialogRatingArguments.visibility = View.VISIBLE
                viewDialog.textView8.visibility = View.VISIBLE
                viewDialog.textView9.visibility = View.VISIBLE
                viewDialog.textView10.visibility = View.VISIBLE
                viewDialog.textView11.visibility = View.VISIBLE
                viewDialog.ratingBarHistory.visibility = View.VISIBLE
                viewDialog.ratingBarCharacter.visibility = View.VISIBLE
                viewDialog.ratingBarEffects.visibility = View.VISIBLE
                viewDialog.ratingBarEnjoyment.visibility = View.VISIBLE
                viewDialog.ratingBarSoundtrack.visibility = View.VISIBLE
            }else {
                // Show the general rating
                viewDialog.textViewDialogRatingGeneral.visibility = View.VISIBLE
                viewDialog.ratingBarGeneral.visibility = View.VISIBLE
                // Hide the rating in detail
                viewDialog.textViewDialogRatingArguments.visibility = View.INVISIBLE
                viewDialog.textView8.visibility = View.INVISIBLE
                viewDialog.textView9.visibility = View.INVISIBLE
                viewDialog.textView10.visibility = View.INVISIBLE
                viewDialog.textView11.visibility = View.INVISIBLE
                viewDialog.ratingBarHistory.visibility = View.INVISIBLE
                viewDialog.ratingBarCharacter.visibility = View.INVISIBLE
                viewDialog.ratingBarEffects.visibility = View.INVISIBLE
                viewDialog.ratingBarEnjoyment.visibility = View.INVISIBLE
                viewDialog.ratingBarSoundtrack.visibility = View.INVISIBLE
            }

        }

        viewDialog.buttonAddList.setOnClickListener {
            val args = arguments
            val Id = args?.getInt("id")

            val season = viewDialog.editTextDialogAddListSeason.text.toString()
            val episode = viewDialog.editTextDialogAddListEpisode.text.toString()

            val ratingGeneral = viewDialog.ratingBarGeneral.rating
            val ratingHistory = viewDialog.ratingBarHistory.rating
            val ratingCharacter = viewDialog.ratingBarCharacter.rating
            val ratingEffects = viewDialog.ratingBarEffects.rating
            val ratingSoundtrack = viewDialog.ratingBarSoundtrack.rating
            val ratingEnjoyment = viewDialog.ratingBarEnjoyment.rating

            
            if(ratingGeneral.toInt() == 0){
                Log.d("AddListDialog", "it is a note in detail")
                var resultFinalRate = ratingHistory + ratingCharacter + ratingEffects + ratingSoundtrack + ratingEnjoyment
                // we divide to get the average
                val result = resultFinalRate / 5
                Log.d("AddListDialog", "Result final rate = $result")


                val itemRating = ItemListRating(
                    currentUser.uid,
                    statusInt,
                    Id,
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
                dismiss()
            }else{
                val itemRatingGeneral = ItemListRating(
                    currentUser.uid,
                    statusInt,
                    Id,
                    Date(),
                    ratingGeneral,
                    ratingGeneral,
                    ratingGeneral,
                    ratingGeneral,
                    ratingGeneral,
                    ratingGeneral,
                    season,
                    episode,
                    typeInt
                )
                RxBus.publish(NewListRating(itemRatingGeneral))
                dismiss()
            }



        }
    }
}