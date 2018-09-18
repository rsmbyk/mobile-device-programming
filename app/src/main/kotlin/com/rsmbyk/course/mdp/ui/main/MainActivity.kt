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
import com.rsmbyk.course.mdp.model.MenuModel
import com.rsmbyk.course.mdp.ui.calculator.CalculatorFragment
import com.rsmbyk.course.mdp.ui.camera.CameraFragment
import com.rsmbyk.course.mdp.ui.database.DatabaseFragment
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
        if (supportFragmentManager.findFragmentById (R.id.fragment) == null)
            bottom_navigation.selectedItemId = R.id.menu_camera
    }

    private fun setupToolbar () {
        setSupportActionBar (toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled (true)
            setHomeAsUpIndicator (R.drawable.ic_menu)
        }
    }

    private fun setupNavigationView () {

        bottom_navigation.setOnNavigationItemSelectedListener { menuItem ->
            val menuModel = MenuModel.values ().first { it.id == menuItem.itemId }
            supportFragmentManager.replace (R.id.fragment, when (menuModel) {
                MenuModel.CALCULATOR -> CalculatorFragment ()
                MenuModel.CAMERA -> CameraFragment ()
                MenuModel.NETWORKING -> NetworkingFragment ()
                MenuModel.DATABASE -> DatabaseFragment ()
            })
            navigation_view.setCheckedItem (menuItem)
            supportActionBar?.title = menuItem.title
            true
        }

        navigation_view.setNavigationItemSelectedListener { menuItem ->
            bottom_navigation.selectedItemId = menuItem.itemId
            supportActionBar?.title = menuItem.title
            menuItem.isChecked = true
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
