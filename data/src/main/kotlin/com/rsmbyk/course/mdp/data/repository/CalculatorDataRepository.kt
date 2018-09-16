package com.rsmbyk.course.mdp.data.repository

import com.rsmbyk.course.mdp.data.model.OperatorData
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.Operator
import com.rsmbyk.course.mdp.domain.repository.CalculatorRepository
import io.reactivex.Observable

class CalculatorDataRepository (private val mapper: Mapper<Operator, OperatorData>): CalculatorRepository {

    override fun evaluate (operator: Operator, x: Int, y: Int): Observable<Int> {
        return Observable.create { emitter ->
            try {
                emitter.onNext (mapper.mapToModel (operator).func (x, y))
            } catch (e: Exception) {
                emitter.onError (e)
            } finally {
                emitter.onComplete ()
            }
        }
    }
}
