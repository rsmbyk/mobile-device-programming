package com.rsmbyk.course.mdp.mapper

import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.UploadImage
import com.rsmbyk.course.mdp.model.UploadImageModel
import java.io.File

class UploadImageModelMapper: Mapper<UploadImage, UploadImageModel> {

    override fun mapToEntity (model: UploadImageModel): UploadImage =
        model.run {
            UploadImage (
                index,
                file.absolutePath,
                elapsedTime,
                uploadTime
            )
        }

    override fun mapToModel (entity: UploadImage): UploadImageModel =
        entity.run {
            UploadImageModel (
                code,
                File (path),
                elapsedTime,
                uploadTime
            )
        }

}
