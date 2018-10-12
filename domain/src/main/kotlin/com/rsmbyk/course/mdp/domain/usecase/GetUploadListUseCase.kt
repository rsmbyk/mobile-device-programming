package com.rsmbyk.course.mdp.domain.usecase

import com.rsmbyk.course.mdp.domain.repository.UploadImageRepository

class GetUploadListUseCase (private val repository: UploadImageRepository) {

    operator fun invoke (): List<String> =
        repository.getUploadList ()
}
