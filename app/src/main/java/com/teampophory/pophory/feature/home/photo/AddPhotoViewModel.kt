package com.teampophory.pophory.feature.home.photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.common.time.systemNow
import com.teampophory.pophory.data.model.photo.Studio
import com.teampophory.pophory.data.repository.photo.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import timber.log.Timber
import javax.inject.Inject

enum class AddPhotoEvent {
    DATE,
    STUDIO;
}

@HiltViewModel
class AddPhotoViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
) : ViewModel() {
    private val _createdAt = MutableStateFlow(Instant.systemNow().toEpochMilliseconds())
    val createdAt = _createdAt.asStateFlow()
    var allStudios: List<Studio> = emptyList()
        private set
    private var currentStudio: MutableStateFlow<Set<Studio>> = MutableStateFlow(emptySet())
    private val _event = MutableSharedFlow<AddPhotoEvent>()
    val event = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            photoRepository.getStudios()
                .onSuccess {
                    allStudios = it
                }.onFailure(Timber::e)
        }
    }

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

    fun onUpdateStudio(new: Studio) {
        if (currentStudio.value.contains(new)) {
            currentStudio.value = currentStudio.value - new
            return
        }
        if (currentStudio.value.isNotEmpty()) {
            return
        }
        currentStudio.value = currentStudio.value + new
    }
}
