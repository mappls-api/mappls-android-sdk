package com.mappls.sdk.demo.java.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.mappls.sdk.demo.databinding.ActivityNearbyUiBinding;
import com.mappls.sdk.demo.R;
import com.mappls.sdk.nearby.plugin.MapplsNearbyWidget;
import com.mappls.sdk.services.api.nearby.model.NearbyAtlasResult;

public class NearbyUiActivity extends AppCompatActivity {

    private ActivityNearbyUiBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_nearby_ui);
        mBinding.btnOpenNearbyActivity.setOnClickListener(v -> {
            Intent intent = new MapplsNearbyWidget.IntentBuilder().build(this);
            startActivityForResult(intent, 101);
        });

        mBinding.btnOpenNearbyFragmentUi.setOnClickListener( view -> {
            startActivity(new Intent(this, NearbyPluginActivity.class));
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            NearbyAtlasResult result = MapplsNearbyWidget.getNearbyResponse(data);
            Toast.makeText(this, result.placeAddress, Toast.LENGTH_SHORT).show();
        }
    }
}