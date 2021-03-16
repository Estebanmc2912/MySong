package com.masglobal.mysong.app.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.masglobal.mysong.app.database.entitiy.UserEntity
import com.masglobal.mysong.ui.main.entities.SongEntity

@Dao
interface UserDao {

    @Insert
    fun registerUser(userEntity : UserEntity)

    @Query("SELECT * FROM users where userId = :userId and password= :password")
    fun loginUser(userId : String, password:String) : UserEntity

    @Query("SELECT * FROM users where userId = :userId")
    fun searchUser(userId : String) : UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSong(song: SongEntity)

    @Query("SELECT * FROM song where userId = :userId")
    fun getSongsWithUser(userId: String) : List<SongEntity>

}