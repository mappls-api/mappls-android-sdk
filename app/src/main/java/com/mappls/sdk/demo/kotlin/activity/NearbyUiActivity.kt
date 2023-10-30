package com.mappls.sdk.demo.kotlin.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityNearbyUiBinding
import com.mappls.sdk.nearby.plugin.MapplsNearbyWidget
import com.mappls.sdk.services.api.nearby.model.NearbyAtlasResult

class NearbyUiActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityNearbyUiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_nearby_ui)

        mBinding.btnOpenNearbyActivity.setOnClickListener {
            val intent = MapplsNearbyWidget.IntentBuilder().build(this)
            startActivityForResult(intent, 101)
        }

        mBinding.btnOpenNearbyFragmentUi.setOnClickListener {
            startActivity(Intent(this, NearbyPluginActivity::class.java))
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            val place: NearbyAtlasResult? = MapplsNearbyWidget.getNearbyResponse(data!!)
            Toast.makeText(this, place?.placeAddress, Toast.LENGTH_SHORT).show()
        }
    }
}