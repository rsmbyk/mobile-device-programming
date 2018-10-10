package com.rsmbyk.course.mdp.data.mapper

import com.rsmbyk.course.mdp.data.model.PredictResponseData
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.PredictResponse

class PredictResponseDataMapper: Mapper<PredictResponse, PredictResponseData> {

    override fun mapToEntity (model: PredictResponseData): PredictResponse =
        model.run { PredictResponse () }

    override fun mapToModel (entity: PredictResponse): PredictResponseData =
        entity.run { PredictResponseData () }
}
