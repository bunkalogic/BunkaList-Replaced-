package com.bunkalogic.bunkalist.Adapters

import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.text.InputFilter
import android.text.Spannable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bunkalogic.bunkalist.Activities.DetailsActivities.ItemDetailsActivity
import com.bunkalogic.bunkalist.Fragments.ListProfileFragment
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.Retrofit.Callback.OnGetMovieCallback
import com.bunkalogic.bunkalist.Retrofit.Callback.OnGetSeriesCallback
import com.bunkalogic.bunkalist.Retrofit.Response.Movies.Movie
import com.bunkalogic.bunkalist.Retrofit.Response.SeriesAndAnime.Series
import com.bunkalogic.bunkalist.SharedPreferences.preferences
import com.bunkalogic.bunkalist.ViewModel.ViewModelAPItmdb
import com.bunkalogic.bunkalist.db.ItemListRating
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import org.jetbrains.anko.intentFor
import java.text.SimpleDateFormat
import com.bumptech.glide.util.Util.getSnapshot
import com.bunkalogic.bunkalist.Others.InputFilterRange
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import androidx.paging.PagedList
import com.bunkalogic.bunkalist.Utils.InputFilterMinMax




class ProfileListFirestoreAdapter(var ctx : Context, query : Query){

    var searchViewModelAPItmdb: ViewModelAPItmdb? = null


    init {
        searchViewModelAPItmdb = ViewModelProviders.of(ctx as androidx.fragment.app.FragmentActivity).get(ViewModelAPItmdb::class.java)
    }


    val options: FirestoreRecyclerOptions<ItemListRating> = FirestoreRecyclerOptions.Builder<ItemListRating>()
        .setQuery(query,ItemListRating::class.java)
        .build()


    val adapter  = object : FirestoreRecyclerAdapter<ItemListRating, ViewHolder>(options){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_profile_fragement_item, parent, false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int, listRating: ItemListRating) {

            val numPosition = position + 1

            holder.numPosition.text = "$numPosition."
            holder.dateAt.text = SimpleDateFormat("dd.MM.yyyy").format(listRating.addDate)
            holder.yourRating.text = listRating.finalRate.toString()

            holder.yourRatingFilter.visibility = View.GONE
            holder.imageFilter.visibility = View.GONE
            holder.consContainer.visibility = View.GONE


            // is responsible for checking the list has been filtered and if so load the corresponding notes
            if (preferences.ratingId != 0){
                when(preferences.ratingId){
                    Constans.filter_rating_story -> {
                        holder.imageFinalRate.visibility = View.GONE
                        holder.yourRating.visibility = View.GONE
                        holder.yourRatingFilter.visibility = View.VISIBLE
                        holder.imageFilter.visibility = View.VISIBLE
                        // We add the corresponding data
                        holder.yourRatingFilter.text = listRating.historyRate.toString()
                        Glide.with(ctx)
                            .load(R.drawable.ic_filter_rating_story)
                            .override(60, 60)
                            .into(holder.imageFilter)

                    }
                    Constans.filter_rating_characters -> {
                        holder.imageFinalRate.visibility = View.GONE
                        holder.yourRating.visibility = View.GONE
                        holder.yourRatingFilter.visibility = View.VISIBLE
                        holder.imageFilter.visibility = View.VISIBLE
                        // We add the corresponding data
                        holder.yourRatingFilter.text = listRating.characterRate.toString()
                        Glide.with(ctx)
                            .load(R.drawable.ic_filter_rating_characters)
                            .override(60, 60)
                            .into(holder.imageFilter)


                    }
                    Constans.filter_rating_soundtrack -> {
                        holder.imageFinalRate.visibility = View.GONE
                        holder.yourRating.visibility = View.GONE
                        holder.yourRatingFilter.visibility = View.VISIBLE
                        holder.imageFilter.visibility = View.VISIBLE
                        // We add the corresponding data
                        holder.yourRatingFilter.text = listRating.soundtrackRate.toString()
                        Glide.with(ctx)
                            .load(R.drawable.ic_filter_rating_soundtrack)
                            .override(60, 60)
                            .into(holder.imageFilter)
                    }
                    Constans.filter_rating_photography -> {
                        holder.imageFinalRate.visibility = View.GONE
                        holder.yourRating.visibility = View.GONE
                        holder.yourRatingFilter.visibility = View.VISIBLE
                        holder.imageFilter.visibility = View.VISIBLE
                        // We add the corresponding data
                        holder.yourRatingFilter.text = listRating.effectsRate.toString()
                        Glide.with(ctx)
                            .load(R.drawable.ic_filter_rating_effects)
                            .override(60, 60)
                            .into(holder.imageFilter)

                    }
                    Constans.filter_rating_enjoyment -> {
                        holder.imageFinalRate.visibility = View.GONE
                        holder.yourRating.visibility = View.GONE
                        holder.yourRatingFilter.visibility = View.VISIBLE
                        holder.imageFilter.visibility = View.VISIBLE
                        // We add the corresponding data
                        holder.yourRatingFilter.text = listRating.enjoymentRate.toString()
                        Glide.with(ctx)
                            .load(R.drawable.ic_filter_rating_enjoyment)
                            .override(60, 60)
                            .into(holder.imageFilter)

                    }
                    Constans.filter_rating_final ->{
                        holder.imageFinalRate.visibility = View.VISIBLE
                        holder.yourRating.visibility = View.VISIBLE
                        holder.yourRatingFilter.visibility = View.GONE
                        holder.imageFilter.visibility = View.GONE
                        holder.yourRating.text = listRating.finalRate.toString()
                    }
                }
            }else{
                holder.imageFinalRate.visibility = View.VISIBLE
                holder.yourRating.visibility = View.VISIBLE
                holder.yourRatingFilter.visibility = View.GONE
                holder.imageFilter.visibility = View.GONE
                holder.yourRating.text = listRating.finalRate.toString()
            }







            val type = listRating.typeOeuvre
            val idItem = listRating.oeuvreId
            val mediaTypeTV = "tv"
            val mediaTypeMovie = "movie"

            // check which value has type to load movie or series or anime
            if (type == Constans.MOVIE_LIST){
                searchViewModelAPItmdb!!.getMovie(idItem!!,object :
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
                searchViewModelAPItmdb!!.getSeriesAndAnime(idItem!!, object :
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
                searchViewModelAPItmdb!!.getSeriesAndAnime(idItem!!, object :
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


            //Here is responsible for the view to edit the rating
            holder.imageArrowDown.setOnClickListener {
                if (holder.consContainer.tag == "open"){
                    holder.consContainer.visibility = View.GONE
                    holder.consContainer.tag = "close"
                }else{
                    holder.consContainer.visibility = View.VISIBLE
                    holder.consContainer.tag = "open"
                }
            }

            // we add the hint with the current ratings
            holder.editTextRatingStory.hint = listRating.historyRate.toString()
            holder.editTextRatingCharacters.hint = listRating.characterRate.toString()
            holder.editTextRatingSoundtrack.hint = listRating.soundtrackRate.toString()
            holder.editTextRatingPhotography.hint = listRating.effectsRate.toString()
            holder.editTextRatingEnjoyment.hint = listRating.enjoymentRate.toString()

            // is responsible for limiting the values ​​of the edittext from 0 - 10

            holder.editTextRatingStory.filters = arrayOf<InputFilter>(InputFilterMinMax(0.0, 10.0))
            holder.editTextRatingCharacters.filters = arrayOf<InputFilter>(InputFilterMinMax(0.0, 10.0))
            holder.editTextRatingSoundtrack.filters = arrayOf<InputFilter>(InputFilterMinMax(0.0, 10.0))
            holder.editTextRatingPhotography.filters = arrayOf<InputFilter>(InputFilterMinMax(0.0, 10.0))
            holder.editTextRatingEnjoyment.filters = arrayOf<InputFilter>(InputFilterMinMax(0.0, 10.0))





            // set up the spinner
            var statusInt: Int = 0

            val adpStatus: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(ctx, R.array.status, android.R.layout.simple_spinner_item)
            adpStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            holder.spinnerStatus.adapter = adpStatus

            holder.spinnerStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Log.d("ProfileListAdapter", "No selected")
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    Log.d("ProfileListAdapter", "Selected: $position")
                    statusInt = position
                    Log.d("ProfileListAdapter", "typeInt = $statusInt")
                }

            }



            holder.buttonUpdate.setOnClickListener {


                // we check that this is not empty the new rating variables
                var ratingStory  = if (holder.editTextRatingStory.text.isNullOrBlank()){
                    holder.editTextRatingStory.hint.toString()
                }else{
                    holder.editTextRatingStory.text.toString()
                }
                var ratingCharacters  = if (holder.editTextRatingCharacters.text.isNullOrBlank()){
                    holder.editTextRatingCharacters.hint.toString()
                }else{
                    holder.editTextRatingCharacters.text.toString()
                }
                var ratingSoundtrack  = if (holder.editTextRatingSoundtrack.text.isNullOrBlank()){
                    holder.editTextRatingSoundtrack.hint.toString()
                }else{
                    holder.editTextRatingSoundtrack.text.toString()
                }
                var ratingPhotography  = if (holder.editTextRatingPhotography.text.isNullOrBlank()){
                    holder.editTextRatingPhotography.hint.toString()
                }else{
                    holder.editTextRatingPhotography.text.toString()
                }
                var ratingEnjoyment  = if (holder.editTextRatingEnjoyment.text.isNullOrBlank()){
                    holder.editTextRatingEnjoyment.hint.toString()
                }else{
                    holder.editTextRatingPhotography.text.toString()
                }

                //val maxValue = 10

                //comprobamos si los rating superan el valor de 10 cambiar el valor para que sea 10
                //if (ratingStory.toFloat() >= maxValue){
                //    ratingStory = maxValue.toString()
                //}
                //if (ratingCharacters.toFloat() >= maxValue){
                //    ratingCharacters = maxValue.toString()
                //}
                //if (ratingPhotography.toFloat() >= maxValue){
                //    ratingPhotography = maxValue.toString()
                //}
                //if (ratingSoundtrack.toFloat() >= maxValue){
                //    ratingSoundtrack = maxValue.toString()
                //}
                //if (ratingEnjoyment.toFloat() >= maxValue){
                //    ratingEnjoyment = maxValue.toString()
                //}

                // we convert all the float ratings to add them y recogemos el valor editado
                val resultFinalRate = ratingStory.toFloat() + ratingCharacters.toFloat() + ratingPhotography.toFloat() + ratingSoundtrack.toFloat() + ratingEnjoyment.toFloat()
                // we divide to get the average
                val result = resultFinalRate / 5
                Log.d("ProfileListAdapterUI", "Result final rate = $result")
                holder.textViewNewRatingFinal.text = result.toString()

                holder.textViewNewRatingFinal.setOnClickListener {
                    holder.textViewNewRatingFinal.text = result.toString()
                }


                val updateItemRating = ItemListRating(listRating.userId,
                    statusInt,
                    listRating.oeuvreId,
                    listRating.addDate,
                    ratingStory.toFloat(),
                    ratingCharacters.toFloat(),
                    ratingPhotography.toFloat(),
                    ratingSoundtrack.toFloat(),
                    ratingEnjoyment.toFloat(),
                    result,
                    listRating.seasonNumber,
                    listRating.episodeNumber,
                    listRating.typeOeuvre
                )

                val snapshot: DocumentSnapshot = snapshots.getSnapshot(holder.adapterPosition)
                val id = snapshot.id

                ListProfileFragment().updateRatingItem(updateItemRating, id)

                holder.consContainer.visibility = View.GONE
                holder.consContainer.tag = "close"
                // lipiamos los editText
                holder.editTextRatingStory.text.clear()
                holder.editTextRatingCharacters.text.clear()
                holder.editTextRatingSoundtrack.text.clear()
                holder.editTextRatingPhotography.text.clear()
                holder.editTextRatingEnjoyment.text.clear()



                notifyItemChanged(holder.adapterPosition)
            }



            holder.itemView.setOnLongClickListener {
                val ref = FirebaseFirestore.getInstance().collection("Users/${preferences.userId}/RatingList")

                val snapshot: DocumentSnapshot = snapshots.getSnapshot(holder.adapterPosition)
                val id = snapshot.id

                ref.document(id)
                    .delete()
                    .addOnCompleteListener(object : OnCompleteListener<Void>{
                        override fun onComplete(p0: Task<Void>) {
                            if (p0.isSuccessful){
                                Log.d("ProfileListAdapter", "Deleted item")
                                notifyItemRemoved(holder.adapterPosition)
                                notifyDataSetChanged()
                            }else{
                                Log.d("ProfileListAdapter", "Failed deleted item")
                            }
                        }

                    })

                true
            }
        }

    }






    inner class ViewHolder internal constructor(view: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(view){
        internal var title: TextView
        internal var dateRelease: TextView
        internal var globalRating: TextView
        internal var numPosition: TextView
        internal var dateAt: TextView
        internal var yourRating: TextView
        internal var imagePoster: ImageView
        internal var yourRatingFilter: TextView
        internal var imageFilter: ImageView
        internal var imageFinalRate: ImageView
        internal var imageArrowDown: ImageView
        internal var consContainer : ConstraintLayout
        internal var editTextRatingStory: EditText
        internal var editTextRatingCharacters: EditText
        internal var editTextRatingSoundtrack: EditText
        internal var editTextRatingPhotography: EditText
        internal var editTextRatingEnjoyment: EditText
        internal var textViewNewRatingFinal: TextView
        internal var buttonUpdate: Button
        internal var spinnerStatus: Spinner


        init {
            title = view.findViewById(R.id.textViewTitleListProfile)
            dateRelease = view.findViewById(R.id.textViewDateReleastListProfile)
            globalRating = view.findViewById(R.id.textViewRatingListProfile)
            numPosition = view.findViewById(R.id.textViewListProfileNumberPosition)
            dateAt = view.findViewById(R.id.textViewListProfileDateAt)
            yourRating = view.findViewById(R.id.textViewListProfileYourRating)
            imagePoster = view.findViewById(R.id.imageViewPosterListProfile)
            yourRatingFilter = view.findViewById(R.id.textViewRatingFilterSelected)
            imageFilter = view.findViewById(R.id.imageViewFilterIcon)
            imageFinalRate = view.findViewById(R.id.imageViewFinalRate)
            imageArrowDown = view.findViewById(R.id.imageViewArrowDown)
            consContainer = view.findViewById(R.id.ConsContainer)
            editTextRatingStory = view.findViewById(R.id.editTextRatingStory)
            editTextRatingCharacters = view.findViewById(R.id.editTextRatingCharacters)
            editTextRatingSoundtrack = view.findViewById(R.id.editTextRatingSoundtrack)
            editTextRatingPhotography = view.findViewById(R.id.editTextRatingPhotography)
            editTextRatingEnjoyment = view.findViewById(R.id.editTextRatingEnjoyment)
            textViewNewRatingFinal = view.findViewById(R.id.textViewNewRatingFinal)
            spinnerStatus = view.findViewById(R.id.spinnerStatusRating)
            buttonUpdate = view.findViewById(R.id.buttonUpdateRating)

        }
    }

}