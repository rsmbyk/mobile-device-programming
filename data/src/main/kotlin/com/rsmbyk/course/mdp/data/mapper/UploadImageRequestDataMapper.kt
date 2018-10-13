package com.rsmbyk.course.mdp.data.mapper

import com.rsmbyk.course.mdp.data.model.UploadImageRequestData
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.UploadImageRequest

class UploadImageRequestDataMapper: Mapper<UploadImageRequest, UploadImageRequestData> {

    override fun mapToEntity (model: UploadImageRequestData): UploadImageRequest =
        model.run { UploadImageRequest (image) }

    override fun mapToModel (entity: UploadImageRequest): UploadImageRequestData =
        entity.run { UploadImageRequestData (image) }
}
