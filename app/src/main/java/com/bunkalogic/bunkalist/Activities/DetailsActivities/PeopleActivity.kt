package com.bunkalogic.bunkalist.Activities.DetailsActivities

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bunkalogic.bunkalist.Adapters.CastDataPersonAdapter
import com.bunkalogic.bunkalist.Adapters.CrewDataPersonAdapter
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.Retrofit.Callback.OnGetPeopleDataCallback
import com.bunkalogic.bunkalist.Retrofit.Callback.OnGetPeopleDataCastCallback
import com.bunkalogic.bunkalist.Retrofit.Callback.OnGetPeopleDataCrewCallback
import com.bunkalogic.bunkalist.Retrofit.Response.People.CastResult
import com.bunkalogic.bunkalist.Retrofit.Response.People.CrewResult
import com.bunkalogic.bunkalist.Retrofit.Response.People.ResultPeople
import com.bunkalogic.bunkalist.ViewModel.ViewModelSearch
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_people.*
import org.jetbrains.anko.toast

class PeopleActivity : AppCompatActivity() {

    private lateinit var searchViewModel: ViewModelSearch


    private lateinit var toolbar: Toolbar
    lateinit var mAdView : AdView

    lateinit var adapterCast : CastDataPersonAdapter
    lateinit var adapterCrew : CrewDataPersonAdapter

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var currentUser: FirebaseUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people)
        setUpToolbar()
        setUpCurrentUser()
        addBannerAds()
        //instantiate the ViewModel
        searchViewModel = ViewModelProviders.of(this).get(ViewModelSearch::class.java)

        getPeopleData()
        getPeopleCastAndCrew()


    }

    //initializing the banner in this activity
    private fun addBannerAds(){
        mAdView = findViewById(R.id.adViewBannerActivityPeople)
        // Ad request to show on the banner
        val adRequest = AdRequest.Builder().build()
        // Associate the request to the banner
        mAdView.loadAd(adRequest)
    }

    // Initializing the currentUser
    private fun setUpCurrentUser(){
        currentUser = mAuth.currentUser!!
    }

    private fun setUpToolbar(){
        toolbar = findViewById(R.id.toolbarPeopleActivity)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val extrasName = intent.extras?.getString("name")
        supportActionBar!!.title = extrasName
    }

    private fun getContentPeopleDataId(callback : OnGetPeopleDataCallback){
        val extrasId = intent.extras?.getInt("idPerson")
        searchViewModel.getPeopleData(extrasId!!, callback)
    }

    private fun getPeopleData(){
        getContentPeopleDataId(object : OnGetPeopleDataCallback{
            override fun onSuccess(people: ResultPeople) {
                textViewPersonName.text = people.name.toString()
                textViewbirthdayDatePeople.text = people.birthday.toString()
                textViewDataPeoplePlaceBirth.text = people.placeOfBirth.toString()
                textViewPeopleBiography.text = people.biography.toString()

                val deathday = people.deathday.toString()
                Log.d("PeopleActivity", "$deathday")


                if (people.deathday == null){
                    textViewLabelDeathDay.visibility = View.GONE
                    textViewdeathdayDatePeople.visibility = View.GONE
                }else{
                    textViewdeathdayDatePeople.text = deathday
                    textViewLabelDeathDay.visibility = View.VISIBLE
                    textViewdeathdayDatePeople.visibility = View.VISIBLE
                }

                val imagePerson = people.profilePath.toString()

                Glide.with(this@PeopleActivity)
                    .load(Constans.API_MOVIE_SERIES_ANIME_BASE_URL_IMG_PROFILE + imagePerson)
                    .placeholder(R.drawable.ic_person_black_24dp)
                    .centerCrop()
                    .into(ImageViewPersonActivity)

            }

            override fun onError() {
                toast("Please check your internet connection")
            }

        })
    }

    private fun getContentPeopleDataIdCastAndCrew(callback : OnGetPeopleDataCastCallback){
        val extrasId = intent.extras?.getInt("idPerson")
        searchViewModel.getPeopleDataCast(extrasId!!, callback)
    }

    private fun getPeopleCastAndCrew(){
        getContentPeopleDataIdCastAndCrew(object : OnGetPeopleDataCastCallback{
            override fun onSuccess(peopleCast: List<CastResult>, peopleCrew: List<CrewResult>) {

                if (peopleCast.isNotEmpty()){
                    textViewLabelDataCast.visibility = View.VISIBLE
                    RecyclerDataCast.removeAllViews()

                    val layoutManagerCast = LinearLayoutManager(this@PeopleActivity, LinearLayoutManager.HORIZONTAL, false)
                    adapterCast = CastDataPersonAdapter(this@PeopleActivity, peopleCast)

                    RecyclerDataCast.layoutManager = layoutManagerCast
                    RecyclerDataCast.setHasFixedSize(true)
                    RecyclerDataCast.itemAnimator = DefaultItemAnimator()
                    RecyclerDataCast.adapter = adapterCast

                }else{
                    textViewLabelDataCast.visibility = View.GONE
                }

                if (peopleCrew.isNotEmpty()){
                    textViewLabelDataCrew.visibility = View.VISIBLE
                    RecyclerDataCrew.removeAllViews()

                    val layoutManagerCrew = LinearLayoutManager(this@PeopleActivity, LinearLayoutManager.HORIZONTAL, false)
                    adapterCrew = CrewDataPersonAdapter(this@PeopleActivity, peopleCrew)

                    RecyclerDataCrew.layoutManager = layoutManagerCrew
                    RecyclerDataCrew.setHasFixedSize(true)
                    RecyclerDataCrew.itemAnimator = DefaultItemAnimator()
                    RecyclerDataCrew.adapter = adapterCrew
                }else{
                    textViewLabelDataCrew.visibility = View.GONE
                }

            }

            override fun onError() {
                textViewLabelDataCast.visibility = View.GONE
                textViewLabelDataCrew.visibility = View.GONE
                toast("Please check your internet connection")
            }

        })
    }





    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
