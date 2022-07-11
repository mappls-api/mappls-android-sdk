package com.mappls.sdk.demo.kotlin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.kotlin.fragments.MapFragment

class MapFragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_fragment)

        supportFragmentManager.beginTransaction().add(R.id.container_id, MapFragment(), MapFragment::class.java.simpleName).commit()
    }
}