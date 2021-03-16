package com.masglobal.mysong.app.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.masglobal.mysong.app.database.dao.UserDao
import com.masglobal.mysong.app.database.entitiy.UserEntity
import com.masglobal.mysong.ui.main.entities.SongEntity

val dbName = "user"

@Database(entities = [UserEntity::class,
                     SongEntity::class], version = 2, exportSchema = false)
abstract class UserDatabase : RoomDatabase(){

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        dbName
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    abstract fun userDao() : UserDao
}