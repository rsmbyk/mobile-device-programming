package com.rsmbyk.course.mdp.ui.main

import android.os.Bundle
import android.os.Handler
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.view.MenuItem
import android.widget.Toast
import com.rsmbyk.course.mdp.R
import com.rsmbyk.course.mdp.ui.calculator.CalculatorFragment
import com.rsmbyk.course.mdp.ui.camera.CameraFragment
import com.rsmbyk.course.mdp.ui.networking.NetworkingFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: DaggerAppCompatActivity () {

    private var pendingExitHandler: Handler? = null

    private fun FragmentManager.replace (@IdRes id: Int, fragment: Fragment, addToBackStack: Boolean = false) {
        beginTransaction ().apply {
            replace (id, fragment)
            if (addToBackStack)
                addToBackStack (null)
            commit ()
        }
    }

    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate (savedInstanceState)
        setContentView (R.layout.activity_main)
        setupToolbar ()
        setupNavigationView ()
        supportFragmentManager.replace (R.id.fragment, CameraFragment ())
    }

    private fun setupToolbar () {
        setSupportActionBar (toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled (true)
            setHomeAsUpIndicator (R.drawable.ic_menu)
        }
    }

    private fun setupNavigationView () {
        navigation_view.setNavigationItemSelectedListener {
            supportFragmentManager.replace (R.id.fragment, when (it.itemId) {
                R.id.menu_calculator -> CalculatorFragment ()
                R.id.menu_camera -> CameraFragment ()
                R.id.menu_networking -> NetworkingFragment ()
//                R.id.menu_database -> DatabaseFragment ()
                else -> throw IllegalArgumentException ()
            })
            supportActionBar?.title = when (it.itemId) {
                R.id.menu_calculator -> getString (R.string.menu_calculator)
                R.id.menu_camera -> getString (R.string.menu_camera)
                R.id.menu_networking -> getString (R.string.menu_networking)
                R.id.menu_database -> getString (R.string.menu_database)
                else -> throw IllegalArgumentException ()
            }
            it.isChecked = true
            drawer_layout.closeDrawers ()
            true
        }
    }

    override fun onOptionsItemSelected (item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer (GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected (item)
        }
    }

    override fun onBackPressed () {
        if (drawer_layout.isDrawerOpen (GravityCompat.START))
            drawer_layout.closeDrawers ()
        else {
            if (pendingExitHandler != null)
                super.onBackPressed ()
            else {
                pendingExitHandler = Handler ()
                pendingExitHandler
                    ?.postDelayed (
                        { pendingExitHandler = null },
                        resources.getInteger (R.integer.pending_exit_interval).toLong ())
                Toast
                    .makeText (
                        this, getString (R.string.toast_pending_exit), Toast.LENGTH_SHORT)
                    .show ()
            }
        }
    }
}
