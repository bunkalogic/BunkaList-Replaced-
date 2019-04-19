package com.bunkalogic.bunkalist.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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

    private var typeList = 0

    private var listProfileitem: ArrayList<ItemListRating> = ArrayList()
    private lateinit var adapter: ProfileListAdapter

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var currentUser: FirebaseUser

    private val store: FirebaseFirestore = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            typeList = it.getInt(Constans.TYPE_LIST)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _view =  inflater.inflate(R.layout.fragment_list_profile, container, false)
        setUpCurrentUser()

        whatUserIs()
        setUpRecycler()

        return _view
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

    private fun whatUserIs(){
        if (preferences.OtherUserId.isNullOrEmpty()){
            getListOeuvre(preferences.userId!!)
        }else{
            getListOeuvre(preferences.OtherUserId!!)
        }
    }

    private fun getListOeuvre(userId: String){

        if (typeList == Constans.MOVIE_LIST){
            subscribeToProfileListMovie(userId)
        }else if (typeList == Constans.SERIE_LIST){
            subscribeToProfileListSeries(userId)
        }else if (typeList == Constans.ANIME_LIST){
            subscribeToProfileListAnime(userId)
        }else if (typeList == Constans.MOVIE_TOP){
            subscribeToProfileTopMovie(userId)
        }else if (typeList == Constans.SERIE_TOP){
            subscribeToProfileTopSeries(userId)
        }else if (typeList == Constans.ANIME_TOP){
            subscribeToProfileTopAnime(userId)
        }

    }


    // just give me the movies
    private fun subscribeToProfileListMovie(userId: String){
        Log.d("ListProfileFragment", "$userId")
        store.collection("Users/$userId/RatingList")
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
                        preferences.sizeMovies = it.size()
                        adapter.notifyDataSetChanged()
                    }
                }

            })
    }
    // just give me the series
    private fun subscribeToProfileListSeries(userId: String){
        store.collection("Users/$userId/RatingList")
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
                        preferences.sizeSeries = it.size()
                        adapter.notifyDataSetChanged()
                    }
                }

            })
    }
    // just give me the anime
    private fun subscribeToProfileListAnime(userId: String){
        store.collection("Users/$userId/RatingList")
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
                        preferences.sizeAnime = it.size()
                        adapter.notifyDataSetChanged()
                    }
                }

            })
    }

    // just give me the movies top 10
    private fun subscribeToProfileTopMovie(userId: String){
        store.collection("Users/$userId/RatingList")
            .whereEqualTo("typeOeuvre", Constans.MOVIE_LIST)
            .orderBy("finalRate", Query.Direction.DESCENDING)
            .limit(10)
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
    // just give me the series top 10
    private fun subscribeToProfileTopSeries(userId: String){
        store.collection("Users/$userId/RatingList")
            .whereEqualTo("typeOeuvre", Constans.SERIE_LIST)
            .orderBy("finalRate", Query.Direction.DESCENDING)
            .limit(10)
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
    // just give me the anime top 10
    private fun subscribeToProfileTopAnime(userId: String){
        store.collection("Users/$userId/RatingList")
            .whereEqualTo("typeOeuvre", Constans.ANIME_LIST)
            .orderBy("finalRate", Query.Direction.DESCENDING)
            .limit(10)
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


    companion object {
        @JvmStatic
        fun newInstance(typeList: Int) =
            ListProfileFragment().apply {
                arguments = Bundle().apply {
                    putInt(Constans.TYPE_LIST, typeList)
                }
            }
    }



    override fun onDestroyView() {
        preferences.deleteOtherUser()
        super.onDestroyView()
    }
}
