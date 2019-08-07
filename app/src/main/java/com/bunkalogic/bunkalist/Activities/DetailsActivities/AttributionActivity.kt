package com.bunkalogic.bunkalist.Activities.DetailsActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.bunkalogic.bunkalist.R

class AttributionActivity : AppCompatActivity() {

    //TODO: Agregar la atribucion a la Api, tambien las de las animaciones de Lottie Files y de Flat icons
    // traducir los string del Welcome Activity

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attribution)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.about_the_app)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
