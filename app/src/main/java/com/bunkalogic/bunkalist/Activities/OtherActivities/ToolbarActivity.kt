package com.bunkalogic.bunkalist.Activities.OtherActivities

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.bunkalogic.bunkalist.Interfaces.iToolbar

open class ToolbarActivity : AppCompatActivity(), iToolbar {


    protected var _toolbar: Toolbar? = null

    override fun toolbarToLoad(toolbar: Toolbar?) {
        _toolbar = toolbar
        _toolbar?.let { setSupportActionBar(_toolbar) }
    }

    override fun enableHomeDisplay(values: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(values)
    }
}
