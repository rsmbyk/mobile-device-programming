package com.rsmbyk.course.mdp.ui.networking

data class NetworkingViewState (
    val isUploading: Boolean = false,
    val uploadIndex: Int = 0,
    val uploadName: String = "",
    val uploadTotal: Int = 0
)
