package com.masglobal.mysong.ui.main.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "song")
data class SongEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Int,

    var userId : String,

    @ColumnInfo(name = "songName")
    var songName : String,

    @ColumnInfo(name = "songArtist")
    var songArtist : String,

    @ColumnInfo(name = "songImage")
    var songImage : String,

    @ColumnInfo(name = "songGenre")
    var songGenre : String,

    @ColumnInfo(name = "songDate")
    var songDate : String,

    @ColumnInfo(name = "songPreview")
    var songPreview : String){
    constructor(songName: String, songArtist : String, songImage : String, songGenre : String,songDate : String,songPreview : String) :
            this(0, "",songName, songArtist, songImage, songGenre, songDate, songPreview)
}
