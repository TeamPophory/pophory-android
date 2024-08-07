package com.teampophory.pophory.feature.album.cover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.BuildConfig
import com.teampophory.pophory.ad.entity.AdIdentifier
import com.teampophory.pophory.ad.usecase.FetchAdConstantUseCase
import com.teampophory.pophory.data.network.model.album.AlbumCoverChangeRequest
import com.teampophory.pophory.designsystem.type.DesignSystemResources
import com.teampophory.pophory.domain.repository.photo.PhotoRepository
import com.teampophory.pophory.feature.album.cover.model.AlbumCoverItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumCoverEditViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val fetchAdConstantUseCase: FetchAdConstantUseCase,
) : ViewModel() {

    private val _albumEditState = MutableStateFlow<AlbumEditState>(AlbumEditState.Uninitialized)
    val albumEditState = _albumEditState.asStateFlow()

    private var currentDesignId = 0L

    fun getAlbumItemList(): List<AlbumCoverItem> {
        return listOf(
            AlbumCoverItem(
                AlbumCoverItem.AlbumTheme.FRIEND,
                DesignSystemResources.ic_album_cover_friends_1
            ),
            AlbumCoverItem(
                AlbumCoverItem.AlbumTheme.FRIEND,
                DesignSystemResources.ic_album_cover_friends_2
            ),
            AlbumCoverItem(
                AlbumCoverItem.AlbumTheme.LOVE,
                DesignSystemResources.ic_album_cover_love_1
            ),
            AlbumCoverItem(
                AlbumCoverItem.AlbumTheme.LOVE,
                DesignSystemResources.ic_album_cover_love_2
            ),
            AlbumCoverItem(
                AlbumCoverItem.AlbumTheme.MY_ALBUM,
                DesignSystemResources.ic_album_cover_me_1
            ),
            AlbumCoverItem(
                AlbumCoverItem.AlbumTheme.MY_ALBUM,
                DesignSystemResources.ic_album_cover_me_2
            ),
            AlbumCoverItem(
                AlbumCoverItem.AlbumTheme.FAMILY,
                DesignSystemResources.ic_album_cover_family_1
            ),
            AlbumCoverItem(
                AlbumCoverItem.AlbumTheme.FAMILY,
                DesignSystemResources.ic_album_cover_family_2
            ),
        )
    }

    fun patchAlbumCover(albumId: Long) {
        viewModelScope.launch {
            _albumEditState.emit(AlbumEditState.Loading)
            val request = AlbumCoverChangeRequest(currentDesignId)
            photoRepository.patchAlbumCover(albumId, request).onSuccess {
                _albumEditState.emit(AlbumEditState.Success)
            }.onFailure {
                _albumEditState.emit(AlbumEditState.Error(it))
            }
        }
    }

    fun updateCurrentPosition(currentDesignId: Long) {
        this.currentDesignId = currentDesignId + 1
    }

    suspend fun fetchAdConstant(adName: String): AdIdentifier? {
        return if (BuildConfig.DEBUG) {
            AdIdentifier("ca-app-pub-3940256099942544/5224354917", "DEBUG")
        } else {
            fetchAdConstantUseCase(adName)
        }
    }
}
