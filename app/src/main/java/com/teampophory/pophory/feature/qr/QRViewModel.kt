package com.teampophory.pophory.feature.qr

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

sealed class QRState {
    data object Initial : QRState()
    data object Loading : QRState()
    data class Success(val uri: Uri) : QRState()
    data class Fail(val message: String) : QRState()
}

class QRViewModel : ViewModel() {
    val uiState: MutableLiveData<QRState> = MutableLiveData(QRState.Initial)
}
