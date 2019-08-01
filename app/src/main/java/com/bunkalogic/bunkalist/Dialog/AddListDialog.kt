package com.bunkalogic.bunkalist.Dialog


import android.content.res.AssetManager
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.appcompat.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import com.airbnb.lottie.LottieAnimationView
import com.bunkalogic.bunkalist.Adapters.StatusColorsAdapter
import com.bunkalogic.bunkalist.Models.FilterSearchData
import com.bunkalogic.bunkalist.Models.StatusColorItem
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.Retrofit.Callback.OnGetGuestSessionCallback
import com.bunkalogic.bunkalist.RxBus.RxBus
import com.bunkalogic.bunkalist.SharedPreferences.preferences
import com.bunkalogic.bunkalist.ViewModel.ViewModelAPItmdb
import com.bunkalogic.bunkalist.db.ItemListRating
import com.bunkalogic.bunkalist.db.NewListRating
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.dialog_add_list.view.*
import org.jetbrains.anko.support.v4.toast
import java.util.*





class AddListDialog : androidx.fragment.app.DialogFragment(){

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var currentUser: FirebaseUser

    private lateinit var viewDialog: View

    private lateinit var adapter : StatusColorsAdapter

    var typeInt: Int = 0


    private lateinit var searchViewModel: ViewModelAPItmdb
    val filterListSort: ArrayList<StatusColorItem> by lazy { getStatus() }



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
        searchViewModel = ViewModelProviders.of(this).get(ViewModelAPItmdb::class.java)

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
        setAdapter()
        //setUpSpinner()
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

    private fun setAdapter(){
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            context,
            androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
            false
        )
        adapter = StatusColorsAdapter(context!!, getStatus())

        viewDialog.RecyclerStatusColor.layoutManager = layoutManager
        viewDialog.RecyclerStatusColor.setHasFixedSize(true)
        viewDialog.RecyclerStatusColor.itemAnimator = DefaultItemAnimator()
        viewDialog.RecyclerStatusColor.adapter = adapter

    }

    //private fun setUpSpinner(){
    //    // Creating the spinnerStatus adapter
    //    val adpStatus: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(context!!, R.array.status, android.R.layout.simple_spinner_item)
    //    adpStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    //    viewDialog.spinnerStatus.adapter = adpStatus
//
    //    viewDialog.spinnerStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
    //        override fun onNothingSelected(parent: AdapterView<*>?) {
    //            Log.d("AddListDialog", "No selected")
    //        }
//
    //        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    //            Log.d("AddListDialog", "Selected: $position")
    //            statusInt = position
    //            Log.d("AddListDialog", "typeInt = $statusInt")
    //        }
//
    //    }
//
    //}

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

            if (adapter.getStatusId() == -1){
                toast(R.string.dialog_add_list_select_one_status)
            }else{
                if(ratingGeneral.toInt() == 0){
                    Log.d("AddListDialog", "it is a note in detail")
                    var resultFinalRate = ratingHistory + ratingCharacter + ratingEffects + ratingSoundtrack + ratingEnjoyment
                    // we divide to get the average
                    val result = resultFinalRate / 5
                    Log.d("AddListDialog", "Result final rate = $result")


                    val itemRating = ItemListRating(
                        currentUser.uid,
                        adapter.getStatusId(),
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

                    // post the rate in TMDB
                    if (preferences.userGuestSesionId!!.isEmpty()){
                        createGuestSession()
                    }
                    Log.d("AddListDialog", "Guest Session: ${preferences.userGuestSesionId}")
                    postRateMovieOrSeries(result.toDouble())
                    dismiss()
                }else{
                    val itemRatingGeneral = ItemListRating(
                        currentUser.uid,
                        adapter.getStatusId(),
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
                    // post the rate in TMDB
                    if (preferences.userGuestSesionId!!.isEmpty()){
                        createGuestSession()
                    }
                    Log.d("AddListDialog", "Guest Session: ${preferences.userGuestSesionId}")
                    postRateMovieOrSeries(ratingGeneral.toDouble())

                    dismiss()
                }
            }

        }
    }


    private fun createGuestSession(){
            searchViewModel.getGuestSession(object : OnGetGuestSessionCallback{
                override fun onSuccess(guestId: String) {
                    preferences.userGuestSesionId = guestId
                }

                override fun onError() {
                    Log.d("AddListDialog", "Guest Session: ${preferences.userGuestSesionId} ")
                }

            })
    }

    private fun postRateMovieOrSeries(rate: Double){
        val args = arguments
        val type = args?.getString("type")
        val Id = args?.getInt("id")

        if (type == "movie"){
            searchViewModel.postMovie(Id!!, rate)
        }
        if (type == "tv"){
            searchViewModel.postSeriesAndAnime(Id!!, rate)
        }
    }


    private fun getStatus(): ArrayList<StatusColorItem> {
        return object: ArrayList<StatusColorItem>(){
            init {
                add(StatusColorItem(Constans.filter_status_complete, R.color.colorStatusComplete, getString(R.string.fab_filter_profile_status_complete)))
                add(StatusColorItem(Constans.filter_status_watching, R.color.colorStatusWatching, getString(R.string.fab_filter_profile_status_watching)))
                add(StatusColorItem(Constans.filter_status_waiting, R.color.colorStatusPending, getString(R.string.fab_filter_profile_status_waiting)))
                add(StatusColorItem(Constans.filter_status_pause, R.color.colorStatusPause, getString(R.string.fab_filter_profile_status_pause)))
                add(StatusColorItem(Constans.filter_status_dropped, R.color.colorStatusDropped, getString(R.string.fab_filter_profile_status_dropped)))
            }
        }
    }


}