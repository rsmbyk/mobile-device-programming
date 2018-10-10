package com.rsmbyk.course.mdp.data.mapper

import com.rsmbyk.course.mdp.data.db.entity.StudentEntity
import com.rsmbyk.course.mdp.domain.mapper.Mapper
import com.rsmbyk.course.mdp.domain.model.Student

class StudentEntityMapper: Mapper<Student, StudentEntity> {

    override fun mapToEntity (model: StudentEntity): Student =
        model.run { Student (nrp, name, attendances) }

    override fun mapToModel (entity: Student): StudentEntity =
        entity.run { StudentEntity (nrp, name, attendances) }
}
