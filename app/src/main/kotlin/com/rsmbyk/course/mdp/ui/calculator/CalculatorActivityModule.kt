package com.rsmbyk.course.mdp.ui.calculator

import android.arch.lifecycle.ViewModelProviders
import com.rsmbyk.course.mdp.data.repository.CalculatorDataRepository
import com.rsmbyk.course.mdp.domain.repository.CalculatorRepository
import com.rsmbyk.course.mdp.domain.usecase.GetOperationResultUseCase
import dagger.Module
import dagger.Provides

@Module
class CalculatorActivityModule {

    @Provides
    fun provideCalculatorRepository (): CalculatorRepository =
        CalculatorDataRepository ()

    @Provides
    fun provideGetOperationResultUseCase (calculatorRepository: CalculatorRepository): GetOperationResultUseCase =
        GetOperationResultUseCase (calculatorRepository)

    @Provides
    fun provideCalculatorViewModelFactory (getOperationResultUseCase: GetOperationResultUseCase): CalculatorViewModelFactory =
        CalculatorViewModelFactory (getOperationResultUseCase)

    @Provides
    fun provideCalculatorViewModel (activity: CalculatorActivity, factory: CalculatorViewModelFactory): CalculatorViewModel =
        ViewModelProviders.of (activity, factory).get (CalculatorViewModel::class.java)
}
