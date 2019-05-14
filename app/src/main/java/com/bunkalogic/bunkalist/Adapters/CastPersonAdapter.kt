package com.bunkalogic.bunkalist.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bunkalogic.bunkalist.Activities.DetailsActivities.PeopleActivity
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.Retrofit.Response.People.Cast
import org.jetbrains.anko.intentFor

class CastPersonAdapter(private val ctx: Context, private var mValues: List<Cast>): RecyclerView.Adapter<CastPersonAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_people_view, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mValues[position])
    }

    inner class ViewHolder(mView: View): RecyclerView.ViewHolder(mView){
        private val textViewName: TextView = mView.findViewById(R.id.textViewItemPeopleName)
        private val textViewWork: TextView = mView.findViewById(R.id.textViewItemPeopleWork)
        private val imageViewPoster: ImageView = mView.findViewById(R.id.ImageItemPeople)

        fun bind(mItem: Cast){

            textViewName.text = mItem.name.toString()
            textViewWork.text = mItem.character.toString()
            val imagePerson = mItem.profilePath.toString()

            Glide.with(ctx)
                .load(Constans.API_MOVIE_SERIES_ANIME_BASE_URL_IMG_PROFILE + imagePerson)
                .placeholder(R.drawable.ic_person_black_24dp)
                .centerCrop()
                .into(imageViewPoster)

            val idPerson = mItem.id
            Log.d("CastPersonAdapter", "$idPerson")
            val namePerson = mItem.name


            imageViewPoster.setOnClickListener {
                ctx.startActivity(ctx.intentFor<PeopleActivity>(
                        "idPerson" to idPerson,
                        "name" to namePerson
                ))
            }

        }

    }
}