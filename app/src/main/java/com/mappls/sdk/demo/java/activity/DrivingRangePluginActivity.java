package com.mappls.sdk.demo.java.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mappls.sdk.demo.databinding.ActivityDrivingRangePluginBinding;
import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.java.settingscreen.DrivingRangeSettingActivity;

public class DrivingRangePluginActivity extends AppCompatActivity {

    private ActivityDrivingRangePluginBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_driving_range_plugin);
        mBinding.btnOpenDrivingRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DrivingRangePluginActivity.this, DrivingRangeActivity.class));
            }
        });
        mBinding.btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DrivingRangePluginActivity.this, DrivingRangeSettingActivity.class));
            }
        });
    }
}