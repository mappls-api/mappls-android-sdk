package com.mappls.sdk.demo.java.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.Toast;

import com.mappls.sdk.demo.databinding.ActivityNearbyPluginBinding;
import com.mappls.sdk.demo.R;
import com.mappls.sdk.nearby.plugin.MapplsNearbyFragment;

public class NearbyPluginActivity extends AppCompatActivity {

    private ActivityNearbyPluginBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_nearby_plugin);

        MapplsNearbyFragment nearbyFragment = MapplsNearbyFragment.newInstance();

        getSupportFragmentManager().
                beginTransaction().
                replace(mBinding.fragmentContainer.getId(), nearbyFragment, MapplsNearbyFragment.class.getSimpleName())
                .commit();


        nearbyFragment.setMapplsNearbyCallback(nearbyAtlasResult -> {
            // getSupportFragmentManager().beginTransaction().remove(nearbyFragment).commit();

            Toast.makeText(NearbyPluginActivity.this, nearbyAtlasResult.placeAddress, Toast.LENGTH_SHORT).show();
        });
    }
}