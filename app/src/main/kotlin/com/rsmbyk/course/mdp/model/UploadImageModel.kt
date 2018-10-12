package com.rsmbyk.course.mdp.model

import java.util.*

data class UploadImageModel (
    val index: Int,
    val text: String,
    val image: ByteArray? = null,
    var elapsedTime: Float? = null,
    var timestamp: Long? = null,
    var state: State = State.IDLE) {

    enum class State {
        IDLE,
        UPLOADING,
        FAILED,
        SUCCESS
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UploadImageModel

        if (text != other.text) return false
        if (!Arrays.equals(image, other.image)) return false
        if (elapsedTime != other.elapsedTime) return false
        if (timestamp != other.timestamp) return false
        if (state != other.state) return false

        return true
    }

    override fun hashCode(): Int {
        var result = text.hashCode ()
        result = 31 * result + (image?.let { Arrays.hashCode (it) } ?: 0)
        result = 31 * result + (elapsedTime?.hashCode () ?: 0)
        result = 31 * result + (timestamp?.hashCode () ?: 0)
        result = 31 * result + state.hashCode ()
        return result
    }
}
