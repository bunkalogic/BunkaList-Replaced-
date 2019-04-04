package com.bunkalogic.bunkalist.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bunkalogic.bunkalist.Dialog.ReviewDialog

import com.bunkalogic.bunkalist.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_topandreview.view.*


/**
 *  Created by @author Naim Dridi on 25/02/19
 */

class TopsAndReviewFragment : Fragment() {

    private lateinit var _view: View

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
        setUpCurrentUser()
        setUpFab()

        return _view
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

}
