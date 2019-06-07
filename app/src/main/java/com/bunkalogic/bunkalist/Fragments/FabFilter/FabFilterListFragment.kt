package com.bunkalogic.bunkalist.Fragments.FabFilter


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.PagerAdapter
import android.util.ArrayMap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.allattentionhere.fabulousfilter.AAH_FabulousFragment
import com.bunkalogic.bunkalist.R
import com.google.android.flexbox.FlexboxLayout
import kotlinx.android.synthetic.main.fragment_fab_filter.view.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.layoutInflater
import android.widget.TextView
import com.bunkalogic.bunkalist.Models.FilterListUser
import com.bunkalogic.bunkalist.Others.Constans
import java.lang.Exception
import com.bunkalogic.bunkalist.Fragments.ListProfileFragment


class FabFilterListFragment : AAH_FabulousFragment() {

    lateinit var contentView : View
    var applied_filters: ArrayMap<String, Int> = ArrayMap()
    var textviews: ArrayList<TextView> = ArrayList()

    val filterListStatus: ArrayList<FilterListUser> by lazy { getFilterStatus() }
    val filterListRating: ArrayList<FilterListUser> by lazy { getFilterRating() }
    val filterListOrder: ArrayList<FilterListUser> by lazy { getFilterOrder() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applied_filters = ListProfileFragment().getAppliedFilters()

        for (entry in applied_filters.entries){
            Log.d("ProfileListFabFilterAd", "from activity: ${entry.key}, ${entry.value}")

        }

    }



    override fun setupDialog(dialog: Dialog?, style: Int) {
        contentView = View.inflate(context, R.layout.fragment_fab_filter, null)
        val rl_content = contentView.findViewById<View>(R.id.rl_content) as RelativeLayout
        val ll_buttons = contentView.findViewById<View>(R.id.ll_buttons) as LinearLayout

        contentView.findViewById<ImageButton>(R.id.fabFilterOk).setOnClickListener {
            Constans.applied_list_filter = applied_filters
            closeFilter(applied_filters)
        }

        contentView.findViewById<ImageButton>(R.id.fabFilterClose).setOnClickListener {
            for (tv in textviews){
                tv.tag = "unselected"
                tv.backgroundResource = R.drawable.item_filter_unselected
            }
            applied_filters.clear()
            Constans.applied_list_filter.clear()
        }

        setViewgroupStatic(ll_buttons)
        setViewMain(rl_content)
        //param

        // Add other functions
        setUpAdapter()

        setMainContentView(contentView)
        super.setupDialog(dialog, style)
    }

    private fun setUpAdapter(){
        val mAdapter = ProfileListFabFilterAdapter(context!!)
        contentView.vp_types.offscreenPageLimit = 3
        contentView.vp_types.adapter = mAdapter
        mAdapter.notifyDataSetChanged()
        contentView.tabs_types.tabGravity = TabLayout.MODE_FIXED
        contentView.tabs_types.setupWithViewPager(contentView.vp_types)

    }

