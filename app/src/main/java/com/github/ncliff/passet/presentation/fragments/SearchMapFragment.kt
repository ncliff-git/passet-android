package com.github.ncliff.passet.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.github.ncliff.passet.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.logo.Alignment
import com.yandex.mapkit.logo.HorizontalAlignment
import com.yandex.mapkit.logo.VerticalAlignment
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView

class SearchMapFragment : Fragment() {
    private var mapView: MapView? = null
    private var locationButton: FloatingActionButton? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_search_map, container, false)
        mapView = root.findViewById(R.id.search_fragment_map_view)
        locationButton = root.findViewById(R.id.location_button)
        initYandexMap()
        return root
    }

    private fun initYandexMap() {
        val mapLogoAlignment = Alignment(HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM)
        mapView?.map?.logo?.setAlignment(mapLogoAlignment)

        onMapReady()
        locationButton?.setOnClickListener {
            onMapReady()
        }
    }

    private fun onMapReady() {
        val mapKit = MapKitFactory.getInstance()

        mapKit.createLocationManager().requestSingleUpdate(object : LocationListener {
            override fun onLocationUpdated(location: Location) {
                mapView?.map?.move(
                    CameraPosition(location.position, 16.0f, 0.0f, 0.0f),
                    Animation(Animation.Type.SMOOTH, 1f),
                    null
                )
            }

            override fun onLocationStatusUpdated(p0: LocationStatus) {}
        })
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        mapView?.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onDestroy() {
        mapView = null
        super.onDestroy()
    }
}