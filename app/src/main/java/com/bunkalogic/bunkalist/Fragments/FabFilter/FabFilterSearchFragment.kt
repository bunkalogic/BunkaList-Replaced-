package com.bunkalogic.bunkalist.Fragments.FabFilter


import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
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
import com.bunkalogic.bunkalist.Others.Constans
import java.lang.Exception
import com.bunkalogic.bunkalist.Fragments.SearchFragment
import com.bunkalogic.bunkalist.Models.FilterSearchData




class FabFilterSearchFragment : AAH_FabulousFragment() {

    lateinit var contentView : View
    var textviews: ArrayList<TextView> = ArrayList()

    var applied_filters: ArrayMap<String, MutableList<Any>> = ArrayMap()


    val filterListType: ArrayList<FilterSearchData> by lazy { getFilterType() }
    val filterListGenresMovies: ArrayList<FilterSearchData> by lazy { getFilterGenresMovies() }
    val filterListYear: ArrayList<FilterSearchData> by lazy { getFilterYear() }
    val filterListSort: ArrayList<FilterSearchData> by lazy { getFilterSort() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applied_filters = SearchFragment().getAppliedFilters()

        for (entry in applied_filters.entries){
            Log.d("ProfileListFabFilterAd", "from activity: ${entry.key}, ${entry.value}")

        }

    }



