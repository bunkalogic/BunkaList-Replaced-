package com.bunkalogic.bunkalist.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bunkalogic.bunkalist.Activities.DetailsActivities.ListMovieActivity
import com.bunkalogic.bunkalist.Activities.DetailsActivities.ListSeriesActivity
import com.bunkalogic.bunkalist.Adapters.ReviewAdapter
import com.bunkalogic.bunkalist.Dialog.ReviewDialog
import com.bunkalogic.bunkalist.Others.Constans

import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.RxBus.RxBus
import com.bunkalogic.bunkalist.db.NewReview
import com.bunkalogic.bunkalist.db.NewReviewEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_topandreview.view.*
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast


/**
 *  Created by @author Naim Dridi on 25/02/19
 */

class TopsAndReviewFragment : Fragment() {

    private lateinit var _view: View

    private val reviewList: ArrayList<NewReview> = ArrayList()
    private lateinit var adapter : ReviewAdapter

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var currentUser: FirebaseUser

    private val store: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var reviewDBRef: CollectionReference

    private var reviewSubscription: ListenerRegistration? = null
    private lateinit var reviewBusListener: Disposable


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _view = inflater.inflate(R.layout.fragment_topandreview, container, false)
        setUpReviewDB()
        setUpCurrentUser()

        setUpRecyclerView()
        setUpFab()
        onClick()

        subscribeToReviews()
        subscribeToNewReview()

        return _view
    }

    fun onClick(){

        _view.buttonMoviesPopular.setOnClickListener {
            startActivity(intentFor<ListMovieActivity>())
        }

        _view.buttonSeriesPopular.setOnClickListener {
            startActivity(intentFor<ListSeriesActivity>())
        }



    }

    private fun setUpReviewDB(){
        reviewDBRef= store.collection("Data/Content/ReviewExt")
    }

    // Creating the new instance in the database
    private fun saveReview(itemReview : NewReview){
        reviewDBRef.add(itemReview)
            .addOnCompleteListener {
                toast("Review Added!")
            }
            .addOnFailureListener { toast("Error try Again") }
    }


    // Initializing the currentUser
    private fun setUpCurrentUser(){
        currentUser = mAuth.currentUser!!
    }

    fun setUpFab(){
        _view.fabNewReviews.setOnClickListener {
            ReviewDialog().show(fragmentManager, "")
        }
    }

    fun setUpRecyclerView(){
        val layoutManager = LinearLayoutManager(context)
        adapter = ReviewAdapter(context!!, reviewList)

        _view.recyclerReviews.setHasFixedSize(true)
        _view.recyclerReviews.layoutManager = layoutManager
        _view.recyclerReviews.itemAnimator = DefaultItemAnimator()
        _view.recyclerReviews.adapter = adapter
    }

    fun subscribeToReviews(){
        reviewSubscription = reviewDBRef
            .orderBy("sentAt")
            .addSnapshotListener(object : java.util.EventListener, EventListener<QuerySnapshot>{

                override fun onEvent(snapshot: QuerySnapshot?, exception: FirebaseFirestoreException?) {
                    exception?.let {
                        Log.d("ReviewFragment", "Exception review!")
                    }
                    snapshot?.let {
                        reviewList.clear()
                        val review = it.toObjects(NewReview::class.java)
                        reviewList.addAll(review)
                        adapter.notifyDataSetChanged()
                    }
                }

            })
    }


    // Using RxAndroid to make the minimum calls to the database
    private fun subscribeToNewReview(){
        reviewBusListener = RxBus.listen(NewReviewEvent::class.java).subscribe {
            saveReview(it.review)
        }
    }

    override fun onDestroyView() {
        reviewBusListener.dispose()
       reviewSubscription?.remove()
        super.onDestroyView()
    }

}
