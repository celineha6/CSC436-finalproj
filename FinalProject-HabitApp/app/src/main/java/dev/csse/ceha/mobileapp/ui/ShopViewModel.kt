package dev.csse.ceha.mobileapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.csse.ceha.mobileapp.MainApplication
import dev.csse.ceha.mobileapp.data.ShopItemEntity
import dev.csse.ceha.mobileapp.data.UserInventoryRepository

class ShopViewModel(
	userRepo: UserInventoryRepository
): ViewModel() {

	data class LibraryScreenUiState(
		val ownedItems: List<ShopItemEntity>
	)

	companion object {
		val Factory: ViewModelProvider.Factory = viewModelFactory {
			initializer {
				val application = (this[APPLICATION_KEY] as MainApplication)
				ShopViewModel(
					userRepo = application.userRepo
				)
			}
		}
	}
}