package com.rsmbyk.course.mdp.mapper

import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.PredictRequest
import com.rsmbyk.course.mdp.model.PredictRequestModel

class PredictRequestModelMapper: Mapper<PredictRequest, PredictRequestModel> {

    private val agendaMapper = AgendaModelMapper ()

    override fun mapToEntity (model: PredictRequestModel): PredictRequest {
        return model.run {
            PredictRequest (
                idUser, password, image, agendaMapper.mapToEntity (agendaModel))
        }
    }

    override fun mapToModel (entity: PredictRequest): PredictRequestModel {
        return entity.run {
            PredictRequestModel (
                idUser, password, image, agendaMapper.mapToModel (agenda))
        }
    }
}
