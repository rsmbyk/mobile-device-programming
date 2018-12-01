package com.rsmbyk.course.mdp.data.mapper

import com.rsmbyk.course.mdp.data.model.AgendaData
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.Agenda

class AgendaDataMapper: Mapper<Agenda, AgendaData> {

    private val coordinateMapper = CoordinateDataMapper ()

    override fun mapToEntity (model: AgendaData): Agenda =
        model.run { Agenda (id, coordinateMapper.mapToEntity (coordinate)) }

    override fun mapToModel (entity: Agenda): AgendaData =
        entity.run { AgendaData (id, coordinateMapper.mapToModel (coordinate)) }
}
