package com.rsmbyk.course.mdp.ui.calculator

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rsmbyk.course.mdp.model.OperatorModel
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.Operator
import com.rsmbyk.course.mdp.domain.usecase.EvaluateUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CalculatorViewModel (
        private val operatorMapper: Mapper<Operator, OperatorModel>,
        private val evaluateUseCase: EvaluateUseCase)
        : ViewModel () {

    private val disposable = CompositeDisposable ()

    val result = MutableLiveData<Int> ()
    val error = MutableLiveData<Throwable> ()

    fun evaluate (operator: OperatorModel, x: Int, y: Int) {
        disposable.add (
            evaluateUseCase (operatorMapper.mapToEntity (operator), x, y)
                .subscribeOn (Schedulers.io ())
                .observeOn (AndroidSchedulers.mainThread ())
                .subscribe (result::setValue, error::setValue)
        )
    }
}
