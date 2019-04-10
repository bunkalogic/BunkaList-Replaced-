package com.bunkalogic.bunkalist.Activities.UserProfileActivities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bunkalogic.bunkalist.Adapters.ProfileListAdapter
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.RxBus.RxBus
import com.bunkalogic.bunkalist.SharedPreferences.preferences
import com.bunkalogic.bunkalist.db.DataUsers
import com.bunkalogic.bunkalist.db.ItemListRating
import com.bunkalogic.bunkalist.db.Users
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_other_user_profile.*
import org.jetbrains.anko.intentFor

class OtherUserProfile : AppCompatActivity() {

    private lateinit var toolbar: android.support.v7.widget.Toolbar

    private var listProfileitem: ArrayList<ItemListRating> = ArrayList()
    private lateinit var adapter: ProfileListAdapter

    //Variables with otherUser's data
    private val userId = intent.extras?.getString("userId")
    private val username = intent.extras?.getString("username")
    private val userPhoto = intent.extras?.getString("userPhoto")

    private val store: FirebaseFirestore = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_user_profile)
        // we instantiated the toolbar
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        setUpOtherProfile()

        clicksListeners()
        setUpRecycler()

        subscribeToProfileListOther()
    }

    private fun setUpOtherProfile(){
         supportActionBar!!.title = username

        userNameProfile.text = username

        Glide.with(this)
            .load(userPhoto)
            .override(130, 130)
            .into(userImageProfile)


    }

    private fun clicksListeners(){

        if (preferences.userId == userId){
            buttonFollows.visibility = View.GONE
        }

        buttonFollows.setOnClickListener {
           val follow = Users(userId!!, username!!, userPhoto!!)
            RxBus.publish(DataUsers(follow))
        }

        buttonListAll.setOnClickListener {
            preferences.OtherUserId = userId
            preferences.OtherUsername = username
            startActivity(intentFor<ProfileListActivity>())
        }
    }

    private fun setUpRecycler(){
        val layoutManager = LinearLayoutManager(this)


        adapter = ProfileListAdapter(this, listProfileitem)

        recyclerOtherProfile.setHasFixedSize(true)
        recyclerOtherProfile.layoutManager = layoutManager as RecyclerView.LayoutManager?
        recyclerOtherProfile.itemAnimator = DefaultItemAnimator() as RecyclerView.ItemAnimator?
        recyclerOtherProfile.adapter = adapter
    }



    // just give me the list for other list
    private fun subscribeToProfileListOther() {

        store.collection("RatingList")
            .whereEqualTo("userId", userId)
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
