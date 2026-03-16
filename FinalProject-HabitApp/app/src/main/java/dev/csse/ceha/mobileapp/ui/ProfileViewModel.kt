package dev.csse.ceha.mobileapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.csse.ceha.mobileapp.MainApplication
import dev.csse.ceha.mobileapp.data.ShopItemEntity
import dev.csse.ceha.mobileapp.data.UserInfo
import dev.csse.ceha.mobileapp.data.UserInventoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

data class ProfileScreenUiState(
	val userInfo: UserInfo = UserInfo(),
	val items: List<ShopItemEntity> = emptyList()
)

class ProfileViewModel(
	val userRepo: UserInventoryRepository
): ViewModel() {
	val items: Flow<List<ShopItemEntity>> = userRepo.getShopItems()

	val combinedFlow: Flow<ProfileScreenUiState> =
		combine(
			userRepo.userFlow,
			items
		) { u, i ->
			ProfileScreenUiState(u, i)
		}

	val uiState: StateFlow<ProfileScreenUiState> =
		combinedFlow
			.stateIn(
				scope = viewModelScope,
				started = SharingStarted.WhileSubscribed(5000L),
				initialValue = ProfileScreenUiState(),
			)

	fun addShopItem(item: ShopItem) {
		val itemEntity = ShopItemEntity(
			name = item.name,
			cost = item.cost,
			iconRes = item.iconRes,
			description = item.description
		)
		userRepo.addShopItem(itemEntity)

		viewModelScope.launch {
			userRepo.updateGold(-item.cost)
		}
	}

	companion object {
		val Factory: ViewModelProvider.Factory = viewModelFactory {
			initializer {
				val application = (this[APPLICATION_KEY] as MainApplication)
				ProfileViewModel(
					userRepo = application.userRepo
				)
			}
		}
	}
}