package com.example.spectackle.viewmodel
import androidx.lifecycle.ViewModel
import com.example.spectackle.model.UserModel
import com.example.spectackle.repository.UserRepository

import com.google.firebase.auth.FirebaseUser

//pass the repository that is to be mediated in the repository
class UserViewModel(val repo: UserRepository):ViewModel() {

    fun login(email:String, password:String,
              callback: (Boolean,String)->Unit){
        repo.login(email,password,callback)
    }

    fun signup(email:String, password:String,
               callback: (Boolean,String,String)->Unit){
        repo.signup(email,password,callback)
    }

    fun addUserToDatabase(userId: String, userModel: UserModel,
                          callback: (Boolean, String) -> Unit){
        repo.addUserToDatabase(userId,userModel,callback)
    }

    fun forgetPassword(email: String,
                       callback: (Boolean, String) -> Unit){
        repo.forgetPassword(email,callback)
    }

    fun getCurrentUser(): FirebaseUser?{
        return repo.getCurrentUser()
    }

}
