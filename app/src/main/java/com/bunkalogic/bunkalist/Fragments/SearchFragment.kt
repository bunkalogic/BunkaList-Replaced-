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
import com.bunkalogic.bunkalist.Adapters.SearchItemUserAdapter
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.Retrofit.OnGetSearchCallback
import com.bunkalogic.bunkalist.Retrofit.Response.ResultSearchAll
import com.bunkalogic.bunkalist.RxBus.RxBus
import com.bunkalogic.bunkalist.data.ViewModelSearch
import com.bunkalogic.bunkalist.db.DataUsers
import com.bunkalogic.bunkalist.db.ItemListRating
import com.bunkalogic.bunkalist.db.NewListRating
import com.bunkalogic.bunkalist.db.UserComplete
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
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
    private lateinit var adapterUser: SearchItemUserAdapter

    private lateinit var searchViewModel: ViewModelSearch
    private var searchList: List<ResultSearchAll>? = null
    private var userList: ArrayList<UserComplete> = ArrayList()

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var currentUser: FirebaseUser

    private val store: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var addItemListDBRef: CollectionReference
    private lateinit var addUserDBRef: CollectionReference

    private var itemRatingSubscription: ListenerRegistration? = null
    private lateinit var itemRatingBusListener: Disposable

    private var addUserSubscription: ListenerRegistration? = null
    private lateinit var addUserBusListener: Disposable


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _view = inflater.inflate(R.layout.fragment_search, container, false)
        setUpAddListDB()
        setUpAddUserDB()
        setUpCurrentUser()

        setUpAdapterSearch()
        setUpAdapterUser()

        onClick()
        addToNewItemRating()
        addToNewUserInDataBase()

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

    // Creating the name instance in the database
    private fun setUpAddUserDB(){
        addUserDBRef = store.collection("UserData")
    }

    // Creating the new instance in the database
    private fun saveUserData(user : UserComplete){
        addUserDBRef.add(user)
            .addOnCompleteListener {
                Log.d("FragmentSearch", "User added on firestore")
            }
            .addOnFailureListener {
                Log.d("FragmentSearch", "User error not added on firestore")
            }
    }


    // Initializing the currentUser
    private fun setUpCurrentUser(){
        currentUser = mAuth.currentUser!!
    }

   private fun setUpAdapterSearch(){
       val layoutManager = LinearLayoutManager(context)

       adapter = SearchItemAdapter(activity!!, searchList)
       _view.recyclerSearch.layoutManager = layoutManager
       _view.recyclerSearch.setHasFixedSize(true)
       _view.recyclerSearch.itemAnimator = DefaultItemAnimator()

       _view.recyclerSearch.adapter = adapter
   }

    private fun setUpAdapterUser(){
        val layoutManager = LinearLayoutManager(context)

        adapterUser = SearchItemUserAdapter(activity!!, userList)
        _view.recyclerSearch.layoutManager = layoutManager
        _view.recyclerSearch.setHasFixedSize(true)
        _view.recyclerSearch.itemAnimator = DefaultItemAnimator()

        _view.recyclerSearch.adapter = adapter
    }


    //TODO: Add user data to the database
    private fun addUserInDatabase(username: String){
        addUserSubscription = addUserDBRef
            .whereLessThanOrEqualTo("username", username)
            .addSnapshotListener(object : java.util.EventListener, EventListener<QuerySnapshot> {
                override fun onEvent(snapshot: QuerySnapshot?, exception: FirebaseFirestoreException?) {
                    exception?.let {
                        Log.d("SearchFragment", "exception")
                        return
                    }
                    snapshot?.let {
                        userList.clear()
                        val itemUser = it.toObjects(UserComplete::class.java)
                        userList.addAll(itemUser)
                        adapterUser.setDataUsername(itemUser)
                    }
                }

            })
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

            val username = editTextSearch.text.toString()
            addUserInDatabase(username)

        }
    }

   private fun addToNewItemRating() {
       itemRatingBusListener = RxBus.listen(NewListRating::class.java).subscribe {
           saveItemRatingList(it.itemListRating)
           Log.d("FragmentSearch", "${it.itemListRating}")
       }
   }

    private fun addToNewUserInDataBase(){
        addUserBusListener = RxBus.listen(DataUsers::class.java).subscribe {
            saveUserData(it.userData)
        }
    }

   override fun onDestroyView() {
       itemRatingBusListener.dispose()
       itemRatingSubscription?.remove()

       addUserBusListener.dispose()
       addUserSubscription?.remove()

       super.onDestroyView()
   }
}
