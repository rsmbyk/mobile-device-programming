package com.rsmbyk.course.mdp.ui.networking

data class NetworkingViewState (
    val uploadState: UploadState = UploadState.IDLE,
    val uploadProgressIndex: Int = 0,
    val uploadProgressName: String = "",
    val uploadTotal: Int = 0,
    val uploadSuccess: Int = 0
) {

    enum class UploadState {
        IDLE,
        UPLOADING,
        FINISHED
    }
}
