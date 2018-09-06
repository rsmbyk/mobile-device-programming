package com.rsmbyk.course.mdp.domain.repository

interface CalculatorRepository {

    fun add (a: Int, b: Int): Int
    fun subtract (a: Int, b: Int): Int
    fun multiply (a: Int, b: Int): Int
    fun divide (a: Int, b: Int): Int
}
