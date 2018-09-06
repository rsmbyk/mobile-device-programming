package com.rsmbyk.course.mdp.ui.calculator

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.rsmbyk.course.mdp.domain.usecase.GetOperationResultUseCase

class CalculatorViewModelFactory (private val getOperationResultUseCase: GetOperationResultUseCase): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create (modelClass: Class<T>): T =
        CalculatorViewModel (getOperationResultUseCase) as T
}
