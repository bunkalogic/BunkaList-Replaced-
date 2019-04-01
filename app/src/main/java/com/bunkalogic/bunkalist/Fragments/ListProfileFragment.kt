package com.bunkalogic.bunkalist.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.RxBus.RxBus
import com.bunkalogic.bunkalist.db.ItemListRating
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.support.v4.toast


class ListProfileFragment : Fragment() {

    private lateinit var _view: View

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var currentUser: FirebaseUser

    private val store: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var addItemListDBRef: CollectionReference

    private var itemRatingSubscription: ListenerRegistration? = null
    private lateinit var itemRatingBusListener: Disposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _view =  inflater.inflate(R.layout.fragment_list_profile, container, false)
        setUpAddListDB()
        setUpCurrentUser()
        addToNewItemRating()

        return _view
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
                Log.d("SearchItemDetailsAct", "itemRating added on firestore")
            }
            .addOnFailureListener {
                toast("Fail add in your list")
                Log.d("SearchItemDetailsAct", "itemRating error not added on firestore")
            }
    }

    // Initializing the currentUser
    private fun setUpCurrentUser(){
        currentUser = mAuth.currentUser!!
    }








    private fun addToNewItemRating(){
        itemRatingBusListener = RxBus.listen(ItemListRating::class.java).subscribe {
            saveItemRatingList(it)
        }
    }

    override fun onDestroyView() {
        itemRatingBusListener.dispose()
        itemRatingSubscription?.remove()
        super.onDestroyView()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ListProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            ListProfileFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
