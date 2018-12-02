package com.rsmbyk.course.mdp.model

class SuperResponseModel (val status: Status, val msg: String) {

    enum class Status {
        ACCEPTED,
        REJECTED
    }
}
