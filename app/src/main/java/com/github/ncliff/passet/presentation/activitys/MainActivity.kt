package com.github.ncliff.passet.presentation.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.github.ncliff.passet.R
import com.github.ncliff.passet.data.DataUtils
import com.yandex.mapkit.MapKitFactory
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initYandexMap()
        setContentView(R.layout.activity_main)
        NavigationUI.setupActionBarWithNavController(this, this.findNavController(R.id.fragmentContainerView))
    }

    private fun initYandexMap() {
        MapKitFactory.setApiKey(DataUtils.YANDEX_MAP_API_KEY)
        MapKitFactory.initialize(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        return this.findNavController(R.id.fragmentContainerView).navigateUp()
    }
}