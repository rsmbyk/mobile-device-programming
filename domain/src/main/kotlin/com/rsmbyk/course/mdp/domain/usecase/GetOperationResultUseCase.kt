package com.rsmbyk.course.mdp.domain.usecase

import com.rsmbyk.course.mdp.domain.model.Operator
import com.rsmbyk.course.mdp.domain.repository.CalculatorRepository

class GetOperationResultUseCase (private val repository: CalculatorRepository) {

    fun execute (operator: Operator, a: Int, b: Int): Int =
        when (operator) {
            Operator.ADD -> repository.add (a, b)
            Operator.SUBTRACT -> repository.subtract (a, b)
            Operator.MULTIPLY -> repository.multiply (a, b)
            Operator.DIVIDE -> repository.divide (a, b)
        }
}
