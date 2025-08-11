package com.mappls.sdk.demo.activity.widgets

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.activity.widgets.geofence.BasicGeoFenceActivity
import com.mappls.sdk.demo.activity.widgets.geofence.GeoFenceDetailActivity
import com.mappls.sdk.demo.databinding.ActivityGeoFenceBinding
import com.mappls.sdk.demo.settings.fragment.MapplsGeofenceSettingFragment

class GeoFenceActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityGeoFenceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityGeoFenceBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mBinding.geoFenceHeader.headerTitle.text = "GeoFence Widget"
        mBinding.geoFenceHeader.headerBack.setOnClickListener {
            finish()
        }
        mBinding.geoFenceHeader.ivSettings.visibility = View.VISIBLE
        mBinding.geoFenceHeader.ivSettings.setOnClickListener {
            val fragment = MapplsGeofenceSettingFragment()
            replaceFragment(fragment)
        }
        mBinding.tvBasicGeofence.setOnClickListener {
            startActivity(Intent(this, BasicGeoFenceActivity::class.java))
        }

        mBinding.tvCustomGeofence.setOnClickListener {
            startActivity(Intent(this, GeoFenceDetailActivity::class.java))
        }
    }

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {

        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(mBinding.fragmentContainer.id, fragment, fragment.javaClass.simpleName)

        if(addToBackStack) {
            ft.addToBackStack(fragment.javaClass.simpleName)
        }
        ft.commit()
    }

    fun popBackStack(fragment: Fragment) {
        supportFragmentManager.popBackStack(fragment.javaClass.simpleName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}