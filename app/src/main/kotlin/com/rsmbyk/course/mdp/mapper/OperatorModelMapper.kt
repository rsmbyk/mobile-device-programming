package com.rsmbyk.course.mdp.mapper

import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.Operator
import com.rsmbyk.course.mdp.model.OperatorModel

class OperatorModelMapper: Mapper<Operator, OperatorModel> {

    override fun mapToEntity (model: OperatorModel): Operator =
        Operator.valueOf (model.name)

    override fun mapToModel (entity: Operator): OperatorModel =
        OperatorModel.valueOf (entity.name)
}
