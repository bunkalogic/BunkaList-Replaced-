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
import com.bunkalogic.bunkalist.Adapters.SearchItemUserAdapter
import com.bunkalogic.bunkalist.Others.Constans

import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.RxBus.RxBus
import com.bunkalogic.bunkalist.SharedPreferences.preferences
import com.bunkalogic.bunkalist.db.DataUsers
import com.bunkalogic.bunkalist.db.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_list_follow.view.*
import org.jetbrains.anko.support.v4.toast


class ListFollowFragment : Fragment() {

    private lateinit var _view: View

    private var typeList = 0

    private lateinit var adapter : SearchItemUserAdapter
    private val userList: ArrayList<Users> = ArrayList()

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var currentUser: FirebaseUser

    private val store: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var followDBRef: CollectionReference
    private lateinit var followersDBRef: CollectionReference

    private var followSubscription: ListenerRegistration? = null
    //private lateinit var followBusListener: Disposable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            typeList = it.getInt(Constans.USER_FOLLOW)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _view = inflater.inflate(R.layout.fragment_list_follow, container, false)
        setUpCurrentUser()

        setUpRecyclerView()

        isFollowOrFollowers()


        //subscribeToNewUserFollows()

        return _view
    }

    private fun setUpFollowsDB(){
        followDBRef= store.collection("Data/Users/${preferences.userId}/ ${preferences.userName} /Follows")
    }

    private fun setUpFollowersDB(){
        followersDBRef= store.collection("Data/Users/${preferences.userId}/ ${preferences.userName} /Followers")
    }

    // Creating the new instance in the database
    //private fun saveUsers(user: Users){
    //    followDBRef.add(user)
    //        .addOnCompleteListener {
    //            Log.d("ListFollowFragment", " User Added! ")
    //        }
    //        .addOnFailureListener {
    //            Log.d("ListFollowFragment", " Error try Again ")
    //        }
    //}


    // Initializing the currentUser
    private fun setUpCurrentUser(){
        currentUser = mAuth.currentUser!!
    }

    private fun setUpRecyclerView(){
        val layoutManager = LinearLayoutManager(context)
        adapter = SearchItemUserAdapter(context!!, userList)

        _view.RecyclerFollow.setHasFixedSize(true)
        _view.RecyclerFollow.layoutManager = layoutManager
        _view.RecyclerFollow.itemAnimator = DefaultItemAnimator() as RecyclerView.ItemAnimator?
        _view.RecyclerFollow.adapter = adapter
    }

    // is responsible for checking if it is an instance of follow or is a followers instance
    private fun isFollowOrFollowers(){
        if (typeList == Constans.USER_LIST_FOLLOWS){
            setUpFollowsDB()
            subscribeToUserFollows()
        }else if (typeList == Constans.USER_LIST_FOLLOWERS){
            setUpFollowersDB()
            subscribeToUserFollowers()
        }
    }

    private fun subscribeToUserFollows(){
        followDBRef
            .addSnapshotListener(object : java.util.EventListener, EventListener<QuerySnapshot>{
                override fun onEvent(snapshot: QuerySnapshot?, exception: FirebaseFirestoreException?) {
                    exception?.let {
                        Log.d("ListFollowFragment", "Exception")
                    }

                    snapshot?.let {
                        userList.clear()
                        val users = it.toObjects(Users::class.java)
                        userList.addAll(users)
                        preferences.follows = it.size()
                        adapter.notifyDataSetChanged()
                    }
                }

            })
    }

    private fun subscribeToUserFollowers(){
        followersDBRef
            .addSnapshotListener(object : java.util.EventListener, EventListener<QuerySnapshot>{
                override fun onEvent(snapshot: QuerySnapshot?, exception: FirebaseFirestoreException?) {
                    exception?.let {
                        Log.d("ListFollowFragment", "Exception")
                    }

                    snapshot?.let {
                        userList.clear()
                        val users = it.toObjects(Users::class.java)
                        userList.addAll(users)
                        preferences.followers = it.size()
                        adapter.notifyDataSetChanged()
                    }
                }

            })
    }




    //// Using RxAndroid to make the minimum calls to the database
    //private fun subscribeToNewUserFollows(){
    //    followBusListener = RxBus.listen(DataUsers::class.java).subscribe {
    //        saveUsers(it.userData)
    //    }
    //}


    companion object {
        @JvmStatic
        fun newInstance(typeList: Int) =
            ListFollowFragment().apply {
                arguments = Bundle().apply {
                    putInt(Constans.USER_FOLLOW, typeList)
                }
            }
    }


    override fun onDestroyView() {
        //followBusListener.dispose()
        //followSubscription?.remove()
        super.onDestroyView()
    }


}