    inner class ProfileListFabFilterAdapter(private val ctx : Context) : PagerAdapter() {

        override fun instantiateItem(collection: ViewGroup, position: Int): Any {
            val inflater = LayoutInflater.from(ctx)
            val layout = inflater.inflate(R.layout.view_filters_sorters, collection, false) as ViewGroup
            val fbl = layout.findViewById<View>(R.id.flexboxFilters) as FlexboxLayout
            // ArraysList
            val listStatus = filterListStatus
            val listRating = filterListRating
            val listOrder = filterListOrder

            when (position) {
                0 -> inflateLayoutWithFilters(ctx.getString(R.string.fab_filter_list_profile_status), fbl, listStatus)
                1 -> inflateLayoutWithFilters(ctx.getString(R.string.fab_filter_list_profile_rating), fbl, listRating)
                2 -> inflateLayoutWithFilters(ctx.getString(R.string.fab_filter_list_profile_order), fbl, listOrder)
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
    }

    fun inflateLayoutWithFilters(filterCategory : String, flexboxItem : FlexboxLayout, listFilter: ArrayList<FilterListUser>){
        val status = context!!.getString(R.string.fab_filter_list_profile_status)
        val rating = context!!.getString(R.string.fab_filter_list_profile_rating)
        val order = context!!.getString(R.string.fab_filter_list_profile_order)

        when(filterCategory){

            status -> listFilter

            rating -> listFilter

            order -> listFilter
        }


        for (filter in listFilter){
            val viewItemFilter = context!!.layoutInflater.inflate(R.layout.single_item_filter, null)
            val textViewFilter = viewItemFilter.findViewById<TextView>(R.id.textViewFilterItem)
            val selected = "selected"
            val unselected = "unselected"
            val filterId = filter.id
            val listFilterFinal: List<Int> = mutableListOf()

            textViewFilter.text = filter.name

            textViewFilter.setOnClickListener {
                if (textViewFilter.tag != null && textViewFilter.tag == selected){
                    textViewFilter.tag = unselected
                    textViewFilter.backgroundResource = R.drawable.item_filter_unselected
                    removeFromSelectedMap(filterCategory, filterId)
                }else{
                    val filterTextSelected = textViewFilter.text.toString()
                    Log.d("ProfileListFabFilterAd", "FilterListUser Selected: $filterTextSelected")
                    Log.d("ProfileListFabFilterAd", "FilterListUser Selected: ${filter.id}")

                    textViewFilter.tag = selected
                    textViewFilter.backgroundResource = R.drawable.item_filter_selected
                    addToSelectedMap(filterCategory, filterId)
                }

            }
            if (textViewFilter.parent != null){
                (textViewFilter.parent as ViewGroup).removeView(textViewFilter)
            }


            try {
                Log.d("ProfileListFabFilterAd", "key: $filterCategory, value: ${listFilterFinal[filterId]}")
                Log.d("ProfileListFabFilterAd", "applied_filter != null: ${applied_filters != null}")
                Log.d("ProfileListFabFilterAd", "applied_filter.get(key) != null: ${applied_filters[filterCategory] != null}")
               // Log.d("ProfileListFabFilterAd", "applied_filters.get(key).contains(listFilterFinal.get(filterId)): ${applied_filters.get(filterCategory)?.contains(listFilterFinal.get(filterId))}")
            }catch (e : Exception){

            }
            if (applied_filters.get(filterCategory) != null){
                textViewFilter.tag = selected
                textViewFilter.backgroundResource = R.drawable.item_filter_selected
            }else{
                textViewFilter.tag = unselected
                textViewFilter.backgroundResource = R.drawable.item_filter_unselected
            }


            textviews.add(textViewFilter)
            flexboxItem.addView(textViewFilter)
        }


    }


    private fun addToSelectedMap(key: String, value: Int){
        if (applied_filters.size != 0 && applied_filters[key] != null){
            applied_filters.put(key, value)
        }else{

            applied_filters.put(key, value)
        }
    }

    private fun removeFromSelectedMap(key: String, value: Int){
        if (applied_filters.size == 1){
            applied_filters.remove(key)
        }else{
            applied_filters.remove(key, value)
        }
    }

    private fun getFilterStatus(): ArrayList<FilterListUser> {
        return object: ArrayList<FilterListUser>(){
            init {
                add(FilterListUser(Constans.filter_status_complete, getString(R.string.fab_filter_profile_status_complete)))
                add(FilterListUser(Constans.filter_status_pause, getString(R.string.fab_filter_profile_status_pause)))
                add(FilterListUser(Constans.filter_status_watching, getString(R.string.fab_filter_profile_status_watching)))
                add(FilterListUser(Constans.filter_status_waiting, getString(R.string.fab_filter_profile_status_waiting)))
                add(FilterListUser(Constans.filter_status_dropped, getString(R.string.fab_filter_profile_status_dropped)))
            }
        }
    }

    private fun getFilterRating(): ArrayList<FilterListUser> {
        return object: ArrayList<FilterListUser>(){
            init {
                add(FilterListUser(Constans.filter_rating_story, getString(R.string.fab_filter_profile_rating_story)))
                add(FilterListUser(Constans.filter_rating_soundtrack, getString(R.string.fab_filter_profile_rating_soundtrack)))
                add(FilterListUser(Constans.filter_rating_characters, getString(R.string.fab_filter_profile_rating_characters)))
                add(FilterListUser(Constans.filter_rating_photography, getString(R.string.fab_filter_profile_rating_photography)))
                add(FilterListUser(Constans.filter_rating_enjoyment, getString(R.string.fab_filter_profile_rating_enjoyment)))
                add(FilterListUser(Constans.filter_rating_final, getString(R.string.fab_filter_profile_rating_final)))
            }
        }
    }

    private fun getFilterOrder(): ArrayList<FilterListUser> {
        return object: ArrayList<FilterListUser>(){
            init {
                add(FilterListUser(Constans.filter_order_ascendant, getString(R.string.fab_filter_profile_order_ascendent)))
                add(FilterListUser(Constans.filter_order_descend, getString(R.string.fab_filter_profile_order_descend)))
            }
        }
    }



    companion object {


        fun newInstance(): FabFilterListFragment {
            return FabFilterListFragment()
        }
    }

}


