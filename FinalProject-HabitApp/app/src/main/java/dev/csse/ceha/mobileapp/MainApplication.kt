package dev.csse.ceha.mobileapp

import android.app.Application
import dev.csse.ceha.mobileapp.data.UserInventoryRepository

class MainApplication: Application() {
	lateinit var userRepo: UserInventoryRepository

	override fun onCreate() {
		super.onCreate()

		userRepo = UserInventoryRepository(applicationContext)
	}
}