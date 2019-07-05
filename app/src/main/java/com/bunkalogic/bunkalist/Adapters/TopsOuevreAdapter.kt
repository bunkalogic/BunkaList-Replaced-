package com.bunkalogic.bunkalist.Adapters

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bunkalogic.bunkalist.Activities.DetailsActivities.ItemDetailsActivity
import com.bunkalogic.bunkalist.Activities.DetailsActivities.ListTopsActivity
import com.bunkalogic.bunkalist.Models.TopsCardItem
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.Retrofit.Response.Movies.ResultMovie
import org.jetbrains.anko.intentFor

class TopsOuevreAdapter(private val ctx: Context,val mValues: List<TopsCardItem>): androidx.recyclerview.widget.RecyclerView.Adapter<TopsOuevreAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_item_tops, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mItem = mValues[position]

        val id = mItem.id
        val title = mItem.title

        //TODO: Agregar la logica para vaya a tipo de top u otro

        holder.itemView.setOnClickListener {
            ctx.startActivity(ctx.intentFor<ListTopsActivity>(
                "idTops" to id,
                "title" to title
            ))
        }

        holder.bind(mItem)


    }

    inner class ViewHolder(mView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(mView){
        private val textViewTitle: TextView = mView.findViewById(R.id.textViewTitleTops)
        private val imageViewPoster: ImageView = mView.findViewById(R.id.imageViewPosterTops)

        fun bind(mItem: TopsCardItem){

            textViewTitle.text = mItem.title


            Glide.with(ctx)
                .load(mItem.image)
                .centerCrop()
                .into(imageViewPoster)

        }

    }
}