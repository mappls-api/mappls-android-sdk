package com.mappls.sdk.demo.activity.events

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.ActivityMapFragmentBinding
import com.mappls.sdk.demo.fragment.MapplsMapFragment

class MapFragmentActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMapFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityMapFragmentBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mBinding.mapFragmentHeader.headerTitle.setText(R.string.map_fragment_title)
        mBinding.mapFragmentHeader.headerBack.setOnClickListener {
            finish()
        }
        replaceFragment(MapplsMapFragment())
    }


    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = false) {

        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(mBinding.mapFragmentContainer.id, fragment, fragment.javaClass.simpleName)

        if(addToBackStack) {
            ft.addToBackStack(fragment.javaClass.simpleName)
        }
        ft.commit()
    }
}