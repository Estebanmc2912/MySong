package com.masglobal.mysong.app.database.dao

import androidx.room.Embedded
import androidx.room.Relation
import com.masglobal.mysong.app.database.entitiy.UserEntity
import com.masglobal.mysong.app.database.entitiy.SongEntity

data class UserWithSongsDao (

   @Embedded val user : UserEntity,
   @Relation(
       parentColumn = "userId",
       entityColumn = "userId",
   )
   val songs : List<SongEntity>

)