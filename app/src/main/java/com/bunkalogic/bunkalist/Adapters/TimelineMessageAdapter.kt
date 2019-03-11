package com.bunkalogic.bunkalist.Adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bunkalogic.bunkalist.Others.inflateM
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.db.TimelineMessage
import kotlinx.android.synthetic.main.fragment_timeline_item.view.*
import java.text.SimpleDateFormat

class TimelineMessageAdapter(private val items: List<TimelineMessage>): RecyclerView.Adapter<TimelineMessageAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflateM(R.layout.fragment_timeline_item))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])




    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
      fun bind(tlmessage: TimelineMessage){
          itemView.textViewUsername.text = tlmessage.username
          itemView.textViewDate.text = SimpleDateFormat("h ,EEE, MMM, yy").format(tlmessage.sentAt)
          itemView.textViewOeuvreName.text = tlmessage.oeuvreName
          itemView.textViewSeason.text = tlmessage.numSeason.toString()
          itemView.textViewCapsNumbers.text = tlmessage.numChapter.toString()
          itemView.textViewContent.text = tlmessage.content
          //itemView.textViewNumberPositive.text = tlmessage.numPositive.toString()
          //itemView.textViewNumberNegative.text = tlmessage.numNegative.toString()
          if (tlmessage.profileImageUrl.isEmpty()){
              Glide.with(itemView)
                  .load(R.drawable.ic_person_black_24dp)
                  .apply(RequestOptions.circleCropTransform()
                      .override(40, 40))
                  .into(itemView.imageViewUserImage)
          }else{
              Glide.with(itemView)
                  .load(tlmessage.profileImageUrl)
                  .apply(RequestOptions.circleCropTransform()
                      .override(40, 40))
                  .into(itemView.imageViewUserImage)
          }
      }
    }
}