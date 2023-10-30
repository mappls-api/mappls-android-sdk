package com.mappls.sdk.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.mappls.sdk.demo.databinding.ActivityCredentialBinding
import com.mappls.sdk.maps.Mappls
import com.mappls.sdk.services.account.MapplsAccountManager


class CredentialActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityCredentialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_credential)
        supportActionBar?.hide()
        Mappls.getInstance(this)
        mBinding.submitBtn.setOnClickListener {


            if (TextUtils.isEmpty(mBinding.mapsdkKey.text.toString())
                || TextUtils.isEmpty(mBinding.restAPIKey.text.toString())
                || TextUtils.isEmpty(mBinding.clientId.text.toString())
                || TextUtils.isEmpty(mBinding.clientSecret.text.toString())) {
                Toast.makeText(this,"Please enter your mapSdkKey or restAPIKey or clientId or clientSecret",
                    Toast.LENGTH_LONG).show()

            } else {
                MapplsAccountManager.getInstance().restAPIKey = mBinding.restAPIKey.text.toString().trim()
                MapplsAccountManager.getInstance().mapSDKKey = mBinding.mapsdkKey.text.toString().trim()
                MapplsAccountManager.getInstance().atlasClientId = mBinding.clientId.text.toString().trim()
                MapplsAccountManager.getInstance().atlasClientSecret = mBinding.clientSecret.text.toString().trim()
                Mappls.getInstance(this)
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
        }

    }
}