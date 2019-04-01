package com.bunkalogic.bunkalist.Fragments



import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bunkalogic.bunkalist.Adapters.SearchItemAdapter
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.Retrofit.OnGetSearchCallback
import com.bunkalogic.bunkalist.Retrofit.Response.ResultSearchAll
import com.bunkalogic.bunkalist.RxBus.RxBus
import com.bunkalogic.bunkalist.data.ViewModelSearch
import com.bunkalogic.bunkalist.db.ItemListRating
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import org.jetbrains.anko.support.v4.toast


/**
 *  Created by @author Naim Dridi on 25/02/19
 */


class SearchFragment : Fragment() {

    private lateinit var _view: View
    private lateinit var adapter: SearchItemAdapter
    private lateinit var searchViewModel: ViewModelSearch
    var searchList: List<ResultSearchAll>? = null

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var currentUser: FirebaseUser

    private val store: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var addItemListDBRef: CollectionReference

    private var itemRatingSubscription: ListenerRegistration? = null
    private lateinit var itemRatingBusListener: Disposable


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _view = inflater.inflate(R.layout.fragment_search, container, false)
        setUpAddListDB()
        setUpCurrentUser()
        setUpAdapter()
        onClick()
        addToNewItemRating()

        return _view
    }

    private fun setUpAddListDB(){
        addItemListDBRef = store.collection("RatingList")
    }

    // Creating the new instance in the database
    private fun saveItemRatingList(itemListRating: ItemListRating){
        addItemListDBRef.add(itemListRating)
            .addOnCompleteListener {
                Log.d("FragmentSearch", "itemRating added on firestore")
                toast("Add in your list")
            }
            .addOnFailureListener {
                Log.d("FragmentSearch", "itemRating error not added on firestore")
            }
    }


    // Initializing the currentUser
    private fun setUpCurrentUser(){
        currentUser = mAuth.currentUser!!
    }

   private fun setUpAdapter(){
       val layoutManager = LinearLayoutManager(context)

       adapter = SearchItemAdapter(activity!!, searchList)
       _view.recyclerSearch.layoutManager = layoutManager
       _view.recyclerSearch.setHasFixedSize(true)
       _view.recyclerSearch.itemAnimator = DefaultItemAnimator()

       _view.recyclerSearch.adapter = adapter
   }



    private fun getSearchAll(callback: OnGetSearchCallback) {
        searchViewModel = ViewModelProviders.of(activity!!).get(ViewModelSearch::class.java)
        val title = editTextSearch.text.toString()

        searchViewModel.getSearchAll(title, callback)

    }

    private fun onClick(){

        _view.imageViewSearch.setOnClickListener {
            getSearchAll(object: OnGetSearchCallback{
                override fun onSuccess(all: List<ResultSearchAll>) {
                    Log.d("FragmentSearch", "On success data")
                    if (all.isEmpty()){
                        toast("Please search again")
                    }else{
                        adapter.setData(all)
                    }
                }

                override fun onError() {
                  toast("Please check your internet connection")
               }
            })
        }
    }

    private fun addToNewItemRating(){
        itemRatingBusListener = RxBus.listen(ItemListRating::class.java).subscribe {
            saveItemRatingList(it)
            Log.d("FragmentSearch", "$it")
        }
    }

    override fun onDestroyView() {
        itemRatingBusListener.dispose()
        itemRatingSubscription?.remove()
        super.onDestroyView()
    }
}
