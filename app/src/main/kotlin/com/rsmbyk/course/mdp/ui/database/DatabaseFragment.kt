package com.rsmbyk.course.mdp.ui.database

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rsmbyk.course.mdp.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_database.*
import javax.inject.Inject

class DatabaseFragment: DaggerFragment () {

    @Inject
    lateinit var viewModel: DatabaseViewModel

    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate (R.layout.fragment_database, container, false)

    override fun onViewCreated (view: View, savedInstanceState: Bundle?) =
        setupUploadList ()

    private fun setupUploadList () {
        upload_list.layoutManager = LinearLayoutManager (context)
        upload_list.addItemDecoration (DividerItemDecoration (context, RecyclerView.VERTICAL))
        upload_list.setHasFixedSize (true)
        upload_list.adapter = viewModel.uploadListAdapter
        viewModel.getUploadedImages ()
    }
}
