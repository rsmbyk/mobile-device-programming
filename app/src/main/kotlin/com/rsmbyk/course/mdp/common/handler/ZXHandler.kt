package com.rsmbyk.course.mdp.common.handler

import android.arch.lifecycle.MutableLiveData
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ZXHandler {

    val result = MutableLiveData<Result> ()
    lateinit var scannerView: ZXingScannerView

    fun setResultHandler (scannerView: ZXingScannerView) {
        if (this::scannerView.isInitialized)
            this.scannerView.stopCamera ()
        this.scannerView = scannerView
    }

    fun start () {
        scannerView.setResultHandler (result::setValue)
        scannerView.startCamera ()
    }

    fun resume () =
        scannerView.resumeCameraPreview (result::setValue)

    fun stop () =
        scannerView.stopCamera ()
}
