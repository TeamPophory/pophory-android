package com.teampophory.pophory.feature.home.photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.common.time.systemNow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import javax.inject.Inject

enum class AddPhotoEvent {
    DATE,
    STUDIO;
}

@HiltViewModel
class AddPhotoViewModel @Inject constructor() : ViewModel() {
    private val _createdAt = MutableStateFlow(Instant.systemNow().toEpochMilliseconds())
    val createdAt = _createdAt.asStateFlow()
    private var studio: String? = null
    private val _event = MutableSharedFlow<AddPhotoEvent>()
    val event = _event.asSharedFlow()

    fun onCreatedAtPressed() {
        viewModelScope.launch {
            _event.emit(AddPhotoEvent.DATE)
        }
    }

    fun onUpdateCreateAt(new: Long) {
        _createdAt.value = new
    }

    fun onStudioPressed() {
        viewModelScope.launch {
            _event.emit(AddPhotoEvent.STUDIO)
        }
    }
}
