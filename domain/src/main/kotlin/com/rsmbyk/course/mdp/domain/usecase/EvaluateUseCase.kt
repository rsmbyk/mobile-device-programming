package com.rsmbyk.course.mdp.domain.usecase

import com.rsmbyk.course.mdp.domain.model.Operator
import com.rsmbyk.course.mdp.domain.repository.CalculatorRepository
import io.reactivex.Single

class EvaluateUseCase (private val repository: CalculatorRepository) {

    operator fun invoke (operator: Operator, a: Int, b: Int): Single<Int> =
        repository.evaluate (operator, a, b)
}
