package com.mappls.sdk.demo.activity.utility

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityDigipinBinding
import com.mappls.sdk.demo.utils.Utils
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.services.utils.DigipinUtility


class DigipinActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var mBinding:ActivityDigipinBinding
    private lateinit var bottomSheetBehaviour: BottomSheetBehavior<ConstraintLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        mBinding= ActivityDigipinBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val ime = insets.getInsets(WindowInsetsCompat.Type.ime())
            val bottomInset = maxOf(systemBars.bottom, ime.bottom)
            view.setPadding(
                systemBars.left, systemBars.top, systemBars.right,
                bottomInset
            )
            insets
        }
        mBinding.topHeader.headerTitle.text = "Get Digipin"
        mBinding.topHeader.headerBack.setOnClickListener {
            finish()
        }

        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
        bottomSheetBehaviour = BottomSheetBehavior.from(mBinding.modeBottomSheet)
        bottomSheetBehaviour.isHideable = true
        bottomSheetBehaviour.isDraggable = false
        bottomSheetBehaviour.state = BottomSheetBehavior.STATE_HIDDEN
        mBinding.rbLatLng.isChecked = true
        if(mBinding.rbLatLng.isChecked){
            mBinding.etCustomLng.visibility = View.VISIBLE
            mBinding.etCustomLat.hint = "Latitude"
            mBinding.etCustomLat.inputType =  InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        }else{
            mBinding.etCustomLng.visibility = View.GONE
            mBinding.etCustomLat.setHint("Digipin")
            mBinding.etCustomLat.inputType = InputType.TYPE_CLASS_TEXT
        }
        mBinding.rgSelectOption.setOnCheckedChangeListener { _, checkId ->
            when(checkId) {
                    mBinding.rbDigipin.id -> {
                        mBinding.etCustomLng.visibility = View.GONE
                        mBinding.etCustomLat.text?.clear()
                        mBinding.etCustomLat.setHint("Digipin")
                        mBinding.etCustomLat.inputType = InputType.TYPE_CLASS_TEXT
                    }
                    mBinding.rbLatLng.id -> {
                        mBinding.etCustomLng.visibility = View.VISIBLE
                        mBinding.etCustomLat.text?.clear()
                        mBinding.etCustomLat.hint = "Latitude"
                        mBinding.etCustomLat.inputType =  InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                    }


                    else -> null
                }
        }

        mBinding.btnCustomLocation.setOnClickListener {
            if (mBinding.rbLatLng.isChecked) {
                val lat = mBinding.etCustomLat.text.toString().trim()
                val lng = mBinding.etCustomLng.text.toString().trim()

                if (lat.isEmpty() || lng.isEmpty()) {
                    Toast.makeText(this, "Please enter both latitude and longitude", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (Utils.validateLatitude(lat) && Utils.validateLongitude(lng)) {
                    val digipin = DigipinUtility.getDigipinFromCoordinate(
                        Point.fromLngLat(
                            lng.toDouble(),
                            lat.toDouble()
                        )
                    )
                    mBinding.tvBottomSheetTitle.text = "Digipin : $digipin"
                    bottomSheetBehaviour.peekHeight = 500
                    bottomSheetBehaviour.state = BottomSheetBehavior.STATE_EXPANDED
                } else {
                    Toast.makeText(this, "Invalid latitude or longitude", Toast.LENGTH_SHORT).show()
                }

            } else {
                val digipinText = mBinding.etCustomLat.text.toString().trim()

                if (digipinText.isEmpty()) {
                    Toast.makeText(this, "Please enter a Digipin", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                bottomSheetBehaviour.peekHeight = 500
                val digipin = DigipinUtility.getCoordinateFromDigipin(digipinText)
                if(digipin != null) {
                    mBinding.tvBottomSheetTitle.text = "Latitude : ${"%.4f".format(digipin.latitude())}\t\tLongitude: ${"%.4f".format(digipin.longitude())}"
                    bottomSheetBehaviour.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }


    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        mapplsMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    22.553147478403194,
                    77.23388671875
                ), 4.0
            )
        )
    }

    override fun onMapError(p0: Int, p1: String?) {

    }
    override fun onStart() {
        super.onStart()
        mBinding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mBinding.mapView.onSaveInstanceState(outState)
    }

    override fun onPause() {
        super.onPause()
        mBinding.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mBinding.mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.mapView.onDestroy()
    }
}