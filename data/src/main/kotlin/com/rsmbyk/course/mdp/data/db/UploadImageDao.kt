package com.rsmbyk.course.mdp.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.rsmbyk.course.mdp.data.model.UploadImageData
import io.reactivex.Single

@Dao
interface UploadImageDao {

    @Query ("SELECT * FROM upload_images")
    fun all (): Single<List<UploadImageData>>

    @Query ("SELECT * FROM upload_images WHERE id = :id")
    fun get (id: Int): Single<UploadImageData>

    @Insert
    fun insert (images: UploadImageData)

    @Update
    fun update (images: UploadImageData)
}
