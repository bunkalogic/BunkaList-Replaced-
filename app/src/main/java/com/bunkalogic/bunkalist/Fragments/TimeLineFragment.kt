package com.bunkalogic.bunkalist.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bunkalogic.bunkalist.R


/**
 *  Created by @author Naim Dridi on 25/02/19
 */


class TimeLineFragment : Fragment() {

    private lateinit var _view: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _view = inflater.inflate(R.layout.fragment_timelist, container, false)

        return _view
    }


}
