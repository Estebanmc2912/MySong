package com.masglobal.mysong.ui.main

import com.masglobal.mysong.app.database.entitiy.SongEntity

interface IMainActivity {

fun onClickAddSongFavourite(songEntity: SongEntity)

fun onCLickDelSongFavourite(songEntity: SongEntity)

fun onCLickAddUserAdmin()

}