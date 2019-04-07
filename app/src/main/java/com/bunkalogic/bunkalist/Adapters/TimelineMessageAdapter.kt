package com.bunkalogic.bunkalist.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.db.TimelineMessage
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat

class TimelineMessageAdapter(val ctx: Context, private val TimelineMessageList: MutableList<TimelineMessage>) : RecyclerView.Adapter<TimelineMessageAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_timeline_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return TimelineMessageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val tlmessage = TimelineMessageList[position]

        holder.username.text = tlmessage.username
        holder.oeuvreName.text = tlmessage.oeuvreName
        holder.sentAt.text = SimpleDateFormat("h:mm EEE,MMM").format(tlmessage.sentAt)
        holder.numSeason.text = tlmessage.numSeason
        holder.numEpisode.text = tlmessage.numEpisode

        // if isSpoiler is true, delete the message if it is false.
        if (tlmessage.isSpoiler == true){
            holder.content.text = ctx.getString(R.string.timeline_message_is_spoiler)
        }else{
            holder.content.text = tlmessage.content
        }

        holder.content.setOnClickListener {
            holder.content.text = tlmessage.content
        }



        // Here I check if the numSeason is empty then it does not show anything
        if (holder.numSeason.text.isNotEmpty()){

            val season = tlmessage.numSeason
            holder.numSeason.text = "Season: $season"

        }else{
            holder.numSeason.text = null
        }
        // Here I check if the numEpisode is empty then it does not show anything
        if (holder.numEpisode.text.isNotEmpty()){

            val episode = tlmessage.numEpisode
            holder.numEpisode.text = "Episode: $episode"

        }else{
            holder.numEpisode.text = null
        }

        if (tlmessage.profileImageUrl.isEmpty()){
            Glide.with(holder.itemView)
                .load(R.drawable.ic_person_black_24dp)
                .apply(
                    RequestOptions.circleCropTransform()
                        .override(40, 40))
                .into(holder.userImage)
        }else{
            Glide.with(holder.itemView)
                .load(tlmessage.profileImageUrl)
                .apply(
                    RequestOptions.circleCropTransform()
                        .override(40, 40))
                .into(holder.userImage)

        }

        
        // TODO: get it saved in the database for every click that is
        // If you click imagePositive that adds +1
        holder.imagePositive.setOnClickListener {
            holder.numPositive.text = "+" + 1
            if (FirebaseAuth.getInstance().currentUser!!.uid == FirebaseAuth.getInstance().uid){
                val store = FirebaseFirestore.getInstance()
                var numPositiveRef = store.collection("timelineMessage").document("userId")

                numPositiveRef.update("numPositive", +1).addOnSuccessListener {
                    Log.d("TimelineMessageAdapter", "successfully updated!")

                }.addOnFailureListener {
                    Log.d("TimelineMessageAdapter", "Error updating document")
                }
            }else{
                Log.d("TimelineMessageAdapter", "Error")
            }
        }
        // If you click imageNegative that adds -1
        holder.imageNegative.setOnClickListener {
            holder.numNegative.text = "-" + 1
            if (FirebaseAuth.getInstance().currentUser!!.uid == FirebaseAuth.getInstance().uid){
                val store = FirebaseFirestore.getInstance()
                var numNegativeRef = store.collection("timelineMessage").document("userId")

                numNegativeRef.update("numNegative", "-1").addOnSuccessListener {
                    Log.d("TimelineMessageAdapter", "successfully updated negative!")

                }.addOnFailureListener {
                    Log.d("TimelineMessageAdapter", "Error updating document")
                }
            }else{
                Log.d("TimelineMessageAdapter", "Error")
            }
        }



    }


    inner class ViewHolder internal constructor(view: View): RecyclerView.ViewHolder(view){
        internal var username: TextView
        internal var userImage: ImageView
        internal var sentAt: TextView
        internal var oeuvreName: TextView
        internal var numSeason: TextView
        internal var numEpisode: TextView
        internal var content: TextView
        internal var numPositive: TextView
        internal var numNegative: TextView
        internal var imagePositive: ImageView
        internal var imageNegative: ImageView


        init {
            username = view.findViewById(R.id.textViewUsername)
            userImage = view.findViewById(R.id.imageViewUserImage)
            sentAt = view.findViewById(R.id.textViewDate)
            oeuvreName = view.findViewById(R.id.textViewOeuvreName)
            numSeason = view.findViewById(R.id.textViewSeason)
            numEpisode = view.findViewById(R.id.textViewCapsNumbers)
            content = view.findViewById(R.id.textViewContent)
            numPositive = view.findViewById(R.id.textViewNumberPositive)
            numNegative = view.findViewById(R.id.textViewNumberNegative)
            imagePositive = view.findViewById(R.id.imageViewPositive)
            imageNegative = view.findViewById(R.id.imageViewNegative)
        }

    }
}