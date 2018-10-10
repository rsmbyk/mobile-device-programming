package com.rsmbyk.course.mdp.domain.usecase

import com.rsmbyk.course.mdp.domain.model.Student
import com.rsmbyk.course.mdp.domain.repository.AttendanceRepository
import io.reactivex.Flowable

class GetStudentsUseCase (private val repository: AttendanceRepository) {

    operator fun invoke (): Flowable<List<Student>> =
        repository.getStudents ()
}
