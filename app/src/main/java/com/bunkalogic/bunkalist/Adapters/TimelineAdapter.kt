package com.bunkalogic.bunkalist.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.db.TimelineMessage
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.android.synthetic.main.fragment_timeline_item.view.*
import java.text.SimpleDateFormat


class TimelineAdapter(options: FirestoreRecyclerOptions<TimelineMessage>, val userId: String) : FirestoreRecyclerAdapter<TimelineMessage, TimelineAdapter.TimelineHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.fragment_timeline_item, parent, false)
        return TimelineHolder(v)
    }

    override fun onBindViewHolder(holder: TimelineHolder, position: Int, model: TimelineMessage) = holder.bindTimeLine(model)




    class TimelineHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindTimeLine(tlmessage: TimelineMessage){

           val season = tlmessage.numSeason
           val episode = tlmessage.numChapter
           var username : String?

           itemView.textViewOeuvreName.text = tlmessage.oeuvreName
           itemView.textViewSeason.text = "Season: $season"
           itemView.textViewCapsNumbers.text = "Episode: $episode"
           itemView.textViewDate.text = SimpleDateFormat("h:mm -EEE/MMM/yy").format(tlmessage.sentAt)
           itemView.textViewContent.text = tlmessage.content


            if (tlmessage.userId == FirebaseAuth.getInstance().uid){

                username = FirebaseAuth.getInstance().currentUser!!.displayName
                itemView.textViewUsername.text = username
            }else{
//                TODO: Get the FirebaseFirestore value from timelineMessage / user
                val otherUsername = FirebaseFirestore.getInstance().collection("user").parent.toString()
                itemView.textViewUsername.text = otherUsername
            }



           if (tlmessage.profileImageUrl.isNotEmpty()){
               Glide.with(itemView)
                   .load(tlmessage.profileImageUrl)
                   .apply(
                       RequestOptions.circleCropTransform()
                           .override(40, 40))
                   .into(itemView.imageViewUserImage)
           }else{
               Glide.with(itemView)
                   .load(R.drawable.ic_person_black_24dp)
                   .apply(
                       RequestOptions.circleCropTransform()
                           .override(40, 40))
                   .into(itemView.imageViewUserImage)
           }
       }
   }
}