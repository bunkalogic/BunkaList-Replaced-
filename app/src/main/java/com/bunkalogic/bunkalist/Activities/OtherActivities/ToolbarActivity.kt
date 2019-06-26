package com.bunkalogic.bunkalist.Activities.OtherActivities

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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
