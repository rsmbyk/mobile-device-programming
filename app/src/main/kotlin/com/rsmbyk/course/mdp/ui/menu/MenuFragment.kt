package com.rsmbyk.course.mdp.ui.menu

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rsmbyk.course.mdp.R
import com.rsmbyk.course.mdp.domain.model.Menu
import com.rsmbyk.course.mdp.ui.calculator.CalculatorFragment
import com.rsmbyk.course.mdp.ui.camera.CameraFragment
import com.rsmbyk.course.mdp.ui.networking.NetworkingFragment
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_menu.*
import javax.inject.Inject

class MenuFragment: DaggerFragment () {

    @Inject
    lateinit var viewModel: MenuViewModel

    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate (R.layout.fragment_menu, container, false)

    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        setupMenuList ()
    }

    private fun setupMenuList () {
        menus.setHasFixedSize (true)
        menus.addItemDecoration (DividerItemDecoration (context, RecyclerView.VERTICAL))
        menus.layoutManager = LinearLayoutManager (context)
        viewModel.menus.observe (this, Observer {
            menus.adapter = MenuAdapter (it!!, ::onMenuItemClick)
        })
        viewModel.getMenus ()
    }

    private fun onMenuItemClick (position: Int) {
        startActivity (Intent (context, when (Menu.values ()[position]) {
            Menu.CALCULATOR -> CalculatorFragment::class.java
            Menu.CAMERA -> CameraFragment::class.java
            Menu.NETWORKING -> NetworkingFragment::class.java
        }))
    }
}
