package com.rsmbyk.course.mdp.domain.model

class PredictRequest (
    val idUser: String,
    val password: String,
    val image: ByteArray,
    val agenda: Agenda)
        : Entity
