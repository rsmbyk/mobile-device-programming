package com.rsmbyk.course.mdp.domain.usecase

import com.rsmbyk.course.mdp.domain.model.Operator
import com.rsmbyk.course.mdp.domain.repository.CalculatorRepository

class GetOperationResultUseCase (private val calculatorRepository: CalculatorRepository) {

    operator fun invoke (operator: Operator, a: Int, b: Int): Int =
        when (operator) {
            Operator.ADD -> calculatorRepository.add (a, b)
            Operator.SUBTRACT -> calculatorRepository.subtract (a, b)
            Operator.MULTIPLY -> calculatorRepository.multiply (a, b)
            Operator.DIVIDE -> calculatorRepository.divide (a, b)
        }
}
