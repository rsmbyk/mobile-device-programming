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
import com.rsmbyk.course.mdp.ui.attendance.AttendanceFragment
import com.rsmbyk.course.mdp.ui.calculator.CalculatorFragment
import com.rsmbyk.course.mdp.ui.database.DatabaseFragment
import com.rsmbyk.course.mdp.ui.gallery.GalleryFragment
import com.rsmbyk.course.mdp.ui.upload.UploadFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: DaggerAppCompatActivity () {

    companion object {
        private const val TITLE_BUNDLE_KEY = "title"
    }

    private var pendingExitHandler: Handler? = null

    private fun FragmentManager.replaceFragment (@IdRes id: Int, fragment: Fragment?) {
        beginTransaction ().apply {
            replace (id, fragment!!)
            commit ()
        }
    }

    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate (savedInstanceState)
        setContentView (R.layout.activity_main)
        setupToolbar ()
        savedInstanceState?.getString (TITLE_BUNDLE_KEY)?.let (supportActionBar!!::setTitle)
        setupNavigationView ()
    }

    private fun setupToolbar () {
        setSupportActionBar (toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled (true)
            setHomeAsUpIndicator (R.drawable.ic_menu)
        }
    }

    private fun setupNavigationView () {
        navigation_view.setNavigationItemSelectedListener { menuItem ->
            supportFragmentManager.replaceFragment (R.id.fragment, when (menuItem.itemId) {
                R.id.menu_calculator -> CalculatorFragment ()
                R.id.menu_gallery -> GalleryFragment ()
                R.id.menu_upload -> UploadFragment ()
                R.id.menu_database -> DatabaseFragment ()
                R.id.menu_attendance -> AttendanceFragment ()
                else -> null
            })
            supportActionBar?.title = menuItem.title
            menuItem.isChecked = true
            drawer_layout.closeDrawers ()
            true
        }

        if (supportFragmentManager.findFragmentById (R.id.fragment) == null) {
            supportFragmentManager.replaceFragment (R.id.fragment, AttendanceFragment ())
            supportActionBar?.title = getString (R.string.menu_attendance)
            navigation_view.setCheckedItem (R.id.menu_attendance)
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

    override fun onSaveInstanceState (outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString (TITLE_BUNDLE_KEY, supportActionBar?.title?.toString ())
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
