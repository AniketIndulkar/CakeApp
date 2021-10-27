package com.task.cakeapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Data class holding all the Variables for cake
//We could add id param in data class but it will increase complications like duplicate Data, which we can avoid but in this case Id was not needed
@Entity(tableName = "Cake")
data class CakeModel(
    @PrimaryKey
    var title: String,
    var desc: String,
    var image: String ){
    override fun toString(): String {
        return title
    }
}