    override fun setupDialog(dialog: Dialog?, style: Int) {
        contentView = View.inflate(context, R.layout.fragment_fab_filter, null)
        val rl_content = contentView.findViewById<View>(R.id.rl_content) as RelativeLayout
        val ll_buttons = contentView.findViewById<View>(R.id.ll_buttons) as LinearLayout

        contentView.findViewById<ImageButton>(R.id.fabFilterOk).setOnClickListener {
            Constans.applied_search_filter = applied_filters
            for (entry in Constans.applied_search_filter.entries){
                Log.d("ProfileListFabFilterAd", "from activity: ${entry.key}, ${entry.value}")

            }
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

    @SuppressLint("WrongConstant")
    private fun setUpAdapter(){
        val mAdapter = ProfileListFabFilterAdapter(context!!)
        contentView.vp_types.offscreenPageLimit = 4
        contentView.vp_types.adapter = mAdapter
        mAdapter.notifyDataSetChanged()
        contentView.tabs_types.tabGravity = TabLayout.MODE_FIXED
        contentView.tabs_types.setupWithViewPager(contentView.vp_types)

    }

    inner class ProfileListFabFilterAdapter(private val ctx : Context) : androidx.viewpager.widget.PagerAdapter() {

        override fun instantiateItem(collection: ViewGroup, position: Int): Any {
            val inflater = LayoutInflater.from(ctx)
            val layout = inflater.inflate(R.layout.view_filters_sorters, collection, false) as ViewGroup
            val fbl = layout.findViewById<View>(R.id.flexboxFilters) as FlexboxLayout
            // ArraysList
            val listType = filterListType
            val listGenresMovies = filterListGenresMovies
            val listYear = filterListYear
            val listSort = filterListSort

            when (position) {
                0 -> inflateLayoutWithFilters(ctx.getString(R.string.fab_filter_search_type), fbl, listType)
                1 ->  inflateLayoutWithFilters(ctx.getString(R.string.fab_filter_search_genres), fbl, listGenresMovies)
                2 -> inflateLayoutWithFilters(ctx.getString(R.string.fab_filter_search_year), fbl, listYear)
                3 -> inflateLayoutWithFilters(ctx.getString(R.string.fab_filter_search_sort), fbl, listSort)
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
            return 4
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                0 -> return ctx.getString(R.string.fab_filter_search_type)
                1 -> return ctx.getString(R.string.fab_filter_search_genres)
                2 -> return ctx.getString(R.string.fab_filter_search_year)
                3 -> return ctx.getString(R.string.fab_filter_search_sort)
            }
            return ""
        }
    }

    fun inflateLayoutWithFilters(filterCategory : String, flexboxItem : FlexboxLayout, listFilter: ArrayList<FilterSearchData>){
        val type = context!!.getString(R.string.fab_filter_search_type)
        val genres = context!!.getString(R.string.fab_filter_search_genres)
        val year = context!!.getString(R.string.fab_filter_search_year)
        val sort = context!!.getString(R.string.fab_filter_search_sort)


        when(filterCategory){

            type -> listFilter

            genres ->listFilter

            year -> listFilter

            sort -> listFilter
        }


        for (filter in listFilter){
            val viewItemFilter = context!!.layoutInflater.inflate(R.layout.single_item_filter, null)
            val textViewFilter = viewItemFilter.findViewById<TextView>(R.id.textViewFilterItem)
            val selected = "selected"
            val unselected = "unselected"
            val filterId = filter.id


            textViewFilter.text = filter.name.toString()

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


    private fun addToSelectedMap(key: String, value: Any){
        if (applied_filters.size != 0 && applied_filters[key] != null){
            applied_filters.get(key)?.add(value)
        }else{
            val temp : MutableList<Any> = ArrayList()
            temp.add(value)
            applied_filters[key] = temp
        }
    }


    private fun removeFromSelectedMap(key: String, value: Any){
        if (applied_filters.size == 1){
            applied_filters.remove(key)
        }else{
            applied_filters[key]?.remove(value)
        }
    }

    private fun getFilterType(): ArrayList<FilterSearchData> {
        return object: ArrayList<FilterSearchData>(){
            init {
                add(FilterSearchData(Constans.filter_search_type_movie_id, getString(R.string.profile_list_activity_tab_movie)))
                add(FilterSearchData(Constans.filter_search_type_series_id, getString(R.string.profile_list_activity_tab_serie)))
                add(FilterSearchData(Constans.filter_search_type_anime_id, getString(R.string.profile_list_activity_tab_anime)))

            }
        }
    }

    private fun getFilterGenresMovies(): ArrayList<FilterSearchData> {
        return object: ArrayList<FilterSearchData>(){
            init {
                add(FilterSearchData(Constans.filter_search_genres_movies_action, getString(R.string.fab_filter_search_genres_action)))
                add(FilterSearchData(Constans.filter_search_genres_movies_adventure, getString(R.string.fab_filter_search_genres_adventure)))
                add(FilterSearchData(Constans.filter_search_genres_movies_animation, getString(R.string.fab_filter_search_genres_animation)))
                add(FilterSearchData(Constans.filter_search_genres_movies_comedy, getString(R.string.fab_filter_search_genres_comedy)))
                add(FilterSearchData(Constans.filter_search_genres_movies_crime, getString(R.string.fab_filter_search_genres_crime)))
                add(FilterSearchData(Constans.filter_search_genres_movies_documentary, getString(R.string.fab_filter_search_genres_documentary)))
                add(FilterSearchData(Constans.filter_search_genres_movies_drama, getString(R.string.fab_filter_search_genres_drama)))
                add(FilterSearchData(Constans.filter_search_genres_movies_family, getString(R.string.fab_filter_search_genres_family)))
                add(FilterSearchData(Constans.filter_search_genres_movies_fantasy, getString(R.string.fab_filter_search_genres_fantasy)))
                add(FilterSearchData(Constans.filter_search_genres_movies_history, getString(R.string.fab_filter_search_genres_history)))
                add(FilterSearchData(Constans.filter_search_genres_movies_horror, getString(R.string.fab_filter_search_genres_horror)))
                add(FilterSearchData(Constans.filter_search_genres_movies_mistery, getString(R.string.fab_filter_search_genres_mistery)))
                add(FilterSearchData(Constans.filter_search_genres_movies_music, getString(R.string.fab_filter_search_genres_music)))
                add(FilterSearchData(Constans.filter_search_genres_movies_romance, getString(R.string.fab_filter_search_genres_romance)))
                add(FilterSearchData(Constans.filter_search_genres_movies_scince_fiction, getString(R.string.fab_filter_science_fiction)))
                add(FilterSearchData(Constans.filter_search_genres_movies_thriller, getString(R.string.fab_filter_search_genres_thriller)))
                //add(FilterSearchData(Constans.filter_search_genres_movies_tv, getString(R.string.fab_filter_search_genres_tv)))
                add(FilterSearchData(Constans.filter_search_genres_movies_war, getString(R.string.fab_filter_search_genres_war)))
                add(FilterSearchData(Constans.filter_search_genres_movies_western, getString(R.string.fab_filter_search_genres_western)))
            }
        }
    }

    //private fun getFilterGenresSeries(): ArrayList<FilterSearchData> {
    //    return object: ArrayList<FilterSearchData>(){
    //        init {
    //            add(FilterSearchData(Constans.filter_search_genres_series_action_adventure, getString(R.string.fab_filter_search_genres_action_adventure)))
    //            add(FilterSearchData(Constans.filter_search_genres_series_animation, getString(R.string.fab_filter_search_genres_animation)))
    //            add(FilterSearchData(Constans.filter_search_genres_series_comedy, getString(R.string.fab_filter_search_genres_comedy)))
    //            add(FilterSearchData(Constans.filter_search_genres_series_crime, getString(R.string.fab_filter_search_genres_crime)))
    //            add(FilterSearchData(Constans.filter_search_genres_series_documentary, getString(R.string.fab_filter_search_genres_documentary)))
    //            add(FilterSearchData(Constans.filter_search_genres_series_drama, getString(R.string.fab_filter_search_genres_drama)))
    //            add(FilterSearchData(Constans.filter_search_genres_series_family, getString(R.string.fab_filter_search_genres_family)))
    //            add(FilterSearchData(Constans.filter_search_genres_series_mistery, getString(R.string.fab_filter_search_genres_mistery)))
    //            add(FilterSearchData(Constans.filter_search_genres_series_reality, getString(R.string.fab_filter_search_genres_reality)))
    //            add(FilterSearchData(Constans.filter_search_genres_movies_romance, getString(R.string.fab_filter_search_genres_romance)))
    //            add(FilterSearchData(Constans.filter_search_genres_series_scince_fiction_fantasy, getString(R.string.fab_filter_science_fiction_fantasy)))
    //            add(FilterSearchData(Constans.filter_search_genres_series_telenovel, getString(R.string.fab_filter_search_genres_telenovel)))
    //            add(FilterSearchData(Constans.filter_search_genres_series_war_politics, getString(R.string.fab_filter_search_genres_war_politics)))
    //            add(FilterSearchData(Constans.filter_search_genres_series_western, getString(R.string.fab_filter_search_genres_western)))
    //        }
    //    }
    //}

    private fun getFilterYear(): ArrayList<FilterSearchData> {
        return object: ArrayList<FilterSearchData>(){
            init {
                add(FilterSearchData(Constans.filter_search_year_2019, Constans.filter_search_year_2019))
                add(FilterSearchData(Constans.filter_search_year_2018, Constans.filter_search_year_2018))
                add(FilterSearchData(Constans.filter_search_year_2017, Constans.filter_search_year_2017))
                add(FilterSearchData(Constans.filter_search_year_2016, Constans.filter_search_year_2016))
                add(FilterSearchData(Constans.filter_search_year_2015, Constans.filter_search_year_2015))
                add(FilterSearchData(Constans.filter_search_year_2014, Constans.filter_search_year_2014))
                add(FilterSearchData(Constans.filter_search_year_2013, Constans.filter_search_year_2013))
                add(FilterSearchData(Constans.filter_search_year_2012, Constans.filter_search_year_2012))
                add(FilterSearchData(Constans.filter_search_year_2011, Constans.filter_search_year_2011))
                add(FilterSearchData(Constans.filter_search_year_2010, Constans.filter_search_year_2010))
                add(FilterSearchData(Constans.filter_search_year_2009, Constans.filter_search_year_2009))
                add(FilterSearchData(Constans.filter_search_year_2008, Constans.filter_search_year_2008))
                add(FilterSearchData(Constans.filter_search_year_2007, Constans.filter_search_year_2007))
                add(FilterSearchData(Constans.filter_search_year_2006, Constans.filter_search_year_2006))
                add(FilterSearchData(Constans.filter_search_year_2005, Constans.filter_search_year_2005))
                add(FilterSearchData(Constans.filter_search_year_2004, Constans.filter_search_year_2004))
                add(FilterSearchData(Constans.filter_search_year_2003, Constans.filter_search_year_2003))
                add(FilterSearchData(Constans.filter_search_year_2002, Constans.filter_search_year_2002))
                add(FilterSearchData(Constans.filter_search_year_2001, Constans.filter_search_year_2001))
                add(FilterSearchData(Constans.filter_search_year_2000, Constans.filter_search_year_2000))
                add(FilterSearchData(Constans.filter_search_year_1999, Constans.filter_search_year_1999))
                add(FilterSearchData(Constans.filter_search_year_1998, Constans.filter_search_year_1998))
                add(FilterSearchData(Constans.filter_search_year_1997, Constans.filter_search_year_1997))
                add(FilterSearchData(Constans.filter_search_year_1996, Constans.filter_search_year_1996))
                add(FilterSearchData(Constans.filter_search_year_1995, Constans.filter_search_year_1995))
                add(FilterSearchData(Constans.filter_search_year_1994, Constans.filter_search_year_1994))
                add(FilterSearchData(Constans.filter_search_year_1993, Constans.filter_search_year_1993))
                add(FilterSearchData(Constans.filter_search_year_1992, Constans.filter_search_year_1992))
                add(FilterSearchData(Constans.filter_search_year_1991, Constans.filter_search_year_1991))
                add(FilterSearchData(Constans.filter_search_year_1990, Constans.filter_search_year_1990))
                add(FilterSearchData(Constans.filter_search_year_1989, Constans.filter_search_year_1989))
                add(FilterSearchData(Constans.filter_search_year_1988, Constans.filter_search_year_1988))
                add(FilterSearchData(Constans.filter_search_year_1987, Constans.filter_search_year_1987))
                add(FilterSearchData(Constans.filter_search_year_1986, Constans.filter_search_year_1986))
                add(FilterSearchData(Constans.filter_search_year_1985, Constans.filter_search_year_1985))
                add(FilterSearchData(Constans.filter_search_year_1984, Constans.filter_search_year_1984))
                add(FilterSearchData(Constans.filter_search_year_1983, Constans.filter_search_year_1983))
                add(FilterSearchData(Constans.filter_search_year_1982, Constans.filter_search_year_1982))
                add(FilterSearchData(Constans.filter_search_year_1981, Constans.filter_search_year_1981))
                add(FilterSearchData(Constans.filter_search_year_1980, Constans.filter_search_year_1980))
                add(FilterSearchData(Constans.filter_search_year_1979, Constans.filter_search_year_1979))
                add(FilterSearchData(Constans.filter_search_year_1978, Constans.filter_search_year_1978))
                add(FilterSearchData(Constans.filter_search_year_1977, Constans.filter_search_year_1977))
                add(FilterSearchData(Constans.filter_search_year_1976, Constans.filter_search_year_1976))
                add(FilterSearchData(Constans.filter_search_year_1975, Constans.filter_search_year_1975))
                add(FilterSearchData(Constans.filter_search_year_1974, Constans.filter_search_year_1974))
                add(FilterSearchData(Constans.filter_search_year_1973, Constans.filter_search_year_1973))
                add(FilterSearchData(Constans.filter_search_year_1972, Constans.filter_search_year_1972))
                add(FilterSearchData(Constans.filter_search_year_1971, Constans.filter_search_year_1971))
                add(FilterSearchData(Constans.filter_search_year_1970, Constans.filter_search_year_1970))
                add(FilterSearchData(Constans.filter_search_year_1969, Constans.filter_search_year_1969))
                add(FilterSearchData(Constans.filter_search_year_1968, Constans.filter_search_year_1968))
                add(FilterSearchData(Constans.filter_search_year_1967, Constans.filter_search_year_1967))
                add(FilterSearchData(Constans.filter_search_year_1966, Constans.filter_search_year_1966))
                add(FilterSearchData(Constans.filter_search_year_1965, Constans.filter_search_year_1965))
                add(FilterSearchData(Constans.filter_search_year_1964, Constans.filter_search_year_1964))
                add(FilterSearchData(Constans.filter_search_year_1963, Constans.filter_search_year_1963))
                add(FilterSearchData(Constans.filter_search_year_1962, Constans.filter_search_year_1962))
                add(FilterSearchData(Constans.filter_search_year_1961, Constans.filter_search_year_1961))
                add(FilterSearchData(Constans.filter_search_year_1960, Constans.filter_search_year_1960))
                add(FilterSearchData(Constans.filter_search_year_1959, Constans.filter_search_year_1959))
                add(FilterSearchData(Constans.filter_search_year_1958, Constans.filter_search_year_1958))
                add(FilterSearchData(Constans.filter_search_year_1957, Constans.filter_search_year_1957))
                add(FilterSearchData(Constans.filter_search_year_1956, Constans.filter_search_year_1956))
                add(FilterSearchData(Constans.filter_search_year_1955, Constans.filter_search_year_1955))
                add(FilterSearchData(Constans.filter_search_year_1954, Constans.filter_search_year_1954))
                add(FilterSearchData(Constans.filter_search_year_1953, Constans.filter_search_year_1953))
                add(FilterSearchData(Constans.filter_search_year_1952, Constans.filter_search_year_1952))
                add(FilterSearchData(Constans.filter_search_year_1951, Constans.filter_search_year_1951))
                add(FilterSearchData(Constans.filter_search_year_1950, Constans.filter_search_year_1950))
                add(FilterSearchData(Constans.filter_search_year_1949, Constans.filter_search_year_1949))
                add(FilterSearchData(Constans.filter_search_year_1948, Constans.filter_search_year_1948))
                add(FilterSearchData(Constans.filter_search_year_1947, Constans.filter_search_year_1947))
                add(FilterSearchData(Constans.filter_search_year_1946, Constans.filter_search_year_1946))
                add(FilterSearchData(Constans.filter_search_year_1945, Constans.filter_search_year_1945))
                add(FilterSearchData(Constans.filter_search_year_1944, Constans.filter_search_year_1944))
                add(FilterSearchData(Constans.filter_search_year_1943, Constans.filter_search_year_1943))
                add(FilterSearchData(Constans.filter_search_year_1942, Constans.filter_search_year_1942))
                add(FilterSearchData(Constans.filter_search_year_1941, Constans.filter_search_year_1941))
                add(FilterSearchData(Constans.filter_search_year_1940, Constans.filter_search_year_1940))
                add(FilterSearchData(Constans.filter_search_year_1939, Constans.filter_search_year_1939))
                add(FilterSearchData(Constans.filter_search_year_1938, Constans.filter_search_year_1938))
                add(FilterSearchData(Constans.filter_search_year_1937, Constans.filter_search_year_1937))
                add(FilterSearchData(Constans.filter_search_year_1936, Constans.filter_search_year_1936))
                add(FilterSearchData(Constans.filter_search_year_1935, Constans.filter_search_year_1935))
                add(FilterSearchData(Constans.filter_search_year_1934, Constans.filter_search_year_1934))
                add(FilterSearchData(Constans.filter_search_year_1933, Constans.filter_search_year_1933))
                add(FilterSearchData(Constans.filter_search_year_1932, Constans.filter_search_year_1932))
                add(FilterSearchData(Constans.filter_search_year_1931, Constans.filter_search_year_1931))
                add(FilterSearchData(Constans.filter_search_year_1930, Constans.filter_search_year_1930))
                add(FilterSearchData(Constans.filter_search_year_1929, Constans.filter_search_year_1929))
                add(FilterSearchData(Constans.filter_search_year_1928, Constans.filter_search_year_1928))
                add(FilterSearchData(Constans.filter_search_year_1927, Constans.filter_search_year_1927))
                add(FilterSearchData(Constans.filter_search_year_1926, Constans.filter_search_year_1926))
                add(FilterSearchData(Constans.filter_search_year_1925, Constans.filter_search_year_1925))
                add(FilterSearchData(Constans.filter_search_year_1924, Constans.filter_search_year_1924))
                add(FilterSearchData(Constans.filter_search_year_1923, Constans.filter_search_year_1923))
                add(FilterSearchData(Constans.filter_search_year_1922, Constans.filter_search_year_1922))
                add(FilterSearchData(Constans.filter_search_year_1921, Constans.filter_search_year_1921))
                add(FilterSearchData(Constans.filter_search_year_1920, Constans.filter_search_year_1920))
                add(FilterSearchData(Constans.filter_search_year_1919, Constans.filter_search_year_1919))
                add(FilterSearchData(Constans.filter_search_year_1918, Constans.filter_search_year_1918))
                add(FilterSearchData(Constans.filter_search_year_1917, Constans.filter_search_year_1917))
                add(FilterSearchData(Constans.filter_search_year_1916, Constans.filter_search_year_1916))
                add(FilterSearchData(Constans.filter_search_year_1915, Constans.filter_search_year_1915))
                add(FilterSearchData(Constans.filter_search_year_1914, Constans.filter_search_year_1914))
                add(FilterSearchData(Constans.filter_search_year_1913, Constans.filter_search_year_1913))
                add(FilterSearchData(Constans.filter_search_year_1912, Constans.filter_search_year_1912))
                add(FilterSearchData(Constans.filter_search_year_1911, Constans.filter_search_year_1911))
                add(FilterSearchData(Constans.filter_search_year_1910, Constans.filter_search_year_1910))
                add(FilterSearchData(Constans.filter_search_year_1909, Constans.filter_search_year_1909))
                add(FilterSearchData(Constans.filter_search_year_1908, Constans.filter_search_year_1908))
                add(FilterSearchData(Constans.filter_search_year_1907, Constans.filter_search_year_1907))
                add(FilterSearchData(Constans.filter_search_year_1906, Constans.filter_search_year_1906))
                add(FilterSearchData(Constans.filter_search_year_1905, Constans.filter_search_year_1905))
                add(FilterSearchData(Constans.filter_search_year_1904, Constans.filter_search_year_1904))
                add(FilterSearchData(Constans.filter_search_year_1903, Constans.filter_search_year_1903))
                add(FilterSearchData(Constans.filter_search_year_1902, Constans.filter_search_year_1902))
                add(FilterSearchData(Constans.filter_search_year_1901, Constans.filter_search_year_1901))
                add(FilterSearchData(Constans.filter_search_year_1900, Constans.filter_search_year_1900))
            }
        }
    }

    private fun getFilterSort(): ArrayList<FilterSearchData> {
        return object: ArrayList<FilterSearchData>(){
            init {
                add(FilterSearchData(Constans.filter_search_sort_populary_asc, getString(R.string.fab_filter_search_sort_populary_asc)))
                add(FilterSearchData(Constans.filter_search_sort_populary_desc, getString(R.string.fab_filter_search_sort_populary_desc)))
                add(FilterSearchData(Constans.filter_search_sort_release_date_asc, getString(R.string.fab_filter_search_sort_release_date_asc)))
                add(FilterSearchData(Constans.filter_search_sort_release_date_desc, getString(R.string.fab_filter_search_sort_release_date_desc)))
                add(FilterSearchData(Constans.filter_search_sort_voted_average_asc, getString(R.string.fab_filter_search_sort_voted_average_asc)))
                add(FilterSearchData(Constans.filter_search_sort_voted_average_desc, getString(R.string.fab_filter_search_voted_average_desc)))
            }
        }
    }



    companion object {


        fun newInstance(): FabFilterSearchFragment {
            return FabFilterSearchFragment()
        }
    }

}


