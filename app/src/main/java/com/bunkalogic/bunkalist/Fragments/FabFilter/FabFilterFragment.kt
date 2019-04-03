package com.bunkalogic.bunkalist.Fragments.FabFilter


import android.app.Dialog
import android.view.View
import com.allattentionhere.fabulousfilter.AAH_FabulousFragment
import com.bunkalogic.bunkalist.R
import android.widget.LinearLayout
import android.widget.RelativeLayout





class FabFilterFragment : AAH_FabulousFragment() {

    fun newInstance(): FabFilterFragment {
        return FabFilterFragment()
    }

    override fun setupDialog(dialog: Dialog?, style: Int) {
        val contentView : View  = View.inflate(context, R.layout.fragment_fab_filter, null)
        val rl_content = contentView.findViewById<View>(com.bunkalogic.bunkalist.R.id.rl_content) as RelativeLayout
        val ll_buttons = contentView.findViewById<View>(com.bunkalogic.bunkalist.R.id.ll_buttons) as LinearLayout

    }


}
