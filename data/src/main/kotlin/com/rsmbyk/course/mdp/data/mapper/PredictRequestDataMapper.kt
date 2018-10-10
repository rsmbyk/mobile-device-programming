package com.rsmbyk.course.mdp.data.mapper

import com.rsmbyk.course.mdp.data.model.PredictRequestData
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.PredictRequest

class PredictRequestDataMapper: Mapper<PredictRequest, PredictRequestData> {

    override fun mapToEntity (model: PredictRequestData): PredictRequest =
        model.run { PredictRequest (nrp, image) }

    override fun mapToModel (entity: PredictRequest): PredictRequestData =
        entity.run { PredictRequestData (nrp, image) }
}
