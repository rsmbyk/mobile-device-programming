package com.rsmbyk.course.mdp.data.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity (tableName = "students")
class StudentEntity (

    @PrimaryKey
    val nrp: String,

    val name: String,
    val attendances: List<Boolean>
)
