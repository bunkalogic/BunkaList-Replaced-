package com.bunkalogic.bunkalist.Activities.UserProfileActivities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bunkalogic.bunkalist.Adapters.ProfileListAdapter
import com.bunkalogic.bunkalist.Adapters.ProfileOtherListAdapter
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.RxBus.RxBus
import com.bunkalogic.bunkalist.SharedPreferences.preferences
import com.bunkalogic.bunkalist.db.DataUsers
import com.bunkalogic.bunkalist.db.ItemListRating
import com.bunkalogic.bunkalist.db.Users
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.firebase.firestore.*
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_other_user_profile.*
import kotlinx.android.synthetic.main.fragment_topandreview.view.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.support.v4.intentFor

class OtherUserProfile : AppCompatActivity() {

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var mAdView : AdView

    private var listProfileitem: ArrayList<ItemListRating> = ArrayList()
    private lateinit var adapter: ProfileOtherListAdapter

    private lateinit var followDBRef: CollectionReference
    private val store: FirebaseFirestore = FirebaseFirestore.getInstance()

    private lateinit var followersDBRef: CollectionReference

    private var followSubscription: ListenerRegistration? = null
    private lateinit var followBusListener: Disposable



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_user_profile)
        // we instantiated the toolbar
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        fixScrollPosition()
        addBannerAds()

        setUpFollowsDB()
        setUpFollowersDB()
        setUpOtherProfile()

        clicksListeners()
        setUpRecycler()

        subscribeToProfileListOther()
        //subscribeToNewUserFollows()
    }
    private fun fixScrollPosition(){
        // This function ensures that when the fragment is loaded, it is displayed from the first position
        scrollView2.isFocusableInTouchMode = true
        scrollView2.descendantFocusability = ViewGroup.FOCUS_BEFORE_DESCENDANTS
    }


    //initializing the banner in this activity
    private fun addBannerAds(){
        mAdView = findViewById(R.id.adViewBannerActivityOtherUser)
        // Ad request to show on the banner
        val adRequest = AdRequest.Builder().build()
        // Associate the request to the banner
        mAdView.loadAd(adRequest)
    }

    private fun setUpOtherProfile(){
        val username = intent.extras?.getString("username")
        val userPhoto = intent.extras?.getString("userPhoto")

         supportActionBar!!.title = username

        userNameProfile.text = username

        Glide.with(this)
            .load(userPhoto)
            .override(130, 130)
            .into(userImageProfile)
    }


    private fun setUpFollowsDB(){
        followDBRef= store.collection("Data/Users/${preferences.userId}/ ${preferences.userIdDatabase} /Follows")
    }

    // Creating the new instance in the database
    private fun saveUsers(user: Users){
        followDBRef.add(user)
            .addOnCompleteListener {
                Log.d("ListFollowFragment", " User Added! ")
            }
            .addOnFailureListener {
                Log.d("ListFollowFragment", " Error try Again ")
            }
    }

    private fun setUpFollowersDB(){
        val userId = intent.extras?.getString("userId")
        val username = intent.extras?.getString("username")


        followersDBRef= store.collection("Data/Users/$userId/ $username /Followers")
    }

    // Creating the new instance in the database
    private fun saveFollowersUsers(user: Users){
        followersDBRef.add(user)
            .addOnCompleteListener {
                Log.d("ListFollowFragment", " User Added! ")
            }
            .addOnFailureListener {
                Log.d("ListFollowFragment", " Error try Again ")
            }
    }





    private fun clicksListeners(){

        val userId = intent.extras?.getString("userId")
        val username = intent.extras?.getString("username")
        val userPhoto = intent.extras?.getString("userPhoto")

        if (preferences.userId == userId){
            buttonFollows.visibility = View.GONE
        }

        buttonFollows.setOnClickListener {
            if (buttonFollows.tag == "click"){
                Log.d("OtherUserProfile", "is click")
                buttonFollows.tag = "unclick"
                buttonFollows.backgroundResource = R.drawable.button_rounded_transparent_color

            }else{
                buttonFollows.tag = "click"
                buttonFollows.backgroundResource = R.drawable.button_rounded_transparent_color_selected
                val follow = Users(userId!!, username!!, userPhoto!!)
                saveUsers(follow)
                val followers = Users(preferences.userId!!, preferences.userName!!, preferences.imageProfilePath!!)
                saveFollowersUsers(followers)
            }

        }

        buttonListAll.setOnClickListener {
            preferences.OtherUserId = userId
            preferences.OtherUserIdBatabase = "@${preferences.OtherUserIdBatabase}"
            startActivity(intentFor<ProfileListActivity>("list" to 1))
        }
        buttonTopFavsAll.setOnClickListener {
            preferences.OtherUserId = userId
            preferences.OtherUserIdBatabase = "@${preferences.OtherUserIdBatabase}"
            startActivity(intentFor<ProfileListActivity>("top" to 2))
        }
    }

    private fun setUpRecycler(){
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)


        adapter = ProfileOtherListAdapter(this, listProfileitem)

        recyclerOtherProfile.setHasFixedSize(true)
        recyclerOtherProfile.layoutManager = layoutManager
        recyclerOtherProfile.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerOtherProfile.adapter = adapter
    }



    // just give me the list for other list
    private fun subscribeToProfileListOther() {
        val userId = intent.extras?.getString("userId")


        store.collection("Users/$userId/RatingList")
            .orderBy("addDate", Query.Direction.DESCENDING)
            .limit(10)
            .addSnapshotListener(object : java.util.EventListener, EventListener<QuerySnapshot> {
                override fun onEvent(snapshot: QuerySnapshot?, exception: FirebaseFirestoreException?) {
                    exception?.let {
                        Log.d("ListProfileFragment", "exception")
                        return
                    }

                    snapshot?.let {
                        listProfileitem.clear()
                        val itemRating = it.toObjects(ItemListRating::class.java)
                        listProfileitem.addAll(itemRating)
                        adapter.notifyDataSetChanged()
                    }
                }

            })
    }



    override fun onSupportNavigateUp(): Boolean {
        preferences.deleteOtherUser()
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        preferences.deleteOtherUser()
        super.onBackPressed()
    }

    override fun onDestroy() {
        preferences.deleteOtherUser()
        super.onDestroy()
    }



}
