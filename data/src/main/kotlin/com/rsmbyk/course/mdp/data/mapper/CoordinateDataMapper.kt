package com.rsmbyk.course.mdp.data.mapper

import com.rsmbyk.course.mdp.data.model.CoordinateData
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.Coordinate

class CoordinateDataMapper: Mapper<Coordinate, CoordinateData> {

    override fun mapToEntity (model: CoordinateData): Coordinate =
        model.run { Coordinate (latitude, longitude) }

    override fun mapToModel (entity: Coordinate): CoordinateData =
        entity.run { CoordinateData (latitude, longitude) }
}
