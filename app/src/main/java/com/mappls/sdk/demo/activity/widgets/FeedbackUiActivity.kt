package com.mappls.sdk.demo.activity.widgets

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mappls.sdk.demo.activity.widgets.feedbackUi.FeedbackWidgetActivity
import com.mappls.sdk.demo.databinding.ActivityFeedbackUiBinding

class FeedbackUiActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityFeedbackUiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityFeedbackUiBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mBinding.feedbackUiHeader.headerTitle.text = "Feedback Widget"
        mBinding.feedbackUiHeader.headerBack.setOnClickListener {
            finish()
        }

        mBinding.tvFeedbackWidget.setOnClickListener {
            startActivity(Intent(this, FeedbackWidgetActivity::class.java))
        }
    }
}