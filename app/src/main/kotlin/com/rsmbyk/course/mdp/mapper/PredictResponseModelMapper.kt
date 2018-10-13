package com.rsmbyk.course.mdp.mapper

import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.PredictResponse
import com.rsmbyk.course.mdp.model.PredictResponseModel

class PredictResponseModelMapper: Mapper<PredictResponse, PredictResponseModel> {

    override fun mapToEntity (model: PredictResponseModel): PredictResponse =
        model.run { PredictResponse () }

    override fun mapToModel (entity: PredictResponse): PredictResponseModel =
        entity.run { PredictResponseModel () }
}
