package com.mappls.sdk.demo.kotlin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityNearbyPluginBinding
import com.mappls.sdk.nearby.plugin.IMapplsNearbyCallback
import com.mappls.sdk.nearby.plugin.MapplsNearbyFragment
import com.mappls.sdk.services.api.nearby.model.NearbyAtlasResult

class NearbyPluginActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityNearbyPluginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_nearby_plugin)
        val nearbyFragment = MapplsNearbyFragment.newInstance()
        supportFragmentManager.beginTransaction().add(mBinding.fragmentContainer.id, nearbyFragment, MapplsNearbyFragment::class.java.simpleName)
                .commit()

        nearbyFragment.setMapplsNearbyCallback(object : IMapplsNearbyCallback {
            override fun getNearbyCallback(nearbyAtlasResult: NearbyAtlasResult) {
                Toast.makeText(this@NearbyPluginActivity, nearbyAtlasResult.placeAddress, Toast.LENGTH_SHORT).show()
            }
        })
    }
}