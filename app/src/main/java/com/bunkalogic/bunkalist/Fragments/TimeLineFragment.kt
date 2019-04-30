package com.bunkalogic.bunkalist.Fragments


import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bunkalogic.bunkalist.Adapters.TimelineMessageAdapter
import com.bunkalogic.bunkalist.Dialog.TimeLineDialog
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.RxBus.RxBus
import com.bunkalogic.bunkalist.db.NewTimeLineEventGlobal
import com.bunkalogic.bunkalist.db.TimelineMessage
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

    private var typeList = 0

    private val timelineList: ArrayList<TimelineMessage> = ArrayList()
    private lateinit var adapter: TimelineMessageAdapter

    private val timelineListGlobal: ArrayList<TimelineMessage> = ArrayList()
    private lateinit var adapterGlobal: TimelineMessageAdapter

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var currentUser: FirebaseUser

    private val store: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var timelineDBRef: CollectionReference
    private lateinit var timelineGlobalDBRef: CollectionReference

    private var tlmessageSubscription: ListenerRegistration? = null
    private lateinit var tlmessageBusListener: Disposable

    private var tlmessageGlobalSubscription: ListenerRegistration? = null
    private lateinit var tlmessageGlobalBusListener: Disposable


   //override fun onCreate(savedInstanceState: Bundle?) {
   //    super.onCreate(savedInstanceState)
   //    arguments?.let {
   //        typeList = it.getInt(Constans.personal)
   //    }

   //}


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _view = inflater.inflate(R.layout.fragment_timeline, container, false)
        setUpTimeLineGlobalDB()
        setUpCurrentUser()
        //setUpTimeLineDB()
        //setUpTabLayout()
        setUpFab()
        //setUpRecyclerView()
        setUpRecyclerViewGlobal()
        setUpSwipeRefreshLayout()

        subscribeToTimelineMessageGlobal()
        subscribeToNewMessageTimeLineGlobal()

        //isGlobalOrPersonal()
        //subscribeToNewMessageTimeLine()

        return _view
    }

    // root : Users/uid/username
    //private fun setUpTimeLineDB(){
    //    timelineDBRef = store.collection("Data/Users/${preferences.userId}/ ${preferences.userName} /timelineMessage")
    //}
//
    // Creating the new instance in the database
    //private fun saveTimelistMessage(messagetl: TimelineMessage){
    //    timelineDBRef.add(messagetl)
    //        .addOnCompleteListener {
    //            toast("Message Added!")
    //        }
    //        .addOnFailureListener { toast("Error try Again") }
    //}

    private fun setUpSwipeRefreshLayout(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            _view.swipeRefreshTimeline.setColorSchemeColors(resources.getColor(R.color.colorPrimaryDark, resources.newTheme()))
        }
    }

    // root : Users/uid/username
    private fun setUpTimeLineGlobalDB(){
        timelineGlobalDBRef = store.collection("Data/Content/timelineMessageGlobal")
    }

    // Creating the new instance in the database
    private fun saveTimelistMessageGlobal(messagetl: TimelineMessage){
        timelineGlobalDBRef.add(messagetl)
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



    //private fun setUpTabLayout(){
//
//
    //    val tabLayoutTimeline = _view.tabLayout
    //    _view.timelineViewPager.offscreenPageLimit = 2
//
    //    tabLayoutTimeline?.addTab(tabLayoutTimeline.newTab().setText(R.string.timeline_tab_layout))
    //    tabLayoutTimeline?.addTab(tabLayoutTimeline.newTab().setText(R.string.timeline_tab_layout_personal))
    //    tabLayoutTimeline?.tabGravity = TabLayout.GRAVITY_FILL
//
    //    val adapter = TimeLineTabAdapter(fragmentManager!!, tabLayoutTimeline.tabCount)
//
    //    _view.timelineViewPager.adapter = adapter
//
    //    if ( tabLayoutTimeline ==  null){
//
    //        _view.timelineViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayoutTimeline))
//
    //        tabLayoutTimeline?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
    //            override fun onTabReselected(p0: TabLayout.Tab?) {
//
    //            }
//
    //            override fun onTabUnselected(p0: TabLayout.Tab?) {
//
    //            }
//
    //            override fun onTabSelected(tab: TabLayout.Tab?) {
    //                var position = tab!!.position
    //                _view.timelineViewPager.currentItem = position
    //                Log.d("TimelineMessage", "$position")
    //            }
//
    //        })
//
    //    }
//
//
//
    //}

    //private fun isGlobalOrPersonal(){
//
    //    when (typeList) {
    //        Constans.TIMELINE_GLOBAL -> {
    //            //setUpRecyclerViewGlobal()
    //            subscribeToTimelineMessageGlobal()
//
    //            //subscribeToNewMessageTimeLineGlobal()
//
    //            Log.d("TimelineMessage", "Is global")
//
    //        }
    //        Constans.TIMELINE_PERSONAL -> {
    //            //setUpRecyclerView()
    //            _view.tabLayout.visibility = View.GONE
    //            subscribeToTimelineMessagePersonal()
    //            //subscribeToNewMessageTimeLine()
//
    //            Log.d("TimelineMessage", "Is personal")
    //        }
    //    }
    //}



    // implementing the adapter in the recyclerView
    //private fun setUpRecyclerView(){
    //    val layoutManager = LinearLayoutManager(context)
//
    //    adapter = TimelineMessageAdapter(context!!,timelineList)
//
//
    //    _view.recyclerTimeline.setHasFixedSize(true)
    //    _view.recyclerTimeline.layoutManager = layoutManager as RecyclerView.LayoutManager?
    //    _view.recyclerTimeline.itemAnimator = DefaultItemAnimator()
    //    _view.recyclerTimeline.adapter = adapter
//
    //}

    // implementing the adapter in the recyclerView
    private fun setUpRecyclerViewGlobal(){
        val layoutManager = LinearLayoutManager(context)

        adapterGlobal = TimelineMessageAdapter(context!!,timelineListGlobal)


        _view.recyclerTimeline.setHasFixedSize(true)
        _view.recyclerTimeline.layoutManager = layoutManager
        _view.recyclerTimeline.itemAnimator = DefaultItemAnimator()
        _view.recyclerTimeline.adapter = adapterGlobal

    }

    //private fun subscribeToTimelineMessagePersonal(){
    //    tlmessageSubscription = timelineDBRef
    //        .orderBy("sentAt", Query.Direction.DESCENDING)
    //        .addSnapshotListener(object: java.util.EventListener, EventListener<QuerySnapshot>{
    //            override fun onEvent(snapshot: QuerySnapshot?, exception: FirebaseFirestoreException?){
    //                exception?.let {
    //                    Log.d("TimelineMessage", "Exception")
    //                    return
    //                }
    //                snapshot?.let {
    //                    timelineList.clear()
    //                    val message = it.toObjects(TimelineMessage::class.java)
    //                    timelineList.addAll(message)
    //                    adapter.notifyDataSetChanged()
    //                }
    //            }
//
    //        })
//
//
    //}

    private fun subscribeToTimelineMessageGlobal(){
        tlmessageGlobalSubscription = timelineGlobalDBRef
            .orderBy("sentAt", Query.Direction.DESCENDING)
            .addSnapshotListener(object: java.util.EventListener, EventListener<QuerySnapshot>{
                override fun onEvent(snapshot: QuerySnapshot?, exception: FirebaseFirestoreException?){
                    exception?.let {
                        Log.d("TimelineMessage", "Exception Global")
                        return
                    }
                    snapshot?.let {
                        timelineListGlobal.clear()
                        val message = it.toObjects(TimelineMessage::class.java)
                        timelineListGlobal.addAll(message)

                        _view.swipeRefreshTimeline.setOnRefreshListener {
                            _view.swipeRefreshTimeline.isRefreshing = true
                            adapterGlobal.notifyDataSetChanged()
                            _view.swipeRefreshTimeline.isRefreshing = false
                        }
                        adapterGlobal.notifyDataSetChanged()

                        //_view.swipeRefreshTimeline.isRefreshing = false

                    }
                }

            })


    }

    // Using RxAndroid to make the minimum calls to the database
    //private fun subscribeToNewMessageTimeLine(){
    //    tlmessageBusListener = RxBus.listen(NewTimeLineEvent::class.java).subscribe {
    //        saveTimelistMessage(it.message)
    //    }
    //}

    // Using RxAndroid to make the minimum calls to the database
    private fun subscribeToNewMessageTimeLineGlobal(){
        tlmessageGlobalBusListener = RxBus.listen(NewTimeLineEventGlobal::class.java).subscribe {
            saveTimelistMessageGlobal(it.message)
        }
    }


    //companion object {
    //    @JvmStatic
    //    fun newInstance(typeList: Int) =
    //        TimeLineFragment().apply {
    //            arguments = Bundle().apply {
    //                putInt(Constans.personal, typeList)
    //            }
    //        }
    //}

    override fun onDestroyView() {
        tlmessageGlobalBusListener.dispose()
        tlmessageGlobalSubscription?.remove()
        super.onDestroyView()
    }
}
