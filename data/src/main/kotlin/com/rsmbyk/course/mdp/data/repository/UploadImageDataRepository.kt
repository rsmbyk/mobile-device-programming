package com.rsmbyk.course.mdp.data.repository

import android.content.Context
import com.rsmbyk.course.mdp.data.R
import com.rsmbyk.course.mdp.data.api.volley.VolleyRequestQueue
import com.rsmbyk.course.mdp.data.api.volley.request.UploadImageVolleyRequest
import com.rsmbyk.course.mdp.data.db.dao.UploadImageDao
import com.rsmbyk.course.mdp.data.db.entity.UploadImageEntity
import com.rsmbyk.course.mdp.data.model.SuperResponseData
import com.rsmbyk.course.mdp.data.model.UploadImageRequestData
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.SuperResponse
import com.rsmbyk.course.mdp.domain.model.UploadImage
import com.rsmbyk.course.mdp.domain.model.UploadImageRequest
import com.rsmbyk.course.mdp.domain.repository.UploadImageRepository
import io.reactivex.Maybe
import io.reactivex.Single

class UploadImageDataRepository (
    private val context: Context,
    private val uploadImageDao: UploadImageDao,
    private val volleyRequestQueue: VolleyRequestQueue,
    private val uploadImageMapper: Mapper<UploadImage, UploadImageEntity>,
    private val uploadImageRequestMapper: Mapper<UploadImageRequest, UploadImageRequestData>,
    private val uploadImageResponseMapper: Mapper<SuperResponse, SuperResponseData>)
        : UploadImageRepository {

    override fun getNames (): List<String> =
        context.resources.getStringArray (R.array.upload_list).toList ()

    override fun uploadImage (request: UploadImageRequest): Single<SuperResponse> {
        return Single.create { emitter ->
            volleyRequestQueue.add (UploadImageVolleyRequest (
                context,
                uploadImageRequestMapper.mapToModel (request),
                { emitter.onSuccess (uploadImageResponseMapper.mapToEntity (it)) },
                emitter::onError))
        }
    }

    override fun saveImage (uploadImage: UploadImage) =
        uploadImageDao.insert (uploadImageMapper.mapToModel (uploadImage))

    override fun getImages (): Maybe<List<UploadImage>> {
        return uploadImageDao.all ()
            .map (uploadImageMapper::mapToEntity)
    }
}
