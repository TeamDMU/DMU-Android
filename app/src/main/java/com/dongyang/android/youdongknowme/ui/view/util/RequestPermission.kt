package com.dongyang.android.youdongknowme.ui.view.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class RequestPermission(private val activity: Activity) {
    private val permission = Manifest.permission.POST_NOTIFICATIONS

    fun isPermissionGranted(): Boolean =
        ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED

    fun requestPermission(requestCode: Int) =
        ActivityCompat.requestPermissions(
            activity, arrayOf(permission), requestCode
        )
}