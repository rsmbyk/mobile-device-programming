package com.rsmbyk.course.mdp.domain.model

class Student (
    val nrp: String,
    val name: String,
    val attendances: List<Boolean>)
        : Entity
