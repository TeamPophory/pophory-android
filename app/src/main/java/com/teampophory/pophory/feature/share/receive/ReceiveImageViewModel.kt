package com.teampophory.pophory.feature.share.receive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.common.qualifier.Secured
import com.teampophory.pophory.common.qualifier.Unsecured
import com.teampophory.pophory.auth.repository.PophoryDataStore
import com.teampophory.pophory.data.network.model.share.AcceptShareResponse
import com.teampophory.pophory.data.network.model.share.SharePhotoResponse
import com.teampophory.pophory.data.network.service.ShareService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

sealed interface ReceiveImageUiState {
    data object Init : ReceiveImageUiState
    data object Loading : ReceiveImageUiState
    data class Photo(
        val realName: String,
        val nickName: String,
        val photoId: Long,
        val imageUrl: String
    ) : ReceiveImageUiState {
        companion object {
            fun of(response: SharePhotoResponse) = Photo(
                realName = response.realName,
                nickName = response.nickname,
                photoId = response.photoId,
                imageUrl = response.imageUrl
            )
        }
    }

    data class Accepted(
        val albumId: Long
    ) : ReceiveImageUiState {
        companion object {
            fun of(response: AcceptShareResponse) = Accepted(response.albumId)
        }
    }

    data object SignUp : ReceiveImageUiState

    data class Error(val exception: Throwable) : ReceiveImageUiState
}

@HiltViewModel
class ReceiveImageViewModel @Inject constructor(
    @Secured private val authorizedService: ShareService,
    @Unsecured private val unAuthorizedService: ShareService,
    private val dataStore: PophoryDataStore
) : ViewModel() {
    private val _uiState: MutableStateFlow<ReceiveImageUiState> =
        MutableStateFlow(ReceiveImageUiState.Init)
    val uiState = _uiState.asStateFlow()

    fun loadUiData(shareId: String) {
        viewModelScope.launch {
            _uiState.value = ReceiveImageUiState.Loading
            runCatching {
                unAuthorizedService.getPhotoInfo(shareId)
            }.onSuccess {
                _uiState.value = ReceiveImageUiState.Photo.of(it)
            }.onFailure {
                Timber.e(it)
                _uiState.value = ReceiveImageUiState.Error(it)
            }
        }
    }

    fun onAccepted() {
        if (uiState.value !is ReceiveImageUiState.Photo) {
            return
        }
        if (dataStore.accessToken.isEmpty()) {
            _uiState.value = ReceiveImageUiState.SignUp
            return
        }
        val photoState = uiState.value as ReceiveImageUiState.Photo
        _uiState.value = ReceiveImageUiState.Loading
        viewModelScope.launch {
            runCatching {
                authorizedService.acceptShare(photoState.photoId)
            }.onSuccess {
                _uiState.value = ReceiveImageUiState.Accepted.of(it)
            }.onFailure {
                _uiState.value = ReceiveImageUiState.Error(it)
            }
        }
    }
}
