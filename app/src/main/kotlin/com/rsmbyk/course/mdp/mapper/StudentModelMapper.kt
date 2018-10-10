package com.rsmbyk.course.mdp.mapper

import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.Student
import com.rsmbyk.course.mdp.model.StudentModel

class StudentModelMapper: Mapper<Student, StudentModel> {

    override fun mapToEntity (model: StudentModel): Student =
        model.run { Student (nrp, name, attendances) }

    override fun mapToModel (entity: Student): StudentModel =
        entity.run { StudentModel (nrp, name, attendances) }
}
