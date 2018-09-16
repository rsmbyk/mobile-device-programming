package com.rsmbyk.course.mdp.data.mapper

import com.rsmbyk.course.mdp.data.model.UploadImageData
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.UploadImage

class UploadImageDataMapper: Mapper<UploadImage, UploadImageData> {

    override fun mapToEntity (model: UploadImageData): UploadImage =
        model.run {
            UploadImage (
                id,
                path,
                elapsedTime,
                uploadTime
            )
        }

    override fun mapToModel (entity: UploadImage): UploadImageData =
        entity.run {
            UploadImageData (
                code,
                path,
                elapsedTime,
                uploadTime
            )
        }
}
