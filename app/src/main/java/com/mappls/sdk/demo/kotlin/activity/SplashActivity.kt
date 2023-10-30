package com.mappls.sdk.demo.kotlin.activity

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import com.mappls.sdk.demo.CredentialActivity
import com.mappls.sdk.demo.MainActivity
import com.mappls.sdk.demo.R

/**
 * Created by CEINFO on 26-02-2019.
 */
class SplashActivity : AppCompatActivity() {

    private val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        //  streamExample()
        requestPermissions()
    }


    private fun redirect() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            startActivity(Intent(applicationContext, CredentialActivity::class.java))
            finish()
        }, 500)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var permissionGranted = true

        if (grantResults.isNotEmpty()) {
            for (i in grantResults.indices) {
                if (grantResults[i] != PERMISSION_GRANTED) {
                    permissionGranted = false
                    break
                }
            }
        } else {
            //PERMISSION REQ
            permissionGranted = false
        }

        if (!permissionGranted) {
            AlertDialog.Builder(this).setTitle("Please grant all the permissions to continue. \nYou can go to phone's settings >> Applications >> Orrder Driver and manually grant the permissions.")
                    .setPositiveButton("OK") { dialog, which -> requestPermissions() }.show()
        } else {
            redirect()
        }
    }

    internal fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, PERMISSIONS_REQUIRED[0]) == PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, PERMISSIONS_REQUIRED[1]) == PERMISSION_GRANTED) {

            redirect()

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISSIONS_REQUIRED[0]) || ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISSIONS_REQUIRED[1])) {
                //showPermissionRequiredDialog
                AlertDialog.Builder(this).setTitle("Please Accept all the permissions.")
                        .setPositiveButton("OK") { dialog, which -> ActivityCompat.requestPermissions(this@SplashActivity, PERMISSIONS_REQUIRED, 100) }.show()
            } else {
                //askPermission
                ActivityCompat.requestPermissions(this, PERMISSIONS_REQUIRED, 100)
            }
        }

    }
}