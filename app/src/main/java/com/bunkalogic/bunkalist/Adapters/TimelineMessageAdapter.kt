package com.bunkalogic.bunkalist.Adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bunkalogic.bunkalist.Others.inflateM
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.SharedPreferences.preferences
import com.bunkalogic.bunkalist.db.TimelineMessage
import kotlinx.android.synthetic.main.fragment_timeline_item.view.*
import java.text.SimpleDateFormat

class TimelineMessageAdapter(private val items: List<TimelineMessage>): RecyclerView.Adapter<TimelineMessageAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflateM(R.layout.fragment_timeline_item))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindTimeLine(items[position])

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindTimeLine(tlmessage: TimelineMessage){
            with(tlmessage){
                val season = numSeason
                val episode = numChapter

                itemView.textViewUsername.text = username
                itemView.textViewOeuvreName.text = oeuvreName
                itemView.textViewSeason.text = "Season: $season"
                itemView.textViewCapsNumbers.text = "Episode: $episode"
                itemView.textViewDate.text = SimpleDateFormat("h:mm -EEE/MMM/yy").format(sentAt)
                itemView.textViewContent.text = content

                if (profileImageUrl.isEmpty()){
                    Glide.with(itemView)
                        .load(R.drawable.ic_person_black_24dp)
                        .apply(
                            RequestOptions.circleCropTransform()
                                .override(40, 40))
                        .into(itemView.imageViewUserImage)
                }else{
                    Glide.with(itemView)
                        .load(profileImageUrl)
                        .apply(
                            RequestOptions.circleCropTransform()
                                .override(40, 40))
                        .into(itemView.imageViewUserImage)
                }
            }
        }

    }
}