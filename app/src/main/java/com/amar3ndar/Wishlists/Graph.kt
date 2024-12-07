package com.amar3ndar.Wishlists

import android.content.Context
import androidx.room.Room
import com.amar3ndar.Wishlists.data.WishDatabase
import com.amar3ndar.Wishlists.data.WishRepository

object Graph {

    // This property was causing the issue because it was accessed before initialization.
    // Change: Ensure proper initialization before usage.
    lateinit var database: WishDatabase

    // Lazily initialize the WishRepository, but make sure `database` is initialized first.
    val wishRepository: WishRepository by lazy {
        WishRepository(wishDao = database.wishDao())
    }

    // Change: Properly assign the database to the lateinit property here.
    fun provide(context: Context) {
        // Initialize the database and assign it to the 'database' property.
        database = Room.databaseBuilder(
            context.applicationContext,   // Ensure using application context to avoid memory leaks.
            WishDatabase::class.java,
            "wishlist.db"
        ).build()
    }
}
