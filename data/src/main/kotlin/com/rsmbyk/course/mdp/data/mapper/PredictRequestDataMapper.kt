package com.rsmbyk.course.mdp.data.mapper

import com.rsmbyk.course.mdp.data.common.compress
import com.rsmbyk.course.mdp.data.common.toBase64ImageString
import com.rsmbyk.course.mdp.data.model.PredictRequestData
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.PredictRequest

class PredictRequestDataMapper: Mapper<PredictRequest, PredictRequestData> {

    private val agendaMapper = AgendaDataMapper ()

    override fun mapToEntity (model: PredictRequestData): PredictRequest =
        model.run {
            PredictRequest (
                idUser,
                password,
                image.toByteArray (),
                agendaMapper.mapToEntity (agenda)) }

    override fun mapToModel (entity: PredictRequest): PredictRequestData =
        entity.run {
            PredictRequestData (
                idUser,
                password,
                image.compress ().toBase64ImageString (),
                agendaMapper.mapToModel (agenda)) }
}
