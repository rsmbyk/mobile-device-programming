package com.rsmbyk.course.mdp.model

class UploadImageResponseModel (val msg: Message) {

    enum class Message {
        DETECTED,
        NOT
    }
}
