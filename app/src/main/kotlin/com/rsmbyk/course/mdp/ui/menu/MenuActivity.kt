package com.rsmbyk.course.mdp.ui.menu

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.rsmbyk.course.mdp.R
import com.rsmbyk.course.mdp.domain.model.Menu
import com.rsmbyk.course.mdp.ui.calculator.CalculatorActivity
import com.rsmbyk.course.mdp.ui.camera.CameraActivity
import com.rsmbyk.course.mdp.ui.networking.NetworkingActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_menu.*
import javax.inject.Inject

class MenuActivity: DaggerAppCompatActivity () {

    @Inject
    lateinit var viewModel: MenuViewModel
    private var pendingExitHandler: Handler? = null

    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate (savedInstanceState)
        setContentView (R.layout.activity_menu)
        setupMenuList ()
    }

    private fun setupMenuList () {
        menus.setHasFixedSize (true)
        menus.addItemDecoration (DividerItemDecoration (this, RecyclerView.VERTICAL))
        menus.layoutManager = LinearLayoutManager (this)
        menus.adapter = MenuAdapter (viewModel.getMenus (), ::onMenuItemClick)
    }

    private fun onMenuItemClick (position: Int) {
        try {
            startActivity (Intent (this, when (Menu.values ()[position]) {
                Menu.CALCULATOR -> CalculatorActivity::class.java
                Menu.CAMERA -> CameraActivity::class.java
                Menu.NETWORKING -> NetworkingActivity::class.java
            }))
        } catch (e: IndexOutOfBoundsException) {
            AlertDialog.Builder (this)
                .setMessage ("Not implemented, yet!")
                .setPositiveButton ("Close") { _, _ -> }
                .setCancelable (false)
                .create ()
                .show ()
        }

    }

    override fun onBackPressed () {
        if (pendingExitHandler != null) {
            super.onBackPressed ()
        } else {
            pendingExitHandler = Handler ()
            pendingExitHandler?.postDelayed ({ pendingExitHandler = null }, 2000)
            Toast.makeText (this, getString (R.string.toast_pending_exit), Toast.LENGTH_SHORT).show ()
        }
    }
}
