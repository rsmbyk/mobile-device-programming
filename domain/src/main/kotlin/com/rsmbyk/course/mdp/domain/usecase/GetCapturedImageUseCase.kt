package com.rsmbyk.course.mdp.domain.usecase

import com.rsmbyk.course.mdp.domain.repository.ImageRepository
import io.reactivex.Observable
import java.io.File

class GetCapturedImageUseCase (private val imageRepository: ImageRepository) {

    operator fun invoke (directory: File): Observable<File> =
        imageRepository.getCapturedImage (directory)
}
