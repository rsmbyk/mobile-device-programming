package com.rsmbyk.course.mdp.domain.mapper

import com.rsmbyk.course.mdp.domain.model.Entity

interface Mapper<E: Entity, M> {

    fun mapToEntity (model: M): E
    fun mapToModel (entity: E): M

    fun mapToEntity (models: List<M>): List<E> =
        models.map (::mapToEntity)

    fun mapToModel (entities: List<E>): List<M> =
        entities.map (::mapToModel)
}
