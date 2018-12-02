package com.rsmbyk.course.mdp.mapper

import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.SuperResponse
import com.rsmbyk.course.mdp.model.SuperResponseModel

class SuperResponseModelMapper: Mapper<SuperResponse, SuperResponseModel> {

    override fun mapToEntity (model: SuperResponseModel): SuperResponse =
        model.run { SuperResponse (status.name, msg) }

    override fun mapToModel (entity: SuperResponse): SuperResponseModel =
        entity.run {
            SuperResponseModel (
                SuperResponseModel.Status.valueOf (status), msg) }
}
