package com.rsmbyk.course.mdp.ui.calculator

import android.arch.lifecycle.ViewModelProviders
import com.rsmbyk.course.mdp.data.mapper.OperatorDataMapper
import com.rsmbyk.course.mdp.data.model.OperatorData
import com.rsmbyk.course.mdp.data.repository.CalculatorDataRepository
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.Operator
import com.rsmbyk.course.mdp.domain.repository.CalculatorRepository
import com.rsmbyk.course.mdp.domain.usecase.EvaluateUseCase
import com.rsmbyk.course.mdp.mapper.OperatorModelMapper
import com.rsmbyk.course.mdp.model.OperatorModel
import dagger.Module
import dagger.Provides

@Module
class CalculatorFragmentModule {

    @Provides
    fun provideCalculatorViewModel (fragment: CalculatorFragment, factory: CalculatorViewModelFactory): CalculatorViewModel =
        ViewModelProviders.of (fragment, factory).get (CalculatorViewModel::class.java)

    @Provides
    fun provideCalculatorViewModelFactory (operatorModelMapper: Mapper<Operator, OperatorModel>, evaluateUseCase: EvaluateUseCase): CalculatorViewModelFactory =
        CalculatorViewModelFactory (operatorModelMapper, evaluateUseCase)

    @Provides
    fun provideOperatorModelMapper (): Mapper<Operator, OperatorModel> =
        OperatorModelMapper ()

    @Provides
    fun provideEvaluateUseCase (calculatorRepository: CalculatorRepository): EvaluateUseCase =
        EvaluateUseCase (calculatorRepository)

    @Provides
    fun provideCalculatorRepository (operatorDataMapper: Mapper<Operator, OperatorData>): CalculatorRepository =
        CalculatorDataRepository (operatorDataMapper)

    @Provides
    fun provideOperatorDataMapper (): Mapper<Operator, OperatorData> =
        OperatorDataMapper ()
}
