package com.rsmbyk.course.mdp.domain.repository

import com.rsmbyk.course.mdp.domain.model.Coordinate

interface CoordinateRepository {

    fun getGPSLocation ()
    fun getDistance (room: Coordinate, device: Coordinate): Float
}
