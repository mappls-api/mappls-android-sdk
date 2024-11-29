package com.mappls.sdk.demo.kotlin.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.java.settings.MapplsFeedbackSetting
import com.mappls.sdk.feedback.callback.FeedbackCallback
import com.mappls.sdk.feedback.model.FeedbackOptions
import com.mappls.sdk.feedback.ui.FeedbackFragment

class FeedbackWidgetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        val feedbackOption = FeedbackOptions.builder().latitude(MapplsFeedbackSetting.getInstance().latitude)
            .longitude(MapplsFeedbackSetting.getInstance().longitude)
            .theme(MapplsFeedbackSetting.getInstance().theme)
            .showStepProgress(MapplsFeedbackSetting.getInstance().isEnableProgress)
            .showCompletedProgress(MapplsFeedbackSetting.getInstance().isEnableCompletedProgress)
            .build()
        val feedbackFragment = FeedbackFragment.newInstance(feedbackOption)
        feedbackFragment.setFeedbackCallback(object : FeedbackCallback{
            override fun onCancel() {
                // On Submitted Report Succssfully
            }

            override fun onSubmittedReport() {
                //on click of Cancel button
            }

        })
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, feedbackFragment, FeedbackFragment::class.java.simpleName)
            .addToBackStack(FeedbackFragment::class.java.simpleName)
            .commit()

    }
    override fun onBackPressed() {
        val feedbackFragment = supportFragmentManager
            .findFragmentByTag(FeedbackFragment::class.java.simpleName) as FeedbackFragment?

        if (feedbackFragment != null && feedbackFragment.isVisible) {
            supportFragmentManager.beginTransaction()
                .remove(feedbackFragment) // Remove the fragment
                .commit()
            finish()
        } else {
            super.onBackPressed() // Default back behavior
        }
    }
}