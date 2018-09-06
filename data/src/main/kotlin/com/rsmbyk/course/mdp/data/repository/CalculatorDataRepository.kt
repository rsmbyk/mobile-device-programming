package com.rsmbyk.course.mdp.data.repository

import com.rsmbyk.course.mdp.domain.repository.CalculatorRepository

class CalculatorDataRepository: CalculatorRepository {

    override fun add (a: Int, b: Int): Int =
        a + b

    override fun subtract (a: Int, b: Int): Int =
        a - b

    override fun multiply (a: Int, b: Int): Int =
        a * b

    override fun divide (a: Int, b: Int): Int =
        a / b
}
