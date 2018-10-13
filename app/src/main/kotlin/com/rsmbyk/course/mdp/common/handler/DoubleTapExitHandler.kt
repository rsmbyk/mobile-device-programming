package com.rsmbyk.course.mdp.common.handler

import android.app.Activity
import android.arch.lifecycle.LifecycleObserver
import android.os.Handler
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.widget.Toast
import com.rsmbyk.course.mdp.R

class DoubleTapExitHandler (private val activity: Activity): LifecycleObserver {

    private var pendingExitHandler: Handler? = null
    private var drawerLayout: DrawerLayout? = null

    fun bindDrawer (drawerLayout: DrawerLayout) {
        this.drawerLayout = drawerLayout
    }

    fun onBackPressed (): Boolean {
        return checkDrawer (drawerLayout) && checkHandler ()
    }

    private fun checkDrawer (drawerLayout: DrawerLayout?): Boolean {
        if (drawerLayout != null) {
            if (drawerLayout.isDrawerOpen (GravityCompat.START)) {
                drawerLayout.closeDrawers ()
                return false
            }
        }
        return true
    }

    private fun checkHandler (): Boolean {
        return if (pendingExitHandler != null) true
        else { restartHandler (); false }
    }

    private fun restartHandler () {
        pendingExitHandler = Handler ()
        pendingExitHandler?.postDelayed (
            { pendingExitHandler = null },
            activity.resources.getInteger (R.integer.pending_exit_interval).toLong ())
        Toast.makeText (activity, activity.getString (R.string.toast_pending_exit), Toast.LENGTH_SHORT).show ()
    }
}
