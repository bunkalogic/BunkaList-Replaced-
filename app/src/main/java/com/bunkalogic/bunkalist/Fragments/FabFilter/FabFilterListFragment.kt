package com.bunkalogic.bunkalist.Fragments.FabFilter


import android.app.Dialog
import android.support.design.widget.TabLayout
import android.view.View
import android.widget.ImageView
import com.allattentionhere.fabulousfilter.AAH_FabulousFragment
import com.bunkalogic.bunkalist.R
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.bunkalogic.bunkalist.Adapters.ProfileListFabFilterAdapter
import kotlinx.android.synthetic.main.fragment_fab_filter.view.*


class FabFilterListFragment : AAH_FabulousFragment() {

    lateinit var contentView : View

    fun newInstance(): FabFilterListFragment {
        return FabFilterListFragment()
    }

    override fun setupDialog(dialog: Dialog?, style: Int) {
        contentView = View.inflate(context, R.layout.fragment_fab_filter, null)
        val rl_content = contentView.findViewById<View>(R.id.rl_content) as RelativeLayout
        val ll_buttons = contentView.findViewById<View>(R.id.ll_buttons) as LinearLayout
        contentView.findViewById<ImageView>(R.id.fabFilterClose).setOnClickListener {
            closeFilter("closed")
        }
        setViewgroupStatic(ll_buttons)
        setViewMain(rl_content)
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





}
