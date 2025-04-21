package com.example.sorucozumpaylasimuygulamasi.room

class UserRepository(private var userDao: UserDao) {
    fun insertUser(user: User){
        userDao.insertUser(user)
    }

    fun getUser(username: String): User? {
        return userDao.getUser(username)
    }
}