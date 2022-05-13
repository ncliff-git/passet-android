package com.github.ncliff.passet.presentation.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
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
import com.yandex.runtime.ui_view.ViewProvider

class SearchMapFragment : Fragment() {
    private var mapView: MapView? = null
    private var locationButton: FloatingActionButton? = null
    private var locationLongitude: Double = 0.0
    private var locationLatitude: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_search_map, container, false)
        setHasOptionsMenu(true)
        mapView = root.findViewById(R.id.search_fragment_map_view)
        locationButton = root.findViewById(R.id.location_button)
        initYandexMap()
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.map_selector_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_save_position) {
            setFragmentResult("location", bundleOf("longitude" to locationLongitude, "latitude" to locationLatitude))
            findNavController().navigateUp()
        }
        return true
    }

    private fun initYandexMap() {
        val mapLogoAlignment = Alignment(HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM)
        mapView?.map?.logo?.setAlignment(mapLogoAlignment)

        val locationPointDrawable = View(requireContext()).apply {
            background = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_location_marker)
        }

        onMapReady(locationPointDrawable)
        locationButton?.setOnClickListener {
            onMapReady(locationPointDrawable)
        }
    }

    private fun onMapReady(drawablePoint: View) {
        val locationManager = MapKitFactory.getInstance().createLocationManager()

        locationManager.requestSingleUpdate(object : LocationListener {
            override fun onLocationUpdated(location: Location) {
                mapView?.map?.move(
                    CameraPosition(location.position, 16.0f, 0.0f, 0.0f),
                    Animation(Animation.Type.SMOOTH, 1f),
                    null
                )

                locationLongitude = location.position.longitude
                locationLatitude = location.position.latitude

                mapView?.map?.mapObjects?.clear()
                mapView?.map?.mapObjects?.addPlacemark(location.position, ViewProvider(drawablePoint))
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