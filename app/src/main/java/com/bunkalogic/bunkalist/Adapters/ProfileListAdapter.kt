package com.bunkalogic.bunkalist.Adapters

import androidx.lifecycle.ViewModelProviders
import android.content.Context
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
import org.jetbrains.anko.backgroundColorResource


class ProfileListAdapter(private val ctx: Context, private var mValues: MutableList<ItemListRating>): androidx.recyclerview.widget.RecyclerView.Adapter<ProfileListAdapter.ViewHolder>(){

    var searchViewModelAPItmdb: ViewModelAPItmdb? = null


    init {
        searchViewModelAPItmdb = ViewModelProviders.of(ctx as androidx.fragment.app.FragmentActivity).get(ViewModelAPItmdb::class.java)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.list_profile_fragement_item, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listRating = mValues[position]

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


        //is responsible for collecting the id of the document and pass it to the fragment to delete it or edit it



        holder.consContainer.visibility = View.GONE
        //Here is responsible for the view to edit the rating
        //holder.imageArrowDown.setOnClickListener {
        //    if (holder.consContainer.tag == "open"){
        //        holder.consContainer.visibility = View.GONE
        //        holder.consContainer.tag = "close"
        //    }else{
        //        holder.consContainer.visibility = View.VISIBLE
        //        holder.consContainer.tag = "open"
        //    }
        //}

        // we add the hint with the current ratings
        //holder.editTextRatingStory.hint = listRating.historyRate.toString()
        //holder.editTextRatingCharacters.hint = listRating.characterRate.toString()
        //holder.editTextRatingSoundtrack.hint = listRating.soundtrackRate.toString()
        //holder.editTextRatingPhotography.hint = listRating.effectsRate.toString()
        //holder.editTextRatingEnjoyment.hint = listRating.enjoymentRate.toString()
//
//
//
//
        //// we check that this is not empty the new rating variables
        //val ratingStory  = if (holder.editTextRatingStory.text.isNotEmpty()){
        //    holder.editTextRatingStory.text.toString()
        //}else{
        //    holder.editTextRatingStory.hint.toString()
        //}
        //val ratingCharacters  = if (holder.editTextRatingCharacters.text.isNotEmpty()){
        //    holder.editTextRatingCharacters.text.toString()
        //}else{
        //    holder.editTextRatingCharacters.hint.toString()
        //}
        //val ratingSoundtrack  = if (holder.editTextRatingSoundtrack.text.isNotEmpty()){
        //    holder.editTextRatingSoundtrack.text.toString()
        //}else{
        //    holder.editTextRatingSoundtrack.hint.toString()
        //}
        //val ratingPhotography  = if (holder.editTextRatingPhotography.text.isNotEmpty()){
        //    holder.editTextRatingPhotography.text.toString()
        //}else{
        //    holder.editTextRatingPhotography.hint.toString()
        //}
        //val ratingEnjoyment  = if (holder.editTextRatingEnjoyment.text.isNotEmpty()){
        //    holder.editTextRatingEnjoyment.text.toString()
        //}else{
        //    holder.editTextRatingPhotography.hint.toString()
        //}
//
//
//
//
//
        //// set up the spinner
        //var statusInt: Int = 0
//
        //val adpStatus: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(ctx, R.array.status, android.R.layout.simple_spinner_item)
        //adpStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //holder.spinnerStatus.adapter = adpStatus
//
        //holder.spinnerStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
        //    override fun onNothingSelected(parent: AdapterView<*>?) {
        //        Log.d("ProfileListAdapter", "No selected")
        //    }
//
        //    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //        Log.d("ProfileListAdapter", "Selected: $position")
        //        statusInt = position
        //        Log.d("ProfileListAdapter", "typeInt = $statusInt")
        //    }
//
        //}
        //// we convert all the float ratings to add them y recogemos el valor editado
        //var resultFinalRate = ratingStory.toFloat() + ratingCharacters.toFloat() + ratingPhotography.toFloat() + ratingSoundtrack.toFloat() + ratingEnjoyment.toFloat()
        //// we divide to get the average
        //val result = resultFinalRate / 5
        //Log.d("AddListDialog", "Result final rate = $result")
        //holder.textViewNewRatingFinal.text = result.toString()
//
        //holder.textViewNewRatingFinal.setOnClickListener {
        //    holder.textViewNewRatingFinal.text = result.toString()
        //}



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

        // se encarga de comprobar el status de darle un color personalizado dependiendo del status

        when(listRating.status){
            Constans.filter_status_complete ->{
                holder.viewStatus.backgroundColorResource =  R.color.colorStatusComplete
            }
            Constans.filter_status_watching ->{
                holder.viewStatus.backgroundColorResource = R.color.colorStatusWatching
            }
            Constans.filter_status_waiting ->{
                holder.viewStatus.backgroundColorResource = R.color.colorStatusPending
            }
            Constans.filter_status_pause ->{
                holder.viewStatus.backgroundColorResource = R.color.colorStatusPause
            }
            Constans.filter_status_dropped ->{
                holder.viewStatus.backgroundColorResource = R.color.colorStatusDropped
            }
        }



        //holder.buttonUpdate.setOnClickListener {
        //    val newItemRating = ItemListRating(listRating.userId,
        //        statusInt,
        //        listRating.oeuvreId,
        //        listRating.addDate,
        //        ratingStory.toFloat(),
        //        ratingCharacters.toFloat(),
        //        ratingPhotography.toFloat(),
        //        ratingSoundtrack.toFloat(),
        //        ratingEnjoyment.toFloat(),
        //        result,
        //        listRating.seasonNumber,
        //        listRating.episodeNumber,
        //        listRating.typeOeuvre
//
        //    )
//
//
//
//
//
        //    //ListProfileFragment().updateRatingItem(newItemRating, position)
        //}



            holder.itemView.setOnLongClickListener {
               val ref = FirebaseFirestore.getInstance().collection("Users/${preferences.userId}/RatingList")

               //val refId = ref.document()
               //Log.d("ProfileListAdapter", refId)


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
        internal var viewStatus: View


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
            viewStatus = view.findViewById(R.id.viewStatus)

        }
    }

}