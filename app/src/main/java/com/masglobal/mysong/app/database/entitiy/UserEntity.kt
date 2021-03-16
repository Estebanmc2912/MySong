package com.masglobal.mysong.app.database.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.masglobal.mysong.ui.main.entities.SongEntity

@Entity(tableName = "users")
data class UserEntity (
    @PrimaryKey(autoGenerate = true)
    var id : Int,

    @ColumnInfo(name = "userId")
    var userId : String,

    @ColumnInfo(name = "password")
    var password : String,

    @ColumnInfo(name = "image")
    var image: String,

    )