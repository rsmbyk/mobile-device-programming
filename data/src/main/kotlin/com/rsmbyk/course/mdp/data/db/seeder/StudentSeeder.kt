package com.rsmbyk.course.mdp.data.db.seeder

import android.content.Context
import com.rsmbyk.course.mdp.data.R
import com.rsmbyk.course.mdp.data.db.dao.StudentDao
import com.rsmbyk.course.mdp.data.db.entity.StudentEntity

class StudentSeeder {

    fun seed (context: Context, dao: StudentDao) {
        val attendances = (0 until 16).map { false }
        context.resources.getStringArray (R.array.students)
            .map { it.split (":", limit = 2) }
            .map { StudentEntity (it[0], it[1], attendances) }
            .forEach (dao::insert)
    }
}
