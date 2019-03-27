package com.bunkalogic.bunkalist.Adapters

import android.content.Context
import android.support.v7.widget.DialogTitle
import android.support.v7.widget.RecyclerView
import android.text.TextUtils.split
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.Retrofit.Response.ResultSearchAll


class SearchItemAdapter(private val ctx: Context, private var mValues: List<ResultSearchAll>?): RecyclerView.Adapter<SearchItemAdapter.ViewHolder>(){


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
            holder.bind(mValues!![position])
    }

   fun setData(title: List<ResultSearchAll>){
       this.mValues = title
       notifyDataSetChanged()
   }


    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val imageViewPoster: ImageView = mView.findViewById(R.id.imageViewPoster)
        val textViewTitle: TextView = mView.findViewById(R.id.textViewTitle)
        val textViewDateReleast :TextView = mView.findViewById(R.id.textViewDateReleast)
        val textViewDescription: TextView = mView.findViewById(R.id.textViewDescription)


        //TODO: this value will be collected the rating of my database
        //val textViewRating: TextView

        fun bind(mItem : ResultSearchAll){

            textViewTitle.text = mItem.title
            if (textViewTitle.text.isEmpty()){
                textViewTitle.text = mItem.name
            }

            textViewDateReleast.text = mItem.releaseDate
            if (textViewDateReleast.text.isEmpty()){
                textViewDateReleast.text = mItem.firstAirDate!!.split("-")[0]
            }else{
                textViewDateReleast.text = mItem.releaseDate!!.split("-")[0]
            }



            textViewDescription.text = mItem.overview

            val photo = mItem.posterPath

                Glide.with(ctx)
                    .load(Constans.API_MOVIE_SERIES_ANIME_BASE_URL_IMG_PATH + photo)
                    .centerCrop()
                    .into(imageViewPoster)
        }


    }
}