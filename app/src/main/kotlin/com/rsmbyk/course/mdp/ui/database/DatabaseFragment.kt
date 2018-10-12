package com.rsmbyk.course.mdp.ui.database

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rsmbyk.course.mdp.R
import com.rsmbyk.course.mdp.model.UploadImageModel
import com.rsmbyk.course.mdp.ui.database.rv.DatabaseAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_upload.*
import javax.inject.Inject

class DatabaseFragment: DaggerFragment () {

    @Inject
    lateinit var viewModel: DatabaseViewModel

    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate (R.layout.fragment_database, container, false)

    override fun onViewCreated (view: View, savedInstanceState: Bundle?) =
        viewModel.uploadImages.observe (this, Observer (::setupUploadList))

    private fun setupUploadList (uploadImages: List<UploadImageModel>?) {
        uploadImages?.also {
            upload_list.setHasFixedSize (true)
            upload_list.adapter = DatabaseAdapter (it)
        }
    }
}
