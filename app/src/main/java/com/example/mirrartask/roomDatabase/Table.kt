package com.example.mirrartask.roomDatabase

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_table")
data class Table(
    val title: String,
    val date: String,
    val image: Bitmap
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}