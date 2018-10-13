package com.rsmbyk.course.mdp.ui.attendance

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rsmbyk.course.mdp.R
import com.rsmbyk.course.mdp.common.util.PermissionUtil
import com.rsmbyk.course.mdp.model.StudentModel
import com.rsmbyk.course.mdp.ui.attendance.tableview.AttendanceAdapter
import com.rsmbyk.course.mdp.ui.attendance.tableview.AttendanceTableListener
import com.rsmbyk.course.mdp.ui.camera.CameraActivity
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_attendance.*
import java.io.File
import javax.inject.Inject

class AttendanceFragment: DaggerFragment () {

    @Inject
    lateinit var viewModel: AttendanceViewModel

    @Inject
    lateinit var permissionUtil: PermissionUtil

    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate (R.layout.fragment_attendance, container, false)

    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        viewModel.students.observe (this, Observer (::setupTableView))
        viewModel.getStudents ()
    }

    private fun setupTableView (students: List<StudentModel>?) {
        val adapter = AttendanceAdapter (context!!)
        table.adapter = adapter
        table.tableViewListener = AttendanceTableListener (::onTableItemLongClicked)
        adapter.setAllItems (
            viewModel.getColumnHeaderList (),
            students?.mapIndexed { index, _ -> index.toString () },
            students?.map {
                listOf (it.nrp, it.name, *it.attendances.map (Boolean::toString).toTypedArray (), "") }
        )
    }

    private fun onTableItemLongClicked (row: Int) {
        permissionUtil.requestPermission (Manifest.permission.CAMERA, ::onPermissionGranted, null, row)
    }

    private fun onPermissionGranted (requestCode: Int) =
        startActivityForResult (Intent (context!!, CameraActivity::class.java), requestCode)

    override fun onRequestPermissionsResult (requestCode: Int, permissions: Array<out String>, grantResults: IntArray) =
        permissionUtil.handlePermissionResult (requestCode, grantResults)

    override fun onActivityResult (requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            data?.let {
                val picturePath = it.getStringExtra (CameraActivity.PICTURE_PATH_EXTRA)
                val image = File (picturePath).readBytes ()
                viewModel.checkFace (requestCode, image)
            }
        }
    }

    override fun onDestroy () {
        super.onDestroy ()
        viewModel.students.removeObservers (this)
    }
}
