package com.rsmbyk.course.mdp.domain.repository

import java.io.File

interface ImageRepository {

    fun getImages (): List<File>
}
