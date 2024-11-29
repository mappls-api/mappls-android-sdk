package com.mappls.sdk.demo.java.settingscreen;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.databinding.ActivityFeedbackSettingBinding;
import com.mappls.sdk.demo.java.settings.MapplsDirectionWidgetSetting;
import com.mappls.sdk.demo.java.settings.MapplsFeedbackSetting;
import com.mappls.sdk.feedback.model.FeedbackOptions;
import com.mappls.sdk.services.api.directions.DirectionsCriteria;

import java.util.Locale;

public class FeedbackSettingActivity extends AppCompatActivity {

    private ActivityFeedbackSettingBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_feedback_setting);
        initSettings();
        initCallback();
    }

    private void initCallback() {
        Double latitude = MapplsFeedbackSetting.getInstance().getLatitude();
        Double longitude = MapplsFeedbackSetting.getInstance().getLongitude();

        if (latitude != null && longitude != null) {
            mBinding.etLatitude.setText(String.format(Locale.getDefault(), "%.6f", latitude));
            mBinding.etLongitude.setText(String.format(Locale.getDefault(), "%.6f", longitude));
        } else {
            // Handle null values (e.g., set default text or show an error message)
            mBinding.etLatitude.setText("");
            mBinding.etLongitude.setText("");
            Log.e("MapplsFeedback", "Latitude or Longitude is null");
        }
        mBinding.cbProgress.setChecked(MapplsFeedbackSetting.getInstance().isEnableProgress());
        mBinding.cbCompleteProgress.setChecked(MapplsFeedbackSetting.getInstance().isEnableCompletedProgress());
        mBinding.rgTheme.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_day) {
                MapplsFeedbackSetting.getInstance().setTheme(FeedbackOptions.THEME_DAY);
            } else if (checkedId == R.id.rb_night) {
                MapplsFeedbackSetting.getInstance().setTheme(FeedbackOptions.THEME_NIGHT);
            }
        });
    }

    private void initSettings() {
        mBinding.btnSaveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String latitudeText = mBinding.etLatitude.getText().toString();
                String longitudeText = mBinding.etLongitude.getText().toString();

                try {
                    double latitude = Double.parseDouble(latitudeText);
                    double longitude = Double.parseDouble(longitudeText);
                    MapplsFeedbackSetting.getInstance().setLatitude(latitude);
                    MapplsFeedbackSetting.getInstance().setLongitude(longitude);
                } catch (NumberFormatException e) {
                    // Handle invalid input (e.g., show an error message)
                    Log.e("MapplsFeedback", "Invalid latitude input: " + latitudeText);
                }


            }
        });
        mBinding.cbProgress.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MapplsFeedbackSetting.getInstance().setEnableProgress(isChecked);
        });
        mBinding.cbCompleteProgress.setOnCheckedChangeListener((buttonView, isChecked) -> {
            MapplsFeedbackSetting.getInstance().setEnableCompletedProgress(isChecked);
        });
        if (MapplsFeedbackSetting.getInstance().getTheme() == FeedbackOptions.THEME_DAY) {
            mBinding.rgTheme.check(mBinding.rbDay.getId());
        } else if (MapplsFeedbackSetting.getInstance().getTheme() == FeedbackOptions.THEME_NIGHT) {
            mBinding.rgTheme.check(mBinding.rbNight.getId());
        }

    }
}