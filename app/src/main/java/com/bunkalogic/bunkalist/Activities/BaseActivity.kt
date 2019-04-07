package com.bunkalogic.bunkalist.Activities

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.view.MenuItem
import com.bunkalogic.bunkalist.Adapters.PagerAdapter
import com.bunkalogic.bunkalist.Fragments.*
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.SharedPreferences.preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*


/**
 *  Created by @author Naim Dridi on 18/02/19
 */

class BaseActivity : AppCompatActivity() {

    private var prevBottomSelected: MenuItem? = null

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var currentUser: FirebaseUser
//
    //private val store: FirebaseFirestore = FirebaseFirestore.getInstance()
    //private lateinit var addUserDBRef: CollectionReference
//
    //private var addUserSubscription: ListenerRegistration? = null
    //private lateinit var addUserBusListener: Disposable
//
    //private var userList: ArrayList<UserComplete> = ArrayList()
//
    //private var listFollow : ArrayList<Users> = ArrayList()
//
    //private var listFollowers : ArrayList<Users> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        whatIsMode()
        setUpViewPager(getPagerAdapter())
        setUpBottomNavigationBar()
        setUpCurrentUser()
        //getNewUserData()
        //setUpAddUserDB()
        //addUserInDatabase()
        //addToNewUserInDataBase()

    }

    // Initializing the currentUser
    private fun setUpCurrentUser(){
        currentUser = mAuth.currentUser!!
    }


    //// Creating the name instance in the database
    //private fun setUpAddUserDB(){
    //    addUserDBRef = store.collection("UserData")
    //}
//
    //// Creating the new instance in the database
    //private fun saveUserData(user : UserComplete){
    //    addUserDBRef.add(user)
    //        .addOnCompleteListener {
    //            Log.d("FragmentSearch", "User added on firestore")
    //        }
    //        .addOnFailureListener {
    //            Log.d("FragmentSearch", "User error not added on firestore")
    //        }
    //}



    // Collect all fragment implements in the adapter
    private fun getPagerAdapter(): PagerAdapter{
        val adapter = PagerAdapter(supportFragmentManager)
        adapter.addFragment(TimeLineFragment())
        adapter.addFragment(TopsAndReviewFragment())
        adapter.addFragment(SearchFragment())
        adapter.addFragment(ProfileFragment())
        adapter.addFragment(SettingsFragment())

        return adapter
    }

    private fun setUpViewPager(adapter: PagerAdapter){
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = adapter.count
        viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(position: Int) {

                if (prevBottomSelected == null){
                    btn_nav_View.menu.getItem(0).isChecked = false
                }else{
                    prevBottomSelected!!.isChecked = false
                }
                btn_nav_View.menu.getItem(position).isChecked = true
                prevBottomSelected = btn_nav_View.menu.getItem(position)

            }

        })
    }


    private fun setUpBottomNavigationBar(){
        btn_nav_View.setOnNavigationItemSelectedListener {Item ->
            when(Item.itemId){
                R.id.bottom_nav_timeline -> {
                    viewPager.currentItem = 0; true
                }

                R.id.bottom_nav_top_and_review -> {
                    viewPager.currentItem = 1; true
                }

                R.id.bottom_nav_search -> {
                    viewPager.currentItem = 2; true
                }

                R.id.bottom_nav_profile -> {
                    viewPager.currentItem = 3; true
                }

                R.id.bottom_nav_settings -> {
                    viewPager.currentItem = 4; true
                }
                else -> false
            }
        }
    }

    // Check if the user has chosen some theme mode in the ModeDayOrNightActivity
    private fun whatIsMode(){
        val mode_light = 1
        val mode_dark = 2
        val mode_custom = 3
        if (preferences.mode == mode_light){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        if (preferences.mode == mode_dark){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        if (preferences.mode == mode_custom){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO)
        }
        AppCompatDelegate.getDefaultNightMode()

    }

    //TODO: Add user data to the database
    //private fun getNewUserData(){
    //    val userId = preferences.userId
    //    val userName = preferences.userName
    //    val userPhoto = currentUser.photoUrl
//
    //    if(userName.isNullOrEmpty()){
    //        val userComplete = UserComplete(userId, "", "", listFollow, listFollowers)
    //        RxBus.publish(DataUsers(userComplete))
    //    }else{
    //        val userComplete = UserComplete(userId, userName, userPhoto.toString(), listFollow, listFollowers)
    //        RxBus.publish(DataUsers(userComplete))
    //    }
//
//
    //}
//
    //private fun addUserInDatabase(){
    //    addUserSubscription = addUserDBRef
    //        .addSnapshotListener(object : java.util.EventListener, EventListener<QuerySnapshot>{
    //            override fun onEvent(snapshot: QuerySnapshot?, exception: FirebaseFirestoreException?) {
    //                exception?.let {
    //                    Log.d("BaseActivity", "exception")
    //                    return
    //                }
    //                snapshot?.let {
    //                    userList.clear()
    //                    val user = it.toObjects(UserComplete::class.java)
    //                    userList.addAll(user)
    //                }
    //            }
//
    //        })
    //}
//
    //private fun addToNewUserInDataBase(){
    //    addUserBusListener = RxBus.listen(DataUsers::class.java).subscribe {
    //        saveUserData(it.userData)
    //    }
    //}
//
    //override fun onDestroy() {
    //    addUserBusListener.dispose()
    //    addUserSubscription?.remove()
    //    super.onDestroy()
    //}


}
