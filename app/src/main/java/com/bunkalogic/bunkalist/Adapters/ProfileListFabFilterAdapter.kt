package com.bunkalogic.bunkalist.Adapters

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import com.google.android.flexbox.FlexboxLayout
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.TextView
import com.bunkalogic.bunkalist.R
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.layoutInflater




class ProfileListFabFilterAdapter(private val ctx : Context) : PagerAdapter() {

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(ctx)
        val layout = inflater.inflate(R.layout.view_filters_sorters, collection, false) as ViewGroup
        val fbl = layout.findViewById<View>(R.id.flexboxFilters) as FlexboxLayout
        when (position) {
            0 -> inflateLayoutWithFilters(ctx.getString(R.string.fab_filter_list_profile_status), fbl)
            1 -> inflateLayoutWithFilters(ctx.getString(R.string.fab_filter_list_profile_rating), fbl)
            2 -> inflateLayoutWithFilters(ctx.getString(R.string.fab_filter_list_profile_order), fbl)
        }
        collection.addView(layout)
        return layout

    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }


    override fun isViewFromObject(view: View, objects: Any): Boolean {
        return view == objects
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return ctx.getString(R.string.fab_filter_list_profile_status)
            1 -> return ctx.getString(R.string.fab_filter_list_profile_rating)
            2 -> return ctx.getString(R.string.fab_filter_list_profile_order)
        }
        return ""
    }

    private fun inflateLayoutWithFilters(filterCategory : String, flexboxItem : FlexboxLayout){
        val statusList  = ctx.resources.getStringArray(R.array.status)

        val status = ctx.getString(R.string.fab_filter_list_profile_status)

        when(filterCategory){
            status -> statusList

        }



        for (filter in statusList){
            val viewItemFilter = ctx.layoutInflater.inflate(R.layout.single_item_filter, null)
            val textViewFilter = viewItemFilter.findViewById<TextView>(R.id.textViewFilterItem)
            val selected = "selected"
            val unselected = "unselected"

            textViewFilter.text = filter



            textViewFilter.setOnClickListener {
                if (textViewFilter.tag != null && textViewFilter.tag == selected){
                    textViewFilter.tag = unselected
                    textViewFilter.backgroundResource = R.drawable.item_filter_unselected
                }else{
                    textViewFilter.tag = selected
                    textViewFilter.backgroundResource = R.drawable.item_filter_selected
                }
            }
            if (textViewFilter.parent != null){
                (textViewFilter.parent as ViewGroup).removeView(textViewFilter)
            }

            flexboxItem.addView(textViewFilter)
        }

    }


}