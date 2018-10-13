package com.rsmbyk.course.mdp.domain.usecase

import com.rsmbyk.course.mdp.domain.repository.UploadImageRepository

class GetUploadImageNamesUseCase (private val repository: UploadImageRepository) {

    operator fun invoke (): List<String> =
        repository.getNames ()
}
