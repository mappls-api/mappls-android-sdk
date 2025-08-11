package com.mappls.sdk.demo.activity.widgets

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityNearbyWidgetBinding
import com.mappls.sdk.nearby.plugin.MapplsNearbyFragment
import com.mappls.sdk.nearby.plugin.MapplsNearbyWidget
import com.mappls.sdk.services.api.nearby.model.NearbyAtlasResult


class NearbyWidgetActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityNearbyWidgetBinding

    private val startActivityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val place: NearbyAtlasResult? = MapplsNearbyWidget.getNearbyResponse(it.data!!)
            Toast.makeText(this, place?.placeAddress, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityNearbyWidgetBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mBinding.nearbyUiHeader.headerTitle.text = "Nearby Widget"
        mBinding.nearbyUiHeader.headerBack.setOnClickListener {
            finish()
        }
        mBinding.nearbyUiHeader.ivSettings.visibility = View.GONE

        mBinding.tvNearbyActivity.setOnClickListener {
            val intent = MapplsNearbyWidget.IntentBuilder()
                .build(this)
            startActivityForResult.launch(intent)
        }
        mBinding.tvNearbyFragment.setOnClickListener {
            val nearbyFragment = MapplsNearbyFragment.newInstance()
            supportFragmentManager.beginTransaction().replace(mBinding.fragmentContainer.getId(), nearbyFragment, MapplsNearbyFragment::class.java.getSimpleName())
            .commit()
        }

    }
}