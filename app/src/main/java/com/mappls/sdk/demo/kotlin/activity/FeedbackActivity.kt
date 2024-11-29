package com.mappls.sdk.demo.kotlin.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.java.settingscreen.FeedbackSettingActivity


class FeedbackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_ui)
        val button = findViewById<Button>(R.id.feedback)
        button.setOnClickListener {
            startActivity(
                Intent(
                    baseContext,
                    FeedbackWidgetActivity::class.java
                )
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
}