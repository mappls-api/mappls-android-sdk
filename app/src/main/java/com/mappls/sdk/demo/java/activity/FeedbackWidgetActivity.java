package com.mappls.sdk.demo.java.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.java.settings.MapplsFeedbackSetting;
import com.mappls.sdk.demo.java.settingscreen.PlacePickerSettingsActivity;
import com.mappls.sdk.feedback.callback.FeedbackCallback;
import com.mappls.sdk.feedback.model.FeedbackOptions;
import com.mappls.sdk.feedback.ui.FeedbackFragment;

public class FeedbackWidgetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        FeedbackOptions feedbackOption = FeedbackOptions.builder().latitude(MapplsFeedbackSetting.getInstance().getLatitude())
                .longitude(MapplsFeedbackSetting.getInstance().getLongitude())
                .theme(MapplsFeedbackSetting.getInstance().getTheme())
                .showStepProgress(MapplsFeedbackSetting.getInstance().isEnableProgress())
                .showCompletedProgress(MapplsFeedbackSetting.getInstance().isEnableCompletedProgress())
                .build();
        FeedbackFragment feedbackFragment = FeedbackFragment.newInstance(feedbackOption);
        feedbackFragment.setFeedbackCallback(new FeedbackCallback() {
            @Override
            public void onSubmittedReport() {
                // On Submitted Report Succssfully
            }

            @Override
            public void onCancel() {
                finish();
            }
        });
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, feedbackFragment, FeedbackFragment.class.getSimpleName())
                .addToBackStack(FeedbackFragment.class.getSimpleName())
                .commit();


    }

}