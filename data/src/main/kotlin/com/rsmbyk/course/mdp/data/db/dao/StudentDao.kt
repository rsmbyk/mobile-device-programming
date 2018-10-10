package com.rsmbyk.course.mdp.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.rsmbyk.course.mdp.data.db.entity.StudentEntity
import io.reactivex.Flowable

@Dao
interface StudentDao {

    @Query ("SELECT * FROM students")
    fun all (): Flowable<List<StudentEntity>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insert (student: StudentEntity)
}
