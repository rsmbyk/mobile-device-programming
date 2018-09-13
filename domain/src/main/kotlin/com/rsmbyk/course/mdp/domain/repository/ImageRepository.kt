package com.rsmbyk.course.mdp.domain.repository

import java.io.File

interface ImageRepository {

    fun getImages (directory: File): List<File>
}
