package com.rsmbyk.course.mdp.mapper

import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.Coordinate
import com.rsmbyk.course.mdp.model.CoordinateModel

class CoordinateModelMapper: Mapper<Coordinate, CoordinateModel> {

    override fun mapToEntity (model: CoordinateModel): Coordinate =
        model.run { Coordinate (latitude, longitude) }

    override fun mapToModel (entity: Coordinate): CoordinateModel =
        entity.run { CoordinateModel (latitude, longitude) }
}
