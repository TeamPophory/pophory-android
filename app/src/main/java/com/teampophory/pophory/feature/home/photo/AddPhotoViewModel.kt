package com.teampophory.pophory.feature.home.photo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.common.image.ContentUriRequestBody
import com.teampophory.pophory.common.time.systemNow
import com.teampophory.pophory.data.model.photo.Studio
import com.teampophory.pophory.data.repository.photo.PhotoRepository
import com.teampophory.pophory.feature.home.photo.model.StudioUiModel
import com.teampophory.pophory.feature.home.photo.model.toUiModel
import com.teampophory.pophory.feature.home.store.model.AlbumItem
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

enum class AddPhotoEvent {
    DATE,
    STUDIO,
    ADD_SUCCESS,
    REQUEST_ERROR;
}

@HiltViewModel
class AddPhotoViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var imageRequestBody: ContentUriRequestBody? = null
    private val _createdAt = MutableStateFlow(Instant.systemNow().toEpochMilliseconds())
    val createdAt = _createdAt.asStateFlow()
    private val allStudio = MutableStateFlow<List<Studio>>(emptyList())
    private val _currentStudio: MutableStateFlow<Set<StudioUiModel>> = MutableStateFlow(emptySet())
    val currentStudio = _currentStudio.asStateFlow()
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
        _currentStudio.value = setOf(new)
    }

    fun onUpdateImage(imageRequestBody: ContentUriRequestBody) {
        this.imageRequestBody = imageRequestBody
    }

    fun onSubmit() {
        viewModelScope.launch {
            photoRepository.addPhoto(
                albumId = savedStateHandle.get<AlbumItem>("albumCover")?.id ?: -1,
                studioId = currentStudio.value.firstOrNull()?.id ?: -1L,
                takenAt = SimpleDateFormat(
                    "yyyy.MM.dd",
                    Locale.getDefault()
                ).format(Date(createdAt.value)),
                photo = imageRequestBody
                    ?: throw IllegalStateException("Pophory: ImageRequestBody is null")
            ).onSuccess {
                _event.emit(AddPhotoEvent.ADD_SUCCESS)
            }.onFailure {
                Timber.e(it)
                _event.emit(AddPhotoEvent.REQUEST_ERROR)
            }
        }
    }
}