package com.bunkalogic.bunkalist.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.ArrayMap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.allattentionhere.fabulousfilter.AAH_FabulousFragment
import com.bunkalogic.bunkalist.Adapters.ProfileListAdapter
import com.bunkalogic.bunkalist.Fragments.FabFilter.FabFilterListFragment
import com.bunkalogic.bunkalist.Others.Constans

import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.SharedPreferences.preferences
import com.bunkalogic.bunkalist.db.ItemListRating
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.fragment_list_profile.*
import kotlinx.android.synthetic.main.fragment_list_profile.view.*



class ListProfileFragment : Fragment() {


    private lateinit var _view: View

    lateinit var mAdView : AdView

    private var typeList = 0

    private var listProfileitem: ArrayList<ItemListRating> = ArrayList()
    private lateinit var adapter: ProfileListAdapter

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var currentUser: FirebaseUser

    private val store: FirebaseFirestore = FirebaseFirestore.getInstance()

    var list_filters: ArrayMap<String, Int> = ArrayMap()

    //variables to collect the data from to filter
    var statusFinalId = -1
    var ratingFieldFinal : String = ""
    var filterOrder = Query.Direction.DESCENDING
    val orderDesc = Query.Direction.DESCENDING
    val orderAdsc = Query.Direction.ASCENDING


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            typeList = it.getInt(Constans.TYPE_LIST)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _view =  inflater.inflate(R.layout.fragment_list_profile, container, false)
        addBannerAds()
        setUpCurrentUser()

        whatUserIs()
        setUpRecycler()
        onClick()


        return _view
    }

    @SuppressLint("RestrictedApi")
    private fun onClick(){
        _view.fabFilter.setOnClickListener {
            val dialogFabList: FabFilterListFragment = FabFilterListFragment.newInstance()
            dialogFabList.setParentFab(fabFilter)
            dialogFabList.show(activity?.supportFragmentManager, dialogFabList.tag)
            _view.fabFilter.visibility = View.GONE
            _view.fabFilterOk.visibility = View.VISIBLE
        }

        _view.fabFilterOk.setOnClickListener {
            resultFilter(Constans.applied_list_filter)
            _view.fabFilterOk.visibility = View.GONE
            _view.fabFilterClean.visibility = View.VISIBLE
        }

        _view.fabFilterClean.setOnClickListener {
            _view.fabFilterClean.visibility = View.GONE
            _view.fabFilter.visibility = View.VISIBLE
        }



    }


    private fun resultFilter(applied_filters: ArrayMap<String, Int> = ArrayMap()){
        if (applied_filters.isNotEmpty() && applied_filters.size != 0){
                val status = context!!.getString(R.string.fab_filter_list_profile_status)
                val rating = context!!.getString(R.string.fab_filter_list_profile_rating)
                val order = context!!.getString(R.string.fab_filter_list_profile_order)

                for ((key, value) in applied_filters){
                    Log.d("ListProfileFragment", "list filter id keys: $key")

                    when(key){
                        status ->{
                            statusFinalId = value
                        }
                        rating -> {
                            // pasar field del .orderBy
                            when(value){
                                Constans.filter_rating_story -> ratingFieldFinal = Constans.filter_rating_story_name
                                Constans.filter_rating_characters -> ratingFieldFinal = Constans.filter_rating_characters_name
                                Constans.filter_rating_soundtrack -> ratingFieldFinal = Constans.filter_rating_soundtrack_name
                                Constans.filter_rating_photography -> ratingFieldFinal = Constans.filter_rating_photography_name
                                Constans.filter_rating_enjoyment -> ratingFieldFinal = Constans.filter_rating_enjoyment_name
                                Constans.filter_rating_final -> ratingFieldFinal = Constans.filter_rating_final_name
                            }
                        }
                        order ->{
                            // pasar Query.Direction
                            when(value){
                                Constans.filter_order_descend -> filterOrder = orderDesc
                                Constans.filter_order_ascendant -> filterOrder = orderAdsc
                            }
                        }
                    }
                }
            subscribeToProfileListMovie(currentUser.uid, Constans.filter_status_name, statusFinalId, ratingFieldFinal, filterOrder)
            subscribeToProfileListSeries(currentUser.uid, Constans.filter_status_name, statusFinalId, ratingFieldFinal, filterOrder)
            subscribeToProfileListAnime(currentUser.uid, Constans.filter_status_name, statusFinalId, ratingFieldFinal, filterOrder)
        }

    }



    fun getAppliedFilters() : ArrayMap<String, Int>{
        return list_filters
    }


    //initializing the banner in this activity
    private fun addBannerAds(){
        mAdView = _view.findViewById(R.id.adViewBannerFragmentList)
        // Ad request to show on the banner
        val adRequest = AdRequest.Builder().build()
        // Associate the request to the banner
        mAdView.loadAd(adRequest)
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

    @SuppressLint("RestrictedApi")
    private fun whatUserIs(){
        if (preferences.OtherUserId.isNullOrEmpty()){
            getListOeuvre(preferences.userId!!)
        }else{
            getListOeuvre(preferences.OtherUserId!!)
            _view.fabFilter.visibility = View.INVISIBLE
        }
    }

    private fun getListOeuvre(userId: String){

        when (typeList) {
            Constans.MOVIE_LIST -> subscribeToProfileListMovie(userId, Constans.typeOuevre, Constans.MOVIE_LIST, Constans.filter_rating_final_name, orderDesc)
            Constans.SERIE_LIST -> subscribeToProfileListSeries(userId, Constans.typeOuevre, Constans.SERIE_LIST, Constans.filter_rating_final_name, orderDesc)
            Constans.ANIME_LIST -> subscribeToProfileListAnime(userId, Constans.typeOuevre, Constans.ANIME_LIST, Constans.filter_rating_final_name, orderDesc)
            Constans.MOVIE_TOP -> subscribeToProfileTopMovie(userId)
            Constans.SERIE_TOP -> subscribeToProfileTopSeries(userId)
            Constans.ANIME_TOP -> subscribeToProfileTopAnime(userId)
        }

    }




    // just give me the movies
    private fun subscribeToProfileListMovie(
        userId: String,
        fieldWhereEqualTo: String,
        valueWhereEqualTo: Int,
        fieldOrderBy: String,
        orderType: Query.Direction,
        value1: String = Constans.typeOuevre,
        value2: Int = Constans.MOVIE_LIST
        ){
        if (fieldWhereEqualTo.isEmpty() && valueWhereEqualTo == -1){
            Log.d("ListProfileFragment", "$userId")
            store.collection("Users/$userId/RatingList")
                .whereEqualTo(value1, value2)
                .orderBy(fieldOrderBy, orderType)
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
        }else{
            Log.d("ListProfileFragment", "$userId")
            store.collection("Users/$userId/RatingList")
                .whereEqualTo(value1, value2)
                .whereEqualTo(fieldWhereEqualTo, valueWhereEqualTo)
                .orderBy(fieldOrderBy, orderType)
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

    }
    // just give me the series
    private fun subscribeToProfileListSeries(
        userId: String,
        fieldWhereEqualTo: String,
        valueWhereEqualTo: Int,
        fieldOrderBy: String,
        orderType: Query.Direction,
        value1: String = Constans.typeOuevre,
        value2: Int = Constans.SERIE_LIST){
        if (fieldWhereEqualTo.isEmpty() && valueWhereEqualTo == -1){
            Log.d("ListProfileFragment", "$userId")
            store.collection("Users/$userId/RatingList")
                .whereEqualTo(value1, value2)
                .orderBy(fieldOrderBy, orderType)
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
        }else{
            Log.d("ListProfileFragment", "$userId")
            store.collection("Users/$userId/RatingList")
                .whereEqualTo(value1, value2)
                .whereEqualTo(fieldWhereEqualTo, valueWhereEqualTo)
                .orderBy(fieldOrderBy, orderType)
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
    }
    // just give me the anime
    private fun subscribeToProfileListAnime(
        userId: String,
        fieldWhereEqualTo: String,
        valueWhereEqualTo: Int,
        fieldOrderBy: String,
        orderType: Query.Direction,
        value1: String = Constans.typeOuevre,
        value2: Int = Constans.ANIME_LIST){
        if (fieldWhereEqualTo.isEmpty() && valueWhereEqualTo == -1){
            Log.d("ListProfileFragment", "$userId")
            store.collection("Users/$userId/RatingList")
                .whereEqualTo(value1, value2)
                .orderBy(fieldOrderBy, orderType)
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
        }else{
            Log.d("ListProfileFragment", "$userId")
            store.collection("Users/$userId/RatingList")
                .whereEqualTo(value1, value2)
                .whereEqualTo(fieldWhereEqualTo, valueWhereEqualTo)
                .orderBy(fieldOrderBy, orderType)
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
