package com.bunkalogic.bunkalist.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bunkalogic.bunkalist.Adapters.TimelineAdapter
import com.bunkalogic.bunkalist.Dialog.TimeLineDialog
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.RxBus.RxBus
import com.bunkalogic.bunkalist.db.NewTimeLineEvent
import com.bunkalogic.bunkalist.db.TimelineMessage
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_timeline.view.*
import org.jetbrains.anko.support.v4.toast
import java.util.*


/**
 *  Created by @author Naim Dridi on 25/02/19
 */


class TimeLineFragment : Fragment() {

    private lateinit var _view: View


    private val timelineList: ArrayList<TimelineMessage> = ArrayList()
    private lateinit var adapter: TimelineAdapter

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var currentUser: FirebaseUser

    private val store: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var timelineDBRef: CollectionReference

    private var tlmessageSubscription: ListenerRegistration? = null
    private lateinit var tlmessageBusListener: Disposable

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _view = inflater.inflate(R.layout.fragment_timeline, container, false)
        setUpTimeLineDB()
        setUpCurrentUser()

        setUpRecyclerView()
        setUpFab()

        subscribeToTimelineMessage()
        subscribeToNewMessageTimeLine()

        return _view
    }

    private fun setUpTimeLineDB(){
        timelineDBRef = store.collection("timelineMessage")
    }

    // Creating the new instance in the database
    private fun saveTimelistMessage(messagetl: TimelineMessage){
        val newMessageTimeLine = HashMap<String, Any>()
        newMessageTimeLine["userId"] = messagetl.userId
        newMessageTimeLine["user"] = messagetl.username
        newMessageTimeLine["profileImageURL"] = messagetl.profileImageUrl
        newMessageTimeLine["sentAt"] = messagetl.sentAt
        newMessageTimeLine["oeuvreName"] = messagetl.oeuvreName
        newMessageTimeLine["numSeason"] = messagetl.numSeason
        newMessageTimeLine["numChapter"] = messagetl.numChapter
        newMessageTimeLine["content"] = messagetl.content

        timelineDBRef.add(newMessageTimeLine)
            .addOnCompleteListener {
                toast("Message Added!")
            }
            .addOnFailureListener { toast("Error try Again") }
    }


    //instantiating the currentUser
    private fun setUpCurrentUser(){
        currentUser = mAuth.currentUser!!
    }

    private fun setUpFab(){

        _view.fabTimeline.setOnClickListener {
            TimeLineDialog().show(fragmentManager, "")
        }
    }

    // implementing the adapter in the recyclerView
    private fun setUpRecyclerView(){
        val layoutManager = LinearLayoutManager(context)
        val query: Query = store.collection("timelineMessage")
            .orderBy("sentAt", Query.Direction.DESCENDING)


        val options = FirestoreRecyclerOptions.Builder<TimelineMessage>()
            .setQuery(query, TimelineMessage::class.java)
            .build()
        adapter = TimelineAdapter(options, currentUser.uid)


        _view.recyclerTimeline.setHasFixedSize(true)
        _view.recyclerTimeline.layoutManager = layoutManager
        _view.recyclerTimeline.itemAnimator = DefaultItemAnimator()
        _view.recyclerTimeline.adapter = adapter




    }

    private fun subscribeToTimelineMessage(){
        tlmessageSubscription = timelineDBRef
            .addSnapshotListener(object: java.util.EventListener, EventListener<QuerySnapshot>{
                override fun onEvent(snapshot: QuerySnapshot?, exception: FirebaseFirestoreException?){
                    exception?.let {
                        toast("Exeception!")
                        return
                    }
                    snapshot?.let {
                        timelineList.clear()
                        val message = it.toObjects(TimelineMessage::class.java)
                        timelineList.addAll(message)
                        adapter.notifyDataSetChanged()
                    }
                }

            })


    }

    // Using RxAndroid to make the minimum calls to the database
    private fun subscribeToNewMessageTimeLine(){
        tlmessageBusListener = RxBus.listen(NewTimeLineEvent::class.java).subscribe {
            saveTimelistMessage(it.message)
        }
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onDestroyView() {
        tlmessageBusListener.dispose()
        tlmessageSubscription?.remove()
        super.onDestroyView()
    }
}
