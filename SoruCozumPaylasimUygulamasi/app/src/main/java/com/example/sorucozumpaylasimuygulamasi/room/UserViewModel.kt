package com.example.sorucozumpaylasimuygulamasi.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val userRepository = UserRepository(db.userDao())

    fun insertUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.insertUser(user)
        }
    }

    fun getUser(username: String): LiveData<User?> {
        val result = MutableLiveData<User?>()
        viewModelScope.launch(Dispatchers.IO) {
            result.postValue(userRepository.getUser(username))
        }
        return result
    }
}
