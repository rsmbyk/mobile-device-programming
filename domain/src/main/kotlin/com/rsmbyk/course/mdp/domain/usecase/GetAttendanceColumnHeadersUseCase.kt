package com.rsmbyk.course.mdp.domain.usecase

import com.rsmbyk.course.mdp.domain.repository.AttendanceRepository

class GetAttendanceColumnHeadersUseCase (private val repository: AttendanceRepository) {

    operator fun invoke (): List<String> =
        repository.getColumnHeaders ()
}
