package com.bunkalogic.bunkalist.Activities.DetailsActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import com.bumptech.glide.Glide
import com.bunkalogic.bunkalist.Adapters.TimelineChatAdapter
import com.bunkalogic.bunkalist.Notification.NotificationHandler
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.RxBus.RxBus
import com.bunkalogic.bunkalist.SharedPreferences.preferences
import com.bunkalogic.bunkalist.db.TimelineChat
import com.bunkalogic.bunkalist.db.TimelineChatEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import kotlinx.android.synthetic.main.activity_item_timeline_chat.*
import java.util.*
import kotlin.collections.ArrayList

class ItemTimelineChatActivity : AppCompatActivity() {

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    private lateinit var adapter: TimelineChatAdapter
    private val chatList: ArrayList<TimelineChat> = ArrayList()

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var currentUser: FirebaseUser

    private val store: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var timelineChatDBRef: CollectionReference

    private var chatSubscription: ListenerRegistration? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_timeline_chat)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        setUpCurrentUser()
        setUpTimelineChatDB()
        getExtrasForTimeline()
        setUpRecycler()
        setUpChatBtn()
        subscribeToChatMessage()
    }

    private fun setUpCurrentUser(){
        currentUser = mAuth.currentUser!!
    }

    private fun setUpTimelineChatDB(){
        val extrasToken = intent.extras?.getString("token")
        timelineChatDBRef = store.collection("Data/Content/timelineMessageGlobal/Chat/$extrasToken")
    }

    private fun saveMessageChat(message: TimelineChat){
        timelineChatDBRef.add(message)
            .addOnCompleteListener{
                Log.d("ItemTimelineChat", "Message Added!")
            }
            .addOnFailureListener {
                Log.d("ItemTimelineChat", "Message error, Try again")
            }

    }


    private fun getExtrasForTimeline(){
        // Extras
        val extrasUserId = intent.extras?.getString("userId")
        val extrasToken = intent.extras?.getString("token")
        val extrasUsername = intent.extras?.getString("username")
        val extrasUserPhoto = intent.extras?.getString("userPhoto")
        val extrasTitle = intent.extras?.getString("title")
        val extrasContent = intent.extras?.getString("content")
        val extrasdateAt = intent.extras?.getString("dateAt")
        val extrasNumSeason = intent.extras?.getString("numSeason")
        val extrasNumEpisode = intent.extras?.getString("numEpisode")

        supportActionBar!!.title = "Comment by $extrasUsername of $extrasTitle"

        textViewUsername.text = extrasUsername
        textViewContent.text = extrasContent
        textViewOeuvreName.text = extrasTitle
        textViewSeason.text = extrasNumSeason
        textViewCapsNumbers.text = extrasNumEpisode
        textViewDate.text = extrasdateAt

        Glide.with(this)
            .load(extrasUserPhoto)
            .override(60, 60)
            .into(imageViewUserImage)



    }

    private fun setUpRecycler(){
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        adapter = TimelineChatAdapter(this, chatList)


        recyclerTimelineChat.setHasFixedSize(true)
        recyclerTimelineChat.layoutManager = layoutManager
        recyclerTimelineChat.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerTimelineChat.adapter = adapter
    }

    private fun setUpChatBtn(){
        floatingActionButtonChatTimeline.setOnClickListener {
            val message = editTextTimelineChat.text.toString()

            if (message.isNotEmpty() && message != "text" && message != "text1234"){
                val messageEvent = TimelineChat(currentUser.uid, currentUser.displayName!!, currentUser.photoUrl.toString(), Date(), message)
                saveMessageChat(messageEvent)
                //notifyUser(currentUser.displayName!!, message)
            }
            editTextTimelineChat.text.clear()
            editTextTimelineChat.text.clearSpans()
        }



    }

    private fun notifyUser(title: String, message: String){
        //TODO: lanzar la notificacion al usuario del chat
        val extrasUserId = intent.extras?.getString("userId")

        if (extrasUserId == preferences.userId){
            NotificationHandler(this).createNotification(title, message, false)
        }
    }

    private fun subscribeToChatMessage(){
        chatSubscription = timelineChatDBRef
            .orderBy("sentAt")
            .limit(500)
            .addSnapshotListener(object : java.util.EventListener, EventListener<QuerySnapshot>{
                override fun onEvent(snapshot: QuerySnapshot?, exception: FirebaseFirestoreException?) {
                    exception?.let {
                        Log.d("ItemTimelineChat", "Exception!")
                    }
                    snapshot?.let {
                        chatList.clear()
                        val message = it.toObjects(TimelineChat::class.java)
                        chatList.addAll(message)
                        adapter.notifyDataSetChanged()
                        recyclerTimelineChat.smoothScrollToPosition(chatList.size)
                        RxBus.publish(TimelineChatEvent(chatList.size))
                    }
                }

            })
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
