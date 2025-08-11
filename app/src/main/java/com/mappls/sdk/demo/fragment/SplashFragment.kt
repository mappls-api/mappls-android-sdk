package com.mappls.sdk.demo.fragment

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.mappls.sdk.demo.MainActivity
import com.mappls.sdk.demo.R
import com.mappls.sdk.demo.databinding.FragmentSplashBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private lateinit var mBinding: FragmentSplashBinding
    private var permissionDialog: Dialog? = null
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { isGrantedMap ->
            permissionDialog?.cancel()
            if(isGrantedMap.get(Manifest.permission.ACCESS_COARSE_LOCATION) == true || isGrantedMap.get(Manifest.permission.ACCESS_FINE_LOCATION) == true) {
                redirect()
            } else {
                requestPermissions()
            }
        }

    private val settingsContent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_CANCELED) {
                requestPermissions()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentSplashBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermissions()
    }

    private fun redirect() {
        lifecycleScope.launch {
            delay(800)
            (requireActivity() as MainActivity).replaceFragment(TypeSelectionFragment(), false)        }
//            (requireActivity() as MainActivity).replaceFragment(CredentialFragment(), false)        }
    }

    private fun requestPermissions() {
        permissionDialog?.cancel()
        when {
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                redirect()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) &&
            ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) -> {
                showPermissionDialog()
            }
            else -> {
                requestPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
            }
        }
    }

    private fun showPermissionDialog() {
        permissionDialog = AlertDialog.Builder(requireContext())
            .setTitle("Permission Settings")
            .setMessage("Please enable Permissions from the Setting")
            .setCancelable(false)
            .setPositiveButton("Settings") { _, _ -> openAppSettings() }
            .create()

        permissionDialog?.setCanceledOnTouchOutside(false)
        if (permissionDialog?.isShowing != true) {
            permissionDialog?.show()
        }
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", requireContext().packageName, null)
        intent.data = uri
        settingsContent.launch(intent)
    }

}