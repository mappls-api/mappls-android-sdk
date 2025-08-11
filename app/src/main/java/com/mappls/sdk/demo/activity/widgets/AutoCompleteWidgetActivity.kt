package com.mappls.sdk.demo.activity.widgets

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.activity.widgets.autocomplete.CardModeActivity
import com.mappls.sdk.demo.activity.widgets.autocomplete.CardModeFragmentActivity
import com.mappls.sdk.demo.activity.widgets.autocomplete.FullModeActivity
import com.mappls.sdk.demo.activity.widgets.autocomplete.FullModeFragmentActivity
import com.mappls.sdk.demo.databinding.ActivityAutoCompleteWidgetBinding
import com.mappls.sdk.demo.settings.fragment.AutoCompleteSettingFragment
import com.mappls.sdk.demo.settings.fragment.DirectionWidgetSettingFragment

class AutoCompleteWidgetActivity : AppCompatActivity() {
    lateinit var mBinding:ActivityAutoCompleteWidgetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityAutoCompleteWidgetBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mBinding.placeAutoCompleteHeader.headerTitle.text = "AutoComplete Widget"
        mBinding.placeAutoCompleteHeader.ivSettings.visibility = View.VISIBLE
        mBinding.placeAutoCompleteHeader.ivSettings.setOnClickListener {
            val fragment = AutoCompleteSettingFragment()
            replaceFragment(fragment)
        }
        mBinding.placeAutoCompleteHeader.headerBack.setOnClickListener { finish() }
        mBinding.tvCardFragment.setOnClickListener {
            startActivity(Intent(this, CardModeFragmentActivity::class.java))
        }
        mBinding.tvFullFragment.setOnClickListener {
            startActivity(Intent(this, FullModeFragmentActivity::class.java))
        }
        mBinding.tvCardActivity.setOnClickListener {
            startActivity(Intent(this, CardModeActivity::class.java))
        }
        mBinding.tvFullActivity.setOnClickListener {
            startActivity(Intent(this, FullModeActivity::class.java))
        }

    }
    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {

        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(mBinding.placeAutocompleteFragmentContainer.id, fragment, fragment.javaClass.simpleName)

        if(addToBackStack) {
            ft.addToBackStack(fragment.javaClass.simpleName)
        }
        ft.commit()
    }

    fun popBackStack(fragment: Fragment) {
        supportFragmentManager.popBackStack(fragment.javaClass.simpleName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}