package com.edwinyosua.ceritaapp.ui.activity.map

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.edwinyosua.ceritaapp.R
import com.edwinyosua.ceritaapp.databinding.ActivityMapsBinding
import com.edwinyosua.ceritaapp.network.ApiResult
import com.edwinyosua.ceritaapp.ui.ViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val mapViewModel: MapViewModel by viewModels { factory }
    private val boundsBuilder = LatLngBounds.Builder()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mapViewModel.getStoriesWithLocation()
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        setMapStyle()

        mMap.uiSettings.isZoomControlsEnabled = true

        mapViewModel.apiResponse.observe(this@MapsActivity) { response ->
            when (response) {
                is ApiResult.ApiSuccess -> {
                    response.data.forEach { stories ->
                        val latLngLocation = LatLng(stories.lat, stories.lon)
                        mMap.addMarker(
                            MarkerOptions()
                                .position(latLngLocation)
                                .title(stories.name)
                                .snippet(stories.description)
                                .icon(
                                    vectorToBitmap(
                                        R.drawable.person_pin_40dp_fill0_wght400_grad0_opsz40,
                                        this@MapsActivity
                                    )
                                )
                        )
                        boundsBuilder.include(latLngLocation)
                        mMap.animateCamera(
                            CameraUpdateFactory.newLatLngBounds(
                                boundsBuilder.build(),
                                resources.displayMetrics.widthPixels,
                                resources.displayMetrics.heightPixels,
                                300
                            )
                        )
                    }
                }

                is ApiResult.ApiError -> {
                    showToast(response.error)
                }

                ApiResult.Loading -> {
                    Log.i("MapsActivity", "Loading")
                    showToast("Loading...")
                }
            }
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_map_options, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.normal_type -> {
//                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
//                true
//            }
//
//            R.id.satellite_type -> {
//                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
//                true
//            }
//
//            R.id.terrain_type -> {
//                mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
//                true
//            }
//
//            R.id.hybrid_type -> {
//                mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
//                true
//            }
//
//            else -> {
//                super.onOptionsItemSelected(item)
//            }
//        }
//    }

    private fun setMapStyle() {
        try {
            val success =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            if (!success) {
                Log.e("MapsActivity", "Style parsing failed.")
            }

        } catch (exception: Resources.NotFoundException) {
            exception.printStackTrace()
            Log.e("MapsActivity", "Style Not Found, Error : ", exception)
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun vectorToBitmap(@DrawableRes id: Int, context: AppCompatActivity): BitmapDescriptor {
        val vectorDrawable = ResourcesCompat.getDrawable(context.resources, id, null)
        if (vectorDrawable == null) {
            Log.e("BitmapHelper", "Resource not found")
            return BitmapDescriptorFactory.defaultMarker()
        }
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}