package com.rsmbyk.course.mdp.data.repository

import com.rsmbyk.course.mdp.data.model.OperatorData
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.Operator
import com.rsmbyk.course.mdp.domain.repository.CalculatorRepository
import io.reactivex.Single

class CalculatorDataRepository (private val operatorMapper: Mapper<Operator, OperatorData>)
    : CalculatorRepository {

    override fun evaluate (operator: Operator, x: Int, y: Int): Single<Int> {
        return Single.create { emitter ->
            try {
                emitter.onSuccess (operatorMapper.mapToModel (operator).func (x, y))
            } catch (e: Exception) {
                emitter.onError (e)
            }
        }
    }
}
