package com.amar3ndar.Wishlists

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amar3ndar.Wishlists.data.Wish
import com.amar3ndar.Wishlists.data.WishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WishViewModel(
    private val wishRepository: WishRepository = Graph.wishRepository
): ViewModel() {

    var wishtTitleState by mutableStateOf("")
    var wishDescriptionState by mutableStateOf("")

    fun onWishTitleChanged(newString: String) {
        wishtTitleState = newString

    }

    fun onWishDescriptionChanged(newString: String) {
        wishDescriptionState = newString
    }

    lateinit var getAllWishes: Flow<List<Wish>>
    //lateinit is used to initialize a variable later

    init {
        viewModelScope.launch { //coroutine scope
            getAllWishes = wishRepository.getWishes()
        }
    }

    fun addWish(wish: Wish){
        viewModelScope.launch (Dispatchers.IO){ //coroutine scope
            wishRepository.addAWish(wish=wish)
        }
    }

    fun getAWishById(id: Long): Flow<Wish> {
        return wishRepository.getAWishById(id)
    }

    fun updateWish(wish: Wish){
        viewModelScope.launch (Dispatchers.IO){ //coroutine scope
            wishRepository.updateAWish(wish=wish)
        }
    }

    fun deleteWish(wish: Wish){
        viewModelScope.launch (Dispatchers.IO){ //coroutine scope
            wishRepository.deleteAWish(wish=wish)
        }
    }

}


