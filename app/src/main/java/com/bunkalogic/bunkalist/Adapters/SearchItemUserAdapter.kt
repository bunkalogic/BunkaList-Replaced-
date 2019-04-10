package com.bunkalogic.bunkalist.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bunkalogic.bunkalist.R
import com.bunkalogic.bunkalist.db.UserComplete
import com.bunkalogic.bunkalist.db.Users

class SearchItemUserAdapter(val ctx: Context, private var userList: MutableList<Users>) : RecyclerView.Adapter<SearchItemUserAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemUserAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_search_user_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: SearchItemUserAdapter.ViewHolder, position: Int) {
        val userItem = userList[position]

        holder.Username.text = userItem.username

        if (userItem.userPhoto!!.isEmpty()){
            Glide.with(holder.itemView)
                .load(R.drawable.ic_person_black_24dp)
                .apply(RequestOptions.circleCropTransform().override(60, 60))
                .into(holder.userPhoto)
        }else{
            Glide.with(holder.itemView)
                .load(userItem.userPhoto)
                .apply(RequestOptions.circleCropTransform().override(60, 60))
                .into(holder.userPhoto)
        }
    }

    fun setDataUsername(user: MutableList<Users>){
        this.userList = user
        notifyDataSetChanged()
    }

    inner class ViewHolder internal constructor(view: View): RecyclerView.ViewHolder(view){
        internal var Username : TextView
        internal var userPhoto : ImageView


        init {
            Username = view.findViewById(R.id.textViewSearchUsername)
            userPhoto = view.findViewById(R.id.imageViewSearchUserItem)
        }

    }

}