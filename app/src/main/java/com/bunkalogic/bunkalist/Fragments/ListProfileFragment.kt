package com.bunkalogic.bunkalist.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bunkalogic.bunkalist.Adapters.ProfileListAdapter
import com.bunkalogic.bunkalist.Others.Constans

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
import org.jetbrains.anko.support.v4.toast


class ListProfileFragment : Fragment() {

    private lateinit var _view: View

    private var typeList = 3

    private val listProfileitem: ArrayList<ItemListRating> = ArrayList()
    private lateinit var adapter: ProfileListAdapter

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

        setUpRecycler()

        getListOeuvre()
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
                Log.d("ListProfileFragment", "itemRating added on firestore")
            }
            .addOnFailureListener {
                toast("Fail add in your list")
                Log.d("ListProfileFragment", "itemRating error not added on firestore")
            }
    }

    // Initializing the currentUser
    private fun setUpCurrentUser(){
        currentUser = mAuth.currentUser!!
    }

    private fun setUpRecycler(){
        val layoutManager = LinearLayoutManager(context)


        adapter = ProfileListAdapter(context!!, listProfileitem)

        _view.recyclerAllList.setHasFixedSize(true)
        _view.recyclerAllList.layoutManager = layoutManager
        _view.recyclerAllList.itemAnimator = DefaultItemAnimator()
        _view.recyclerAllList.adapter = adapter
    }

    private fun getListOeuvre(){

        if (typeList == Constans.ALL_LIST){
            subscribeToProfileAllList()
        }
        if (typeList == Constans.MOVIE_LIST){
            subscribeToProfileListMovie()
        }
        if (typeList == Constans.SERIE_LIST){
            subscribeToProfileListSeries()
        }
        if (typeList == Constans.ANIME_LIST){
            subscribeToProfileListAnime()
        }

    }

    private fun subscribeToProfileAllList(){
        itemRatingSubscription = addItemListDBRef
            .whereEqualTo("userId", preferences.userId) // Here filter the list getting only item with an equal value preferences.userId
            .orderBy("finalRate", Query.Direction.DESCENDING)
            .addSnapshotListener(object : java.util.EventListener, EventListener<QuerySnapshot>{
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

    private fun subscribeToProfileListMovie(){
        itemRatingSubscription = addItemListDBRef
            .whereEqualTo("userId", preferences.userId) // Here filter the list getting only item with an equal value preferences.userId
            .whereEqualTo("typeOeuvre", Constans.MOVIE_LIST)
            .orderBy("finalRate", Query.Direction.DESCENDING)
            .addSnapshotListener(object : java.util.EventListener, EventListener<QuerySnapshot>{
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

    private fun subscribeToProfileListSeries(){
        itemRatingSubscription = addItemListDBRef
            .whereEqualTo("userId", preferences.userId) // Here filter the list getting only item with an equal value preferences.userId
            .whereEqualTo("typeOeuvre", Constans.SERIE_LIST)
            .orderBy("finalRate", Query.Direction.DESCENDING)
            .addSnapshotListener(object : java.util.EventListener, EventListener<QuerySnapshot>{
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

    private fun subscribeToProfileListAnime(){
        itemRatingSubscription = addItemListDBRef
            .whereEqualTo("userId", preferences.userId) // Here filter the list getting only item with an equal value preferences.userId
            .whereEqualTo("typeOeuvre", Constans.ANIME_LIST)
            .orderBy("finalRate", Query.Direction.DESCENDING)
            .addSnapshotListener(object : java.util.EventListener, EventListener<QuerySnapshot>{
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

    companion object {
        @JvmStatic
        fun newInstance(typeList: Int) =
            ListProfileFragment().apply {
                arguments = Bundle().apply {
                    putInt(Constans.TYPE_LIST, typeList)
                }
            }
    }
}
