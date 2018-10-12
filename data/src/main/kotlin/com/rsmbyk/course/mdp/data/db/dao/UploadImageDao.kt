package com.rsmbyk.course.mdp.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.rsmbyk.course.mdp.data.db.entity.UploadImageEntity
import io.reactivex.Single

@Dao
interface UploadImageDao {

    @Query ("SELECT * FROM images")
    fun all (): Single<List<UploadImageEntity>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insert (uploadImage: UploadImageEntity)
}
