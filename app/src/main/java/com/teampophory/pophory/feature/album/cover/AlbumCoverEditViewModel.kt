package com.teampophory.pophory.feature.album.cover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teampophory.pophory.R
import com.teampophory.pophory.ad.entity.AdConstant
import com.teampophory.pophory.ad.usecase.FetchAdConstantUseCase
import com.teampophory.pophory.data.network.model.album.AlbumCoverChangeRequest
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
            AlbumCoverItem(AlbumCoverItem.AlbumTheme.FRIEND, R.drawable.ic_album_cover_friends_1),
            AlbumCoverItem(AlbumCoverItem.AlbumTheme.FRIEND, R.drawable.ic_album_cover_friends_2),
            AlbumCoverItem(AlbumCoverItem.AlbumTheme.LOVE, R.drawable.ic_album_cover_love_1),
            AlbumCoverItem(AlbumCoverItem.AlbumTheme.LOVE, R.drawable.ic_album_cover_love_2),
            AlbumCoverItem(AlbumCoverItem.AlbumTheme.MY_ALBUM, R.drawable.ic_album_cover_me_1),
            AlbumCoverItem(AlbumCoverItem.AlbumTheme.MY_ALBUM, R.drawable.ic_album_cover_me_2),
            AlbumCoverItem(AlbumCoverItem.AlbumTheme.FAMILY, R.drawable.ic_album_cover_family_1),
            AlbumCoverItem(AlbumCoverItem.AlbumTheme.FAMILY, R.drawable.ic_album_cover_family_2),
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

    suspend fun getAdConstant(adName: String): AdConstant? {
        return fetchAdConstantUseCase(adName)
    }
}
