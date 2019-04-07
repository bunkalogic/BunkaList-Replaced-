package com.bunkalogic.bunkalist.Fragments


import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bunkalogic.bunkalist.Activities.UserProfileActivities.ProfileListActivity
import com.bunkalogic.bunkalist.Adapters.ProfileListAdapter

import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.RxBus.RxBus
import com.bunkalogic.bunkalist.SharedPreferences.preferences
import com.bunkalogic.bunkalist.db.ItemListRating
import com.bunkalogic.bunkalist.db.NewListRating
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_list_profile.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast

/**
 *  Created by @author Naim Dridi on 25/02/19
 */


class ProfileFragment : Fragment() {

    private lateinit var _view: View

    private var listProfileitem: ArrayList<ItemListRating> = ArrayList()
    private lateinit var adapter: ProfileListAdapter

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var currentUser: FirebaseUser

    private val store: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var addItemListDBRef: CollectionReference

    private var itemRatingSubscription: ListenerRegistration? = null
    private lateinit var itemRatingBusListener: Disposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _view = inflater.inflate(R.layout.fragment_profile, container, false)
        setUpAddListDB()
        setUpCurrentUser()


        setUpCurrentUserUI()
        onClick()
        setUpRecycler()

        subscribeToProfileAllList()
        addToNewItemRating()


        return _view
    }

    // Initializing the currentUser
    private fun setUpCurrentUser(){
        currentUser = mAuth.currentUser!!
    }

    // Creating the name instance in the database
    private fun setUpAddListDB(){
        addItemListDBRef = store.collection("RatingList")
    }

    // Creating the new instance in the database
    private fun saveItemRatingList(itemListRating: ItemListRating){
        addItemListDBRef.add(itemListRating)
            .addOnCompleteListener {
                toast("Add in your list")
                Log.d("ProfileFragment", "itemRating added on firestore")
            }
            .addOnFailureListener {
                toast("Fail add in your list")
                Log.d("ProfileFragment", "itemRating error not added on firestore")
            }
    }

    // This is responsible for giving me the full list
    private fun subscribeToProfileAllList(){
        itemRatingSubscription = addItemListDBRef
            .whereEqualTo("userId", preferences.userId) // Here filter the list getting only item with an equal value preferences.userId
            .addSnapshotListener(object : java.util.EventListener, EventListener<QuerySnapshot> {
                override fun onEvent(snapshot: QuerySnapshot?, exception: FirebaseFirestoreException?) {
                    exception?.let {
                        Log.d("ProfileFragment", "exception")
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

    private fun setUpRecycler(){
        val layoutManager = LinearLayoutManager(context)


        adapter = ProfileListAdapter(context!!, listProfileitem)

        _view.recyclerProfileAll.setHasFixedSize(true)
        _view.recyclerProfileAll.layoutManager = layoutManager
        _view.recyclerProfileAll.itemAnimator = DefaultItemAnimator() as RecyclerView.ItemAnimator?
        _view.recyclerProfileAll.adapter = adapter
    }

    private fun setUpCurrentUserUI(){

        _view.userNameProfile.text = currentUser.displayName

            Glide.with(this)
                .load(currentUser.photoUrl)
                .apply(RequestOptions.circleCropTransform()
                    .override(160, 160))
                .into(_view.userImageProfile)


        _view.numberMovie.text = "Movies views: " + preferences.sizeMovies
        _view.numberSeries.text = "Series views: " + preferences.sizeSeries
        _view.numberAnime.text = "Anime views: " + preferences.sizeAnime

    }

    fun onClick(){
        _view.buttonListAll.setOnClickListener { startActivity(intentFor<ProfileListActivity>()) }

    }

    private fun addToNewItemRating(){
        itemRatingBusListener = RxBus.listen(NewListRating::class.java).subscribe {
            saveItemRatingList(it.itemListRating)
        }
    }

    override fun onDestroyView() {
        itemRatingBusListener.dispose()
        itemRatingSubscription?.remove()
        super.onDestroyView()
    }


}
