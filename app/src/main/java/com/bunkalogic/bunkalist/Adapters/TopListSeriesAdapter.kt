package com.bunkalogic.bunkalist.Adapters

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bunkalogic.bunkalist.Activities.DetailsActivities.ItemDetailsActivity
import com.bunkalogic.bunkalist.Dialog.AddListDialog
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime.ResultSeries
import com.bunkalogic.bunkalist.SharedPreferences.preferences
import org.jetbrains.anko.intentFor



class TopListSeriesAdapter(private val ctx: Context, private var mValues: ArrayList<ResultSeries>): RecyclerView.Adapter<TopListSeriesAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.top_list_item_series, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  mValues.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mItem = mValues[position]

        val id = mItem.id
        val type = "tv"
        val title = mItem.name

        holder.itemView.setOnClickListener {
            ctx.startActivity(ctx.intentFor<ItemDetailsActivity>(
                "id" to id,
                "type" to type,
                "title" to title
            ))
        }

        val numPosition = position + 1
        holder.textViewTopPosition.text = "$numPosition."
        holder.bind(mItem)
    }

    fun appendSeries(moviesToAppend: List<ResultSeries>) {
        mValues.addAll(moviesToAppend)
        notifyDataSetChanged()
    }

    fun clearList(){
        mValues.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder (mView: View): RecyclerView.ViewHolder(mView){
        private val imageViewPoster: ImageView = mView.findViewById(R.id.imageViewPosterTopSeries)
        private val textViewTitle: TextView = mView.findViewById(R.id.textViewTitleTopSeries)
        private val textViewDateReleast : TextView = mView.findViewById(R.id.textViewDateReleastTopSeries)
        private val textViewDescription: TextView = mView.findViewById(R.id.textViewDescriptionTopSeries)
        private val imageViewRating: ImageView = mView.findViewById(R.id.imageViewAddToMyListTopSeries)
        private val textViewRating: TextView = mView.findViewById(R.id.textViewRatingTopSeries)
        internal var textViewTopPosition: TextView = mView.findViewById(R.id.textViewTopListSeries)

        fun bind(mItem: ResultSeries){

            textViewTitle.text = mItem.name

            textViewDescription.text = mItem.overview

            textViewDateReleast.text = mItem.firstAirDate?.split("-")?.get(0) ?: mItem.firstAirDate

            textViewRating.text = mItem.voteAverage.toString()

            val photo = mItem.posterPath

            Glide.with(ctx)
                .load(Constans.API_MOVIE_SERIES_ANIME_BASE_URL_IMG_PATH_POSTER + photo)
                .into(imageViewPoster)

            // get list genres
            val currentGenres: List<Int>? = mItem.genreIds

            Log.d("SearchItemAdapter", "List: $currentGenres")

            val typeAnime = currentGenres?.filter { it == 16 }?.any()


            // is responsible for collecting the id and type to load after depending on whether it is a movie or series
            val id = mItem.id
            val type = "tv"
            val name =  mItem.name.toString()

            val bundle = Bundle()

            bundle.putInt("id", id!!)
            bundle.putString("name", name)
            bundle.putString("type", type)
            bundle.putBoolean("anime", typeAnime!!)



            imageViewRating.setOnClickListener {

                val dialog = AddListDialog()
                dialog.arguments = bundle
                val manager = (ctx as AppCompatActivity).supportFragmentManager.beginTransaction()
                dialog.show(manager, null)

            }

        }
    }
}