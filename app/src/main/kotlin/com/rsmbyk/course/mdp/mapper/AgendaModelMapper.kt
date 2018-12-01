package com.rsmbyk.course.mdp.mapper

import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.Agenda
import com.rsmbyk.course.mdp.model.AgendaModel

class AgendaModelMapper: Mapper<Agenda, AgendaModel> {

    private val coordinateMapper = CoordinateModelMapper ()

    override fun mapToEntity (model: AgendaModel): Agenda =
        model.run { Agenda (id, coordinateMapper.mapToEntity (coordinateModel)) }

    override fun mapToModel (entity: Agenda): AgendaModel =
        entity.run { AgendaModel (id, coordinateMapper.mapToModel (coordinate)) }
}
