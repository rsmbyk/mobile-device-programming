package com.rsmbyk.course.mdp.domain.usecase

import com.rsmbyk.course.mdp.domain.model.Operator
import com.rsmbyk.course.mdp.domain.repository.CalculatorRepository
import io.reactivex.Observable

class EvaluateUseCase (private val calculatorRepository: CalculatorRepository) {

    operator fun invoke (operator: Operator, a: Int, b: Int): Observable<Int> =
        calculatorRepository.evaluate (operator, a, b)
}
