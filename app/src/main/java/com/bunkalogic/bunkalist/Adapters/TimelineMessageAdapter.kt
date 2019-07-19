package com.bunkalogic.bunkalist.Adapters

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bunkalogic.bunkalist.Activities.DetailsActivities.ItemTimelineChatActivity
import com.bunkalogic.bunkalist.Activities.UserProfileActivities.OtherUserProfile
import com.bunkalogic.bunkalist.Fragments.SearchFragment
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.Others.SolutionCounters
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.RxBus.RxBus
import com.bunkalogic.bunkalist.db.ItemListRating
import com.bunkalogic.bunkalist.db.TimelineChatEvent
import com.bunkalogic.bunkalist.db.TimelineMessage
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.colorAttr
import org.jetbrains.anko.intentFor
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.schedule

class TimelineMessageAdapter(val ctx: Context, query: Query){

    lateinit var countBusListener: Disposable


    val options : FirestoreRecyclerOptions<TimelineMessage> = FirestoreRecyclerOptions.Builder<TimelineMessage>()
        .setQuery(query, TimelineMessage::class.java)
        .build()



    val adapter = object
        : FirestoreRecyclerAdapter<TimelineMessage, ViewHolder>(options){


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_timeline_item, parent, false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int, tlmessage: TimelineMessage) {
            holder.username.text = tlmessage.username
            holder.oeuvreName.text = tlmessage.oeuvreName
            holder.sentAt.text = SimpleDateFormat("h:mm EEE / MMM", Locale.getDefault()).format(tlmessage.sentAt)
            holder.numSeason.text = tlmessage.numSeason
            holder.numEpisode.text = tlmessage.numEpisode

            // if isSpoiler is true, delete the message if it is false.
            if (tlmessage.isSpoiler == true){
                holder.content.text = ctx.getString(R.string.timeline_message_is_spoiler)
                holder.content.setTypeface(null , Typeface.BOLD_ITALIC)
            }else{
                holder.content.text = tlmessage.content
            }

            holder.content.setOnLongClickListener {
                holder.content.text = tlmessage.content
                true
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
                    .override(60, 60)
                    .into(holder.userImage)
            }else{
                Glide.with(holder.itemView)
                    .load(tlmessage.profileImageUrl)
                    .override(60, 60)
                    .into(holder.userImage)

            }

            val userId = tlmessage.userId
            val username = tlmessage.username
            val userPhoto = tlmessage.profileImageUrl
            val ouevreTitle = tlmessage.oeuvreName
            val content = tlmessage.content
            val token = tlmessage.tokenId
            val dateAt = SimpleDateFormat("h:mm EEE,MMM", Locale.getDefault()).format(tlmessage.sentAt)
            val numSeason = tlmessage.numSeason
            val numEpisode = tlmessage.numEpisode


            // it is charged to collect the user's data show his profile
            holder.userImage.setOnClickListener {
                ctx.startActivity(ctx.intentFor<OtherUserProfile>(
                    "userId" to userId,
                    "username" to username,
                    "userPhoto" to userPhoto
                ))
            }

            // it is charged to collect the user's data show his chat
            holder.imageViewChat.setOnClickListener {
                ctx.startActivity(ctx.intentFor<ItemTimelineChatActivity>(
                    "userId" to userId,
                    "username" to username,
                    "userPhoto" to userPhoto,
                    "content" to content,
                    "token" to token,
                    "dateAt" to dateAt,
                    "numSeason" to numSeason,
                    "numEpisode" to numEpisode,
                    "title" to ouevreTitle
                ))
                // is responsible for collecting the title of the work and pass it in SearchFragment
                holder.oeuvreName.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString(Constans.SEARCH_NAME, ouevreTitle)
                    val search = SearchFragment()
                    search.arguments = bundle
                    search.fragmentManager?.beginTransaction()?.commit()



                }


                //It is responsible for showing the number of comments that this TimelineMessage has
                countBusListener = RxBus.listen(TimelineChatEvent::class.java).subscribe {
                    if (holder.numPositive.text.isEmpty()){
                        holder.numPositive.text = it.size.toString()
                    }
                }

            }

            // Is responsible for the favs of each comment
            val favCount = tlmessage.numPositive

            val refDoc = FirebaseFirestore.getInstance().collection("Data/Content/timelineMessageGlobal")
            val snapshot: DocumentSnapshot = snapshots.getSnapshot(holder.adapterPosition)
            val idItemTimelineMessage = snapshot.id


            if(holder.imageStarFav.tag == "full"){
                holder.imageStarFav.backgroundResource = R.drawable.ic_star_complete_24dp
            }else if (holder.imageStarFav.tag == "empty"){
                holder.imageStarFav.backgroundResource = R.drawable.ic_star_filled_24dp
            }



            holder.imageStarFav.setOnClickListener {

                if (holder.imageStarFav.tag == "full"){

                    Timer("decrease", false).schedule(500){
                        refDoc
                            .document(idItemTimelineMessage)
                            .update("numPositive", FieldValue.increment(-1))
                            .addOnCompleteListener {
                                if (it.isSuccessful){
                                    Log.d("TimelineMessageAdapter", "fav is increment: -1")
                                }else{
                                    Log.d("TimelineMessageAdapter", "fav error")
                                }
                            }
                    }

                    holder.imageStarFav.tag = "empty"
                    holder.imageStarFav.backgroundResource = R.drawable.ic_star_filled_24dp
                    notifyItemChanged(position)

                }else if(holder.imageStarFav.tag == "empty"){


                    Timer("decrease", false).schedule(500){
                        refDoc
                            .document(idItemTimelineMessage)
                            .update("numPositive", FieldValue.increment(+1))
                            .addOnCompleteListener {
                                if (it.isSuccessful){
                                    Log.d("TimelineMessageAdapter", "fav is increment: +1")
                                }else{
                                    Log.d("TimelineMessageAdapter", "fav error")
                                }
                            }
                    }

                    holder.imageStarFav.tag = "full"
                    holder.imageStarFav.backgroundResource = R.drawable.ic_star_complete_24dp
                    notifyItemChanged(position)
                }
            }

            holder.countFavs.text = favCount.toString()
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
        internal var imageViewChat: ImageView
        internal var imageStarFav: ImageView
        internal var countFavs: TextView


        init {
            username = view.findViewById(R.id.textViewUsername)
            userImage = view.findViewById(R.id.imageViewUserImage)
            sentAt = view.findViewById(R.id.textViewDate)
            oeuvreName = view.findViewById(R.id.textViewOeuvreName)
            numSeason = view.findViewById(R.id.textViewSeason)
            numEpisode = view.findViewById(R.id.textViewCapsNumbers)
            content = view.findViewById(R.id.textViewContent)
            numPositive = view.findViewById(R.id.textViewNumberPositive)
            imageViewChat = view.findViewById(R.id.imageViewTimelineChat)
            imageStarFav = view.findViewById(R.id.imageViewTimelineFav)
            countFavs = view.findViewById(R.id.textViewNumberFav)
        }

    }


}