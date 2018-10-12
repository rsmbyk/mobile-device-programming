package com.rsmbyk.course.mdp.data.mapper

import com.rsmbyk.course.mdp.data.model.UploadImageResponseData
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.UploadImageResponse

class UploadImageResponseDataMapper: Mapper<UploadImageResponse, UploadImageResponseData> {

    override fun mapToEntity (model: UploadImageResponseData): UploadImageResponse =
        model.run { UploadImageResponse () }

    override fun mapToModel (entity: UploadImageResponse): UploadImageResponseData =
        entity.run { UploadImageResponseData () }
}
