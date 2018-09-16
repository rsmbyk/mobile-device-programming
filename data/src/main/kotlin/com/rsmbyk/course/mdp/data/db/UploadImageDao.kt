package com.rsmbyk.course.mdp.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.rsmbyk.course.mdp.data.model.UploadImageData
import io.reactivex.Single

@Dao
interface UploadImageDao {

    @Query ("SELECT * FROM upload_images")
    fun all (): Single<List<UploadImageData>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insert (images: UploadImageData)
}
