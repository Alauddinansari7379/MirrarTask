package com.example.mirrartask.roomDatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel(application: Application): AndroidViewModel(application) {

    private val dao = MyDatabase.getDatabase(application).myDao()
    private val repository = MyRepository(dao)

    val readData: LiveData<List<Table>> = repository.readData

    fun insertPerson(person: Table){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(person)
        }
    }

    fun delete(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete()
        }
    }

}