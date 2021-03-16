package com.masglobal.mysong.app.database.dao

import androidx.room.*
import com.masglobal.mysong.app.database.entitiy.UserEntity
import com.masglobal.mysong.app.database.entitiy.SongEntity

@Dao
interface UserDao {


    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun registerUser(userEntity : UserEntity)

    @Query("SELECT * FROM users where userId = :userId and password= :password")
    fun loginUser(userId : String, password:String) : UserEntity

    @Query("SELECT * FROM users where userId = :userId")
    fun searchUser(userId : String) : UserEntity

    @Update
    fun updateUser(userEntity: UserEntity)

    @Delete
    fun deleteUser(userEntity: UserEntity)

    @Query("SELECT * FROM users where role = :role")
    fun getUsers(role:String) : List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSong(song: SongEntity)


    @Query("SELECT * FROM song where userId = :userId")
    fun getSongsWithUser(userId: String) : List<SongEntity>

}