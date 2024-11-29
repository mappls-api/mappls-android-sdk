package com.mappls.sdk.demo.kotlin.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.java.activity.FeedbackApiActivity
import com.mappls.sdk.demo.java.settings.MapplsFeedbackSetting
import com.mappls.sdk.demo.java.settingscreen.FeedbackSettingActivity
import com.mappls.sdk.feedback.MapplsFeedback
import com.mappls.sdk.feedback.model.FeedbackOptions

class FeedbackApiActivity : AppCompatActivity() {

    companion object{
        private  const val REQUEST_CODE: Int = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_api)
        val button = findViewById<Button>(R.id.feedback_api)
        button.setOnClickListener {
            val builder = FeedbackOptions.builder()
                .latitude(MapplsFeedbackSetting.getInstance().latitude)
                .longitude(MapplsFeedbackSetting.getInstance().longitude)
                .theme(MapplsFeedbackSetting.getInstance().theme)
                .showStepProgress(MapplsFeedbackSetting.getInstance().isEnableProgress)
                .showCompletedProgress(MapplsFeedbackSetting.getInstance().isEnableCompletedProgress)
                .build()
            val intent =
                MapplsFeedback.IntentBuilder().feedbackOptions(builder)
                    .build(this@FeedbackApiActivity)
            startActivityForResult(
                intent,
            REQUEST_CODE
            )
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.widget_setting_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.widget_setting) {
            startActivity(Intent(this, FeedbackSettingActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //Report Submit Successfully
            }
        }
    }
}