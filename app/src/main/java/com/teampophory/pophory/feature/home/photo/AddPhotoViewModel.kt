package com.teampophory.pophory.feature.home.photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.common.time.systemNow
import com.teampophory.pophory.data.model.photo.Studio
import com.teampophory.pophory.data.repository.photo.PhotoRepository
import com.teampophory.pophory.feature.home.photo.model.StudioUiModel
import com.teampophory.pophory.feature.home.photo.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
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
    private val allStudio = MutableStateFlow<List<Studio>>(emptyList())
    private val currentStudio: MutableStateFlow<Set<StudioUiModel>> = MutableStateFlow(emptySet())
    val studio = allStudio.combine(currentStudio) { all, current ->
        all.map { it.toUiModel(current.any { item -> item.id == it.id }) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000L), emptyList())
    private val _event = MutableSharedFlow<AddPhotoEvent>()
    val event = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            photoRepository.getStudios()
                .onSuccess {
                    allStudio.value = it
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

    fun onUpdateStudio(new: StudioUiModel) {
        currentStudio.value = setOf(new)
    }
}
