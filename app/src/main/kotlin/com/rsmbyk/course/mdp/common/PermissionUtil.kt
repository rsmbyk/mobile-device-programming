package com.rsmbyk.course.mdp.common

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import java.util.*

class PermissionUtil (private val activity: Activity) {

    private val requestQueue = LinkedList<Request> ()

    fun requestPermission (
            permissions: Array<String>,
            onGranted: (requestCode: Int) -> Unit,
            onRejected: ((requestCode: Int) -> Unit)? = null,
            requestCode: Int? = null): Int {
        val finalRequestCode = requestCode ?: Random ().nextInt ()
        requestQueue.push (Request (activity, permissions, finalRequestCode, onGranted, onRejected))
        processNextRequest ()
        return finalRequestCode
    }

    fun requestPermission (
            permission: String,
            onGranted: (requestCode: Int) -> Unit,
            onRejected: ((requestCode: Int) -> Unit)? = null,
            requestCode: Int? = null): Int =
        requestPermission (arrayOf (permission), onGranted, onRejected, requestCode)

    private fun processNextRequest () {
        requestQueue.firstOrNull ()?.apply {
            if (permissions.all (::isGranted))
                onGranted (requestCode)
            else
                ActivityCompat.requestPermissions (activity, permissions, requestCode)
        }
    }

    private fun isGranted (permission: String) =
        ContextCompat.checkSelfPermission (activity, permission) == PackageManager.PERMISSION_GRANTED

    fun handlePermissionResult (requestCode: Int, grantResults: IntArray) {
        requestQueue.firstOrNull ()?.apply {
            if (this.requestCode == requestCode) {
                if (grantResults.all (PackageManager.PERMISSION_GRANTED::equals))
                    onGranted (requestCode)
                else
                    onRejected?.invoke (requestCode)
            }
        }
        requestQueue.removeFirst ()
        processNextRequest ()
    }

    class Request (
        val activity: Activity,
        val permissions: Array<String>,
        val requestCode: Int,
        val onGranted: (requestCode: Int) -> Unit,
        val onRejected: ((requestCode: Int) -> Unit)?
    )
}
