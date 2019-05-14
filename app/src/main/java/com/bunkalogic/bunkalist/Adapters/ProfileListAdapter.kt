package com.bunkalogic.bunkalist.Adapters

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bunkalogic.bunkalist.Activities.DetailsActivities.ItemDetailsActivity
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.Retrofit.Callback.OnGetMovieCallback
import com.bunkalogic.bunkalist.Retrofit.Callback.OnGetSeriesCallback
import com.bunkalogic.bunkalist.Retrofit.Response.Movies.Movie
import com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime.Series
import com.bunkalogic.bunkalist.SharedPreferences.preferences
import com.bunkalogic.bunkalist.ViewModel.ViewModelSearch
import com.bunkalogic.bunkalist.db.ItemListRating
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import org.jetbrains.anko.intentFor
import java.text.SimpleDateFormat



class ProfileListAdapter(private val ctx: Context, private var mValues: MutableList<ItemListRating>): RecyclerView.Adapter<ProfileListAdapter.ViewHolder>(){

    var searchViewModelSearch: ViewModelSearch? = null


    init {
        searchViewModelSearch = ViewModelProviders.of(ctx as FragmentActivity).get(ViewModelSearch::class.java)


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileListAdapter.ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.list_profile_fragement_item, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ProfileListAdapter.ViewHolder, position: Int) {
        val listRating = mValues[position]

            val numPosition = position + 1

            holder.numPosition.text = "$numPosition."
            holder.dateAt.text = SimpleDateFormat("dd.MM.yyyy").format(listRating.addDate)
            holder.yourRating.text = listRating.finalRate.toString()

            val type = listRating.typeOeuvre
            val idItem = listRating.oeuvreId
            val mediaTypeTV = "tv"
            val mediaTypeMovie = "movie"

            // check which value has type to load movie or series or anime
            if (type == Constans.MOVIE_LIST){
                searchViewModelSearch!!.getMovie(idItem!!,object :
                    OnGetMovieCallback {
                    override fun onSuccess(movie: Movie) {
                        holder.title.text = movie.title
                        holder.dateRelease.text = movie.releaseDate?.split("-")?.get(0) ?: movie.releaseDate
                        //holder.description.text = movie.overview
                        holder.globalRating.text = movie.voteAverage.toString()

                        Glide.with(ctx)
                            .load(Constans.API_MOVIE_SERIES_ANIME_BASE_URL_IMG_PATH_POSTER + movie.posterPath)
                            .into(holder.imagePoster)

                        val name = movie.title


                        // is responsible for collecting the data to load in the ItemDetailsActivity
                        holder.itemView.setOnClickListener {
                            ctx.startActivity(ctx.intentFor<ItemDetailsActivity>(
                                "id" to idItem,
                                "type" to mediaTypeMovie,
                                "name" to name
                            ))
                        }


                    }

                    override fun onError() {
                        Log.d("ProfileListAdapter", "Error Movie try Again")
                    }

                })
            }else if (type == Constans.SERIE_LIST){
                searchViewModelSearch!!.getSeriesAndAnime(idItem!!, object :
                    OnGetSeriesCallback {
                    override fun onSuccess(series: Series) {
                        holder.title.text = series.name
                        holder.dateRelease.text = series.firstAirDate?.split("-")?.get(0) ?: series.firstAirDate
                        //holder.description.text = series.overview
                        holder.globalRating.text = series.voteAverage.toString()

                        Glide.with(ctx)
                            .load(Constans.API_MOVIE_SERIES_ANIME_BASE_URL_IMG_PATH_POSTER + series.posterPath)
                            .into(holder.imagePoster)

                        val name = series.name


                        // is responsible for collecting the data to load in the ItemDetailsActivity
                        holder.itemView.setOnClickListener {
                            ctx.startActivity(ctx.intentFor<ItemDetailsActivity>(
                                "id" to idItem,
                                "type" to mediaTypeTV,
                                "name" to name
                            ))
                        }
                    }

                    override fun onError() {
                        Log.d("ProfileListAdapter", "Error Movie try Again")
                    }

                })
            }else if (type == Constans.ANIME_LIST){
                searchViewModelSearch!!.getSeriesAndAnime(idItem!!, object :
                    OnGetSeriesCallback {
                    override fun onSuccess(series: Series) {
                        holder.title.text = series.name
                        holder.dateRelease.text = series.firstAirDate?.split("-")?.get(0) ?: series.firstAirDate
                        //holder.description.text = series.overview
                        holder.globalRating.text = series.voteAverage.toString()

                        Glide.with(ctx)
                            .load(Constans.API_MOVIE_SERIES_ANIME_BASE_URL_IMG_PATH_POSTER + series.posterPath)
                            .into(holder.imagePoster)


                        val name = series.name

                        // is responsible for collecting the data to load in the ItemDetailsActivity
                        holder.itemView.setOnClickListener {
                            ctx.startActivity(ctx.intentFor<ItemDetailsActivity>(
                                "id" to idItem,
                                "type" to mediaTypeTV,
                                "name" to name
                            ))
                        }


                    }

                    override fun onError() {
                        Log.d("ProfileListAdapter", "Error Movie try Again")
                    }

                })
            }

            holder.itemView.setOnLongClickListener {
               val ref = FirebaseFirestore.getInstance().collection("Users/${preferences.userId}/RatingList")

               //val refId = ref.document()
               //Log.d("ProfileListAdapter", refId)

                  ref
                  .document("id")
                      .delete()
                  .addOnCompleteListener(object : OnCompleteListener<Void>{
                      override fun onComplete(p0: Task<Void>) {
                          if (p0.isSuccessful){
                              Log.d("ProfileListAdapter", "Deleted item")
                              mValues.removeAt(position)
                              notifyDataSetChanged()
                          }else{
                              Log.d("ProfileListAdapter", "Failed deleted item")
                          }
                      }

                  })








                true
            }


    }

    fun removeItemList(item: ItemListRating){
       mValues.remove(item)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mValues.size
    }


    inner class ViewHolder internal constructor(view: View): RecyclerView.ViewHolder(view){
        internal var title: TextView
        internal var dateRelease: TextView
        internal var globalRating: TextView
        internal var numPosition: TextView
        internal var dateAt: TextView
        internal var yourRating: TextView
        internal var imagePoster: ImageView


        init {
            title = view.findViewById(R.id.textViewTitleListProfile)
            dateRelease = view.findViewById(R.id.textViewDateReleastListProfile)
            globalRating = view.findViewById(R.id.textViewRatingListProfile)
            numPosition = view.findViewById(R.id.textViewListProfileNumberPosition)
            dateAt = view.findViewById(R.id.textViewListProfileDateAt)
            yourRating = view.findViewById(R.id.textViewListProfileYourRating)
            imagePoster = view.findViewById(R.id.imageViewPosterListProfile)
        }
    }

}