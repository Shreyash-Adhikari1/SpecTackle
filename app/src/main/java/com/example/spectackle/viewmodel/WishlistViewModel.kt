package com.example.spectackle.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spectackle.model.WishlistModel
import com.example.spectackle.repository.WishlistRepository


class WishlistViewModel(private val repo: WishlistRepository) : ViewModel() {

    private val _wishlistItems = MutableLiveData<List<WishlistModel>?>()
    val wishlistItems: LiveData<List<WishlistModel>?>
        get() = _wishlistItems

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun addToWishlist(wishlistModel: WishlistModel) {
        _loading.value = true
        repo.addToWishlist(wishlistModel) { success, message ->
            _loading.value = false
            if (success) {
                getWishlistItems(wishlistModel.userId)
            } else {
                _errorMessage.value = message
            }
        }
    }

    fun removeFromWishlist(wishlistId: String, userId: String) {
        _loading.value = true
        repo.removeFromWishlist(wishlistId) { success, message ->
            _loading.value = false
            if (success) {
                getWishlistItems(userId)
            } else {
                _errorMessage.value = message
            }
        }
    }

    fun updateWishlistItem(wishlistId: String, quantity: Int, userId: String) {
        _loading.value = true
        repo.updateWishlistItem(wishlistId, quantity) { success, message ->
            _loading.value = false
            if (success) {
                getWishlistItems(userId)
            } else {
                _errorMessage.value = message
            }
        }
    }

    fun getWishlistItems(userId: String) {
        _loading.value = true
        repo.getWishlistItems(userId) { wishlistItems, success, message ->
            _loading.value = false
            if (success) {
                _wishlistItems.value = wishlistItems
            } else {
                _errorMessage.value = message
            }
        }
    }
}
