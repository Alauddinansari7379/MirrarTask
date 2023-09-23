package com.example.mirrartask.roomDatabase

import androidx.lifecycle.LiveData

class MyRepository(private val myDao: MyDao) {

    val readData: LiveData<List<Table>> = myDao.readData()

    suspend fun insertData(data: Table){
        myDao.insertData(data)
    }

     fun delete(){
        myDao.delete()
    }

}