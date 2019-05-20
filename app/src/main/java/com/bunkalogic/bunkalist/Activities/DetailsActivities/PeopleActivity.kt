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
import com.bunkalogic.bunkalist.Retrofit.Callback.OnGetPeopleSocialMediaCallback
import com.bunkalogic.bunkalist.Retrofit.Response.People.CastResult
import com.bunkalogic.bunkalist.Retrofit.Response.People.CrewResult
import com.bunkalogic.bunkalist.Retrofit.Response.People.PeopleSocialMediaResponse
import com.bunkalogic.bunkalist.Retrofit.Response.People.ResultPeople
import com.bunkalogic.bunkalist.ViewModel.ViewModelSearch
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_people.*
import org.jetbrains.anko.toast
import android.content.Intent
import android.net.Uri






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
        getPeopleSocialMedia()


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

    // It is responsible for collecting the biographical data of said person
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

    // It is responsible for collecting lists of movies and series that said person has worked on it
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

    private fun getContentPeopleDataIdSocialMedia(callback : OnGetPeopleSocialMediaCallback){
        val extrasId = intent.extras?.getInt("idPerson")
        searchViewModel.getPeopleSocialMedia(extrasId!!, callback)
    }

    // Responsible for showing the icons of social networks if that person has an account
    private fun getPeopleSocialMedia(){
        getContentPeopleDataIdSocialMedia(object : OnGetPeopleSocialMediaCallback{
            override fun onSuccess(peopleSocial: PeopleSocialMediaResponse) {
                val facebookId = peopleSocial.facebookId.toString()
                val twitterId = peopleSocial.twitterId.toString()
                val instagramId = peopleSocial.instagramId.toString()

                //Check if the facebookId is null if it is not try to open the App or if the browser does not have it installed
                if (facebookId == "null"){
                    imageViewPeopleFacebook.visibility = View.INVISIBLE
                }else{
                    imageViewPeopleFacebook.visibility = View.VISIBLE

                    val facebookApp = "fb://page/$facebookId"
                    val facebookUrl = "http://www.facebook.com/$facebookId"

                    imageViewPeopleFacebook.setOnClickListener {
                        try {
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(facebookApp)))
                        } catch (e: Exception) {
                            Log.e("PeopleActivity", "Application not installed.")
                            //Abre url de pagina.
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)))
                        }
                    }
                }

                //Check if the instagramId is null if it is not try to open the App or if the browser does not have it installed
                if (instagramId == "null"){
                    imageViewPeopleInstagram.visibility = View.INVISIBLE
                }else{
                    imageViewPeopleInstagram.visibility = View.VISIBLE


                    imageViewPeopleInstagram.setOnClickListener {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse("http://instagram.com/_u/$instagramId")
                            intent.setPackage("com.instagram.android")
                            startActivity(intent)
                        } catch (anfe: android.content.ActivityNotFoundException) {
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://www.instagram.com/$instagramId")
                                )
                            )
                        }

                    }
                }
                //Check if the twitterId is null if it is not try to open the App or if the browser does not have it installed
                if (twitterId == "null"){
                    imageViewPeopleTwitter.visibility = View.INVISIBLE
                }else{
                    imageViewPeopleTwitter.visibility = View.VISIBLE

                    imageViewPeopleTwitter.setOnClickListener {
                        try {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("twitter://user?screen_name=$twitterId")
                            )
                            startActivity(intent)
                        } catch (e: Exception) {
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://twitter.com/#!/$twitterId")
                                )
                            )
                        }

                    }
                }



            }

            override fun onError() {
                imageViewPeopleFacebook.visibility = View.GONE
                imageViewPeopleTwitter.visibility = View.GONE
                imageViewPeopleInstagram.visibility = View.GONE
            }

        })
    }





    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
