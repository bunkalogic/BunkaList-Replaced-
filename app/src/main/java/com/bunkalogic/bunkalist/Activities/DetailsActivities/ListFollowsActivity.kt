package com.bunkalogic.bunkalist.Activities.DetailsActivities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bunkalogic.bunkalist.Fragments.ListFollowFragment
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.R

class ListFollowsActivity : AppCompatActivity() {

    private lateinit var toolbar: android.support.v7.widget.Toolbar

    private val follows = 1
    private val followers = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_follows)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        isFollowOrFollowers()

    }

    private fun isFollowOrFollowers(){
        val followsExtras = intent.extras.getInt("follow")
        val followersExtras = intent.extras.getInt("followers")

        if (followsExtras == follows){
            supportActionBar!!.title = getString(R.string.fragment_profile_follow_list)
            val fragmentFollow = ListFollowFragment.newInstance(Constans.USER_LIST_FOLLOWS)
            supportFragmentManager.beginTransaction()
                .replace(R.id.contentFollow, fragmentFollow)
                .commit()

        }else if (followersExtras == followers){
            supportActionBar!!.title = getString(R.string.fragment_profile_followers_list)
            val fragmentFollowers = ListFollowFragment.newInstance(Constans.USER_LIST_FOLLOWERS)
            supportFragmentManager.beginTransaction()
                .replace(R.id.contentFollow, fragmentFollowers)
                .commit()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
