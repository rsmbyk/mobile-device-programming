package com.rsmbyk.course.mdp.ui.calculator

import android.arch.lifecycle.ViewModel
import com.rsmbyk.course.mdp.domain.model.Operator
import com.rsmbyk.course.mdp.domain.usecase.GetOperationResultUseCase

class CalculatorViewModel (private val getOperationResultUseCase: GetOperationResultUseCase): ViewModel () {

    fun evaluate (operator: Operator, a: Int, b: Int): Int =
        getOperationResultUseCase (operator, a, b)
}
