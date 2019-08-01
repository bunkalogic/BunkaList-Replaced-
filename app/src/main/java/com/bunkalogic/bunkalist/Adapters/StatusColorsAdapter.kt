package com.bunkalogic.bunkalist.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bunkalogic.bunkalist.Models.StatusColorItem
import com.bunkalogic.bunkalist.Others.Constans
import com.bunkalogic.bunkalist.R
import org.jetbrains.anko.backgroundColorResource

class StatusColorsAdapter(val ctx: Context, private var mValues: MutableList<StatusColorItem>): RecyclerView.Adapter<StatusColorsAdapter.ViewHolder>(){

    var statusInt = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_status_color, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val statusItem = mValues[position]

        holder.textViewStatusName.text = statusItem.name
        holder.imageViewStatusColor.backgroundColorResource = statusItem.color

        var isClicked = -1


        holder.cardViewStatusColor.setOnClickListener {
            if (it.tag == "click"){
                it.tag = "unclick"
                isClicked = -1
                it.visibility = View.VISIBLE
                holder.imageViewStatusAnimation.visibility = View.GONE
                it.backgroundColorResource = statusItem.color

            }else if (it.tag == "unclick"&& isClicked == -1){
                it.tag = "click"
                it.visibility = View.INVISIBLE
                isClicked = position
                statusInt = statusItem.id
                when(statusItem.id){

                    Constans.filter_status_complete ->{

                        holder.imageViewStatusAnimation.visibility = View.VISIBLE

                        holder.imageViewStatusAnimation.setAnimation("green_tick.json")

                        holder.imageViewStatusAnimation.playAnimation()


                    }

                    Constans.filter_status_watching ->{

                        holder.imageViewStatusAnimation.visibility = View.VISIBLE

                        holder.imageViewStatusAnimation.setAnimation("blue_tick.json")

                        holder.imageViewStatusAnimation.playAnimation()

                    }

                    Constans.filter_status_waiting ->{

                        holder.imageViewStatusAnimation.visibility = View.VISIBLE

                        holder.imageViewStatusAnimation.setAnimation("purple-tick.json")

                        holder.imageViewStatusAnimation.playAnimation()

                    }

                    Constans.filter_status_pause ->{

                        holder.imageViewStatusAnimation.visibility = View.VISIBLE

                        holder.imageViewStatusAnimation.setAnimation("orange_tick.json")

                        holder.imageViewStatusAnimation.playAnimation()

                    }

                    Constans.filter_status_dropped ->{

                        holder.imageViewStatusAnimation.visibility = View.VISIBLE

                        holder.imageViewStatusAnimation.setAnimation("red_tick.json")

                        holder.imageViewStatusAnimation.playAnimation()

                    }

                }
            }
        }

        holder.imageViewStatusAnimation.setOnClickListener {
            holder.cardViewStatusColor.visibility = View.VISIBLE
            holder.cardViewStatusColor.tag = -1
            it.visibility = View.GONE
            statusInt = statusItem.id

        }





    }

    fun getStatusId() : Int{
        return statusInt
    }



    inner class ViewHolder internal constructor(mView: View): RecyclerView.ViewHolder(mView){
        internal var imageViewStatusColor: ImageView
        internal var imageViewStatusAnimation: LottieAnimationView
        internal var textViewStatusName: TextView
        internal var cardViewStatusColor: CardView



        init {
            imageViewStatusColor = mView.findViewById(R.id.circleColorStatus)
            imageViewStatusAnimation = mView.findViewById(R.id.circleAnimationStatus)
            textViewStatusName = mView.findViewById(R.id.textViewStatusName)
            cardViewStatusColor = mView.findViewById(R.id.CardViewColorStatus)
        }

    }



}