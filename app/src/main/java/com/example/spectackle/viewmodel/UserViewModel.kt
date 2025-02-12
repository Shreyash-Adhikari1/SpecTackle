package com.example.spectackle.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.spectackle.model.UserModel
import com.example.spectackle.repository.UserRepository
import com.example.spectackle.utils.Resource

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _registerStatus = MutableLiveData<Resource<UserModel>>()
    val registerStatus: LiveData<Resource<UserModel>> get() = _registerStatus

    private val _loginStatus = MutableLiveData<Resource<Unit>>()
    val loginStatus: LiveData<Resource<Unit>> get() = _loginStatus

    fun register(email: String, password: String) {
        _registerStatus.value = Resource.Loading()
        userRepository.register(email, password) { result ->
            _registerStatus.postValue(result)
        }
    }

    fun login(email: String, password: String) {
        _loginStatus.value = Resource.Loading()
        userRepository.login(email, password) { result ->
            _loginStatus.postValue(result)
        }
    }

    class Factory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UserViewModel(userRepository) as T
        }
    }
}
