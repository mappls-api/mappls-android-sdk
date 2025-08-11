package com.mappls.sdk.demo.activity.events

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.adapter.StyleAdapter
import com.mappls.sdk.demo.databinding.ActivityStyleBinding
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.MapplsMapConfiguration
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.camera.CameraUpdateFactory
import com.mappls.sdk.maps.geometry.LatLng

class StyleActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mBinding: ActivityStyleBinding
    private var mMapplsMap: MapplsMap? = null

    private val onScrollChangeListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val firstVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager)
                .findFirstVisibleItemPosition()
            updateDot(firstVisibleItemPosition)
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityStyleBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mBinding.mapStyleHeader.headerTitle.setText(R.string.map_style_title)
        mBinding.mapStyleHeader.headerBack.setOnClickListener {
            finish()
        }

        mBinding.rvStyleList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(mBinding.rvStyleList)

        mBinding.mapView.onCreate(savedInstanceState)
        mBinding.mapView.getMapAsync(this)
        mBinding.rvStyleList.addOnScrollListener(onScrollChangeListener)
        mBinding.swSaveSelectedLastStyle.isChecked = MapplsMapConfiguration.getInstance().isShowLastSelectedStyle
        mBinding.swSaveSelectedLastStyle.setOnCheckedChangeListener { cb, isChecked ->
            MapplsMapConfiguration.getInstance().isShowLastSelectedStyle = isChecked
        }
    }



    private fun updateDot(position: Int) {
        val size = mBinding.dotsIndicator.childCount
        for(i in 0 until size) {
            val view = mBinding.dotsIndicator.getChildAt(i) as ImageView
            if(i == position) {
                view.setImageResource(R.drawable.iv_style_item_selected)
            } else {
                view.setImageResource(R.drawable.iv_style_un_selected)
            }
        }

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
        mBinding.rvStyleList.removeOnScrollListener(onScrollChangeListener)
        mBinding.mapView.onDestroy()
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        this.mMapplsMap = mapplsMap
        mapplsMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(
            22.553147478403194,
            77.23388671875), 4.0))
        createDots(mapplsMap.mapplsAvailableStyles.size)
        val adapter = StyleAdapter(mapplsMap.mapplsAvailableStyles) {
            mMapplsMap?.setMapplsStyle(it.name) {
                Toast.makeText(this, "Style Loaded Successfully", Toast.LENGTH_SHORT).show()
            }
        }
        mBinding.rvStyleList.adapter = adapter
    }

    private fun createDots(total: Int) {
        mBinding.dotsIndicator.removeAllViews()
        for(i in 0 until total) {
            val dot = ImageView(this)
            dot.setImageResource(R.drawable.iv_style_un_selected)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(5, 0, 5, 0) // Add margin between dots
            }

            dot.layoutParams = params
            mBinding.dotsIndicator.addView(dot)
        }
    }

    override fun onMapError(code: Int, message: String?) {
        Toast.makeText(this, "$code --- $message", Toast.LENGTH_SHORT).show()
    }
}