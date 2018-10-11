package com.rsmbyk.course.mdp.common

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import java.util.*

class PermissionUtil (private val activity: Activity? = null, private val fragment: Fragment? = null) {

    private val context = activity ?: fragment?.context
    private val requestQueue = LinkedList<Request> ()

    fun requestPermission (
            permissions: Array<String>,
            onGranted: (requestCode: Int) -> Unit,
            onDenied: ((requestCode: Int) -> Unit)? = null,
            requestCode: Int? = null): Int {
        val finalRequestCode = requestCode ?: Random ().nextInt (65535)
        requestQueue.push (Request (permissions, finalRequestCode, onGranted, onDenied))
        processNextRequest ()
        return finalRequestCode
    }

    fun requestPermission (
            permission: String,
            onGranted: (requestCode: Int) -> Unit,
            onDenied: ((requestCode: Int) -> Unit)? = null,
            requestCode: Int? = null): Int =
        requestPermission (arrayOf (permission), onGranted, onDenied, requestCode)

    fun requestPermission (
            permission: Array<String>,
            onGranted: () -> Unit,
            onDenied: (() -> Unit)? = null,
            requestCode: Int? = null): Int =
        requestPermission (permission, { _ -> onGranted () }, { _ -> onDenied?.invoke () }, requestCode)

    fun requestPermission (
            permission: String,
            onGranted: () -> Unit,
            onDenied: (() -> Unit)? = null,
            requestCode: Int? = null): Int =
        requestPermission (arrayOf (permission), { _ -> onGranted () }, { _ -> onDenied?.invoke () }, requestCode)

    private fun processNextRequest () {
        requestQueue.firstOrNull ()?.apply {
            if (permissions.all (::isGranted))
                onGranted (requestCode)
            else {
                if (activity != null)
                    ActivityCompat.requestPermissions (activity, permissions, requestCode)
                else if (fragment != null)
                    fragment.requestPermissions (permissions, requestCode)
            }
        }
    }

    private fun isGranted (permission: String): Boolean =
        ContextCompat.checkSelfPermission (context!!, permission) == PackageManager.PERMISSION_GRANTED

    fun handlePermissionResult (requestCode: Int, grantResults: IntArray) {
        requestQueue.firstOrNull ()?.apply {
            if (this.requestCode == requestCode) {
                if (grantResults.all (PackageManager.PERMISSION_GRANTED::equals))
                    onGranted (requestCode)
                else
                    onDenied?.invoke (requestCode)
            }
        }
        requestQueue.removeFirst ()
        processNextRequest ()
    }

    class Request (
        val permissions: Array<String>,
        val requestCode: Int,
        val onGranted: (requestCode: Int) -> Unit,
        val onDenied: ((requestCode: Int) -> Unit)?
    )
}
