package com.rsmbyk.course.mdp.data.db.converter

import android.arch.persistence.room.TypeConverter

class AttendanceConverter {

    @TypeConverter
    fun toString (attendances: List<Boolean>): String =
        attendances.map { if (it) "1" else "0" }.joinToString ("")

    @TypeConverter
    fun toBooleanList (attendancesString: String): List<Boolean> =
        attendancesString.map { it == '1' }
}
