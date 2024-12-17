package com.mappls.sdk.demo.java.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.mappls.sdk.demo.databinding.ActivityMapFragmentBinding;
import com.mappls.sdk.demo.java.fragments.MapFragment;

public class MapFragmentActivity extends AppCompatActivity {

    private ActivityMapFragmentBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMapFragmentBinding.inflate(getLayoutInflater());

        setContentView(mBinding.getRoot());
        getSupportFragmentManager().beginTransaction()
                .add(mBinding.containerId.getId(), new MapFragment(), MapFragment.class.getSimpleName())
                .commit();
    }
}