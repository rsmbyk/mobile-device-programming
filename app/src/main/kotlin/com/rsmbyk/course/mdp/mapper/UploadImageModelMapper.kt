package com.rsmbyk.course.mdp.mapper

import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.UploadImage
import com.rsmbyk.course.mdp.model.UploadImageModel

class UploadImageModelMapper: Mapper<UploadImage, UploadImageModel> {

    override fun mapToEntity (model: UploadImageModel): UploadImage {
        return model.run {
            UploadImage (
                index, text, image!!, elapsedTime!!, timestamp!!)
        }
    }

    override fun mapToModel (entity: UploadImage): UploadImageModel {
        return entity.run {
            UploadImageModel (
                id, name, image, elapsedTime, timestamp)
        }
    }
}
