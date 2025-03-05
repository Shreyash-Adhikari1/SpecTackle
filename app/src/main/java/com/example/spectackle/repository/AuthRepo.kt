package com.example.spectackle.repository

interface AuthRepo {

    fun login(email:String, password:String,
              callback: (Boolean,String)->Unit)

    fun signup(email:String, password:String,
               callback: (Boolean,String,String)->Unit)
}