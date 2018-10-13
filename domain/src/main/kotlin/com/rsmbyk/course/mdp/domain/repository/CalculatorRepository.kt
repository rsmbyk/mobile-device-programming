package com.rsmbyk.course.mdp.domain.repository

import com.rsmbyk.course.mdp.domain.model.Operator
import io.reactivex.Single

interface CalculatorRepository {

    fun evaluate (operator: Operator, x: Int, y: Int): Single<Int>
}
