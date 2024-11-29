package com.mappls.sdk.demo.java.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.java.settings.MapplsFeedbackSetting;
import com.mappls.sdk.demo.java.settingscreen.FeedbackSettingActivity;
import com.mappls.sdk.feedback.MapplsFeedback;
import com.mappls.sdk.feedback.model.FeedbackOptions;

public class FeedbackApiActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_api);
        Button button = findViewById(R.id.feedback_api);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedbackOptions builder = FeedbackOptions.builder()
                        .latitude(MapplsFeedbackSetting.getInstance().getLatitude())
                        .longitude(MapplsFeedbackSetting.getInstance().getLongitude())
                        .theme(MapplsFeedbackSetting.getInstance().getTheme())
                        .showStepProgress(MapplsFeedbackSetting.getInstance().isEnableProgress())
                        .showCompletedProgress(MapplsFeedbackSetting.getInstance().isEnableCompletedProgress())
                        .build();
                Intent intent = new MapplsFeedback.IntentBuilder().feedbackOptions(builder)
                        .build(FeedbackApiActivity.this);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.widget_setting_menu, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.widget_setting) {
            startActivity(new Intent(this, FeedbackSettingActivity.class));
        }
        return super.onOptionsItemSelected(item);

    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                //Report Submit Successfully
            }
        }
    }
}