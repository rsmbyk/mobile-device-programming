package com.rsmbyk.course.mdp.data.mapper

import com.rsmbyk.course.mdp.data.model.OperatorData
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.Operator

class OperatorDataMapper: Mapper<Operator, OperatorData> {

    override fun mapToEntity (model: OperatorData): Operator =
        Operator.valueOf (model.name)

    override fun mapToModel (entity: Operator): OperatorData =
        OperatorData.valueOf (entity.name)
}
