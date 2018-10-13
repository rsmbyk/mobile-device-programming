package com.rsmbyk.course.mdp.mapper

import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.UploadImageResponse
import com.rsmbyk.course.mdp.model.UploadImageResponseModel

class UploadImageResponseModelMapper: Mapper<UploadImageResponse, UploadImageResponseModel> {

    override fun mapToEntity (model: UploadImageResponseModel): UploadImageResponse =
        model.run { UploadImageResponse () }

    override fun mapToModel (entity: UploadImageResponse): UploadImageResponseModel =
        entity.run { UploadImageResponseModel () }
}
