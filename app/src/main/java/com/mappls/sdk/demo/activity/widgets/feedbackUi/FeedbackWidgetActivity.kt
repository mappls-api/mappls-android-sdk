package com.mappls.sdk.demo.activity.widgets.feedbackUi

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityFeedbackUiBinding
import com.mappls.sdk.demo.databinding.ActivityFeedbackWidgetBinding
import com.mappls.sdk.feedback.callback.FeedbackCallback
import com.mappls.sdk.feedback.model.FeedbackOptions
import com.mappls.sdk.feedback.ui.FeedbackFragment

class FeedbackWidgetActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityFeedbackWidgetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityFeedbackWidgetBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val feedbackOption = FeedbackOptions.builder()
            .latitude(27.0)
            .longitude(78.0)
//            .theme(MapplsFeedbackSetting.getInstance().theme)
//            .showStepProgress(MapplsFeedbackSetting.getInstance().isEnableProgress)
//            .showCompletedProgress(MapplsFeedbackSetting.getInstance().isEnableCompletedProgress)
            .build()
        val feedbackFragment = FeedbackFragment.newInstance(feedbackOption)
        feedbackFragment.setFeedbackCallback(object : FeedbackCallback{
            override fun onCancel() {
                popBackStack()
            }

            override fun onSubmittedReport() {
                popBackStack()
                Toast.makeText(this@FeedbackWidgetActivity, "Submit Report Successfully", Toast.LENGTH_SHORT).show()
            }

        })
        replaceFragment(feedbackFragment)
    }

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = false) {

        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.add(mBinding.fragmentContainer.id, fragment, fragment.javaClass.simpleName)

        if(addToBackStack) {
            ft.addToBackStack(fragment.javaClass.simpleName)
        }
        ft.commit()
    }
    fun popBackStack(fragment: Fragment) {
        supportFragmentManager.popBackStack(fragment.javaClass.simpleName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
    fun popBackStack() {
        supportFragmentManager.popBackStack()
        finish()
    }
}