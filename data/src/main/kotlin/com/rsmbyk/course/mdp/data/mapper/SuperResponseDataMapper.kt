package com.rsmbyk.course.mdp.data.mapper

import com.rsmbyk.course.mdp.data.model.SuperResponseData
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.SuperResponse

class SuperResponseDataMapper: Mapper<SuperResponse, SuperResponseData> {

    override fun mapToEntity (model: SuperResponseData): SuperResponse =
        model.run {
            msg.split (',', limit = 2).let {
                SuperResponse (it[0], it[1].trim ()) } }

    override fun mapToModel (entity: SuperResponse): SuperResponseData =
        entity.run { SuperResponseData ("$status, $msg") }
}
