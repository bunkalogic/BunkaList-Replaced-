package com.bunkalogic.bunkalist.Adapters

import android.content.Context
import android.support.v7.widget.DialogTitle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.Retrofit.Response.ResponseSearchAll
import com.bunkalogic.bunkalist.Retrofit.Response.ResultSearchAll

class SearchItemAdapter(private val ctx: Context, private var mValues: List<ResponseSearchAll>?): RecyclerView.Adapter<SearchItemAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_search_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (mValues != null)
            return mValues!!.size
            else return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            mValues!![position]

            holder.textViewTitle.text = holder.mItem.name
            holder.textViewDateReleast.text = holder.mItem.firstAirDate
            holder.textViewDescription.text = holder.mItem.overview

            val photo = holder.mItem.posterPath
            if (photo != ""){
                Glide.with(ctx)
                    .load(photo)
                    .override(95, 140)
                    .into(holder.imageViewPoster)
            }



    }

   //fun setData(title: List<ResultSearchAll>){
   //    this.mValues = title
   //    notifyDataSetChanged()
   //}


    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val imageViewPoster: ImageView
        val textViewTitle: TextView
        val textViewDateReleast :TextView
        val textViewDescription: TextView
        // TODO probar a cambiar el mValues por un ResultSearchAll
        lateinit var mItem : ResultSearchAll
        //TODO: this value will be collected the rating of my database
        //val textViewRating: TextView

        init {
            imageViewPoster = mView.findViewById(R.id.imageViewPoster)
            textViewTitle = mView.findViewById(R.id.textViewTitle)
            textViewDateReleast = mView.findViewById(R.id.textViewDateReleast)
            textViewDescription = mView.findViewById(R.id.textViewDescription)
        }
    }


}