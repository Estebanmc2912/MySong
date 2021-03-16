package com.masglobal.mysong.app.database.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users", indices = [Index(value = ["id", "userId"], unique = true)])
data class UserEntity (
    @PrimaryKey(autoGenerate = true)
    var id : Int,

    @ColumnInfo(name = "userId")
    var userId : String,

    @ColumnInfo(name = "password")
    var password : String,

    @ColumnInfo(name = "image")
    var image: String,

    @ColumnInfo(name = "role")
    var role: String)