package com.rsmbyk.course.mdp.model

data class PredictRequestModel (
    val idUser: String,
    val password: String,
    val image: ByteArray,
    val agendaModel: AgendaModel) {

    override fun equals (other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PredictRequestModel

        if (idUser != other.idUser) return false
        if (password != other.password) return false
        if (!image.contentEquals(other.image)) return false
        if (agendaModel != other.agendaModel) return false

        return true
    }

    override fun hashCode (): Int {
        var result = idUser.hashCode ()
        result = 31 * result + password.hashCode ()
        result = 31 * result + image.contentHashCode ()
        result = 31 * result + agendaModel.hashCode ()
        return result
    }
}
