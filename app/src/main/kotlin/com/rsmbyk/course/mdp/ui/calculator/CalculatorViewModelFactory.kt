package com.rsmbyk.course.mdp.ui.calculator

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.Operator
import com.rsmbyk.course.mdp.domain.usecase.EvaluateUseCase
import com.rsmbyk.course.mdp.model.OperatorModel

class CalculatorViewModelFactory (
    private val operatorModelMapper: Mapper<Operator, OperatorModel>,
    private val evaluateUseCase: EvaluateUseCase)
        : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create (modelClass: Class<T>): T =
        CalculatorViewModel (operatorModelMapper, evaluateUseCase) as T
}
