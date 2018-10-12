package com.rsmbyk.course.mdp.data.mapper

import com.rsmbyk.course.mdp.data.db.entity.UploadImageEntity
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.UploadImage

class UploadImageEntityMapper: Mapper<UploadImage, UploadImageEntity> {

    override fun mapToEntity (model: UploadImageEntity): UploadImage =
        model.run { UploadImage (id, name, image, elapsedTime, timestamp) }

    override fun mapToModel (entity: UploadImage): UploadImageEntity =
        entity.run { UploadImageEntity (id, name, image, elapsedTime, timestamp) }
}
