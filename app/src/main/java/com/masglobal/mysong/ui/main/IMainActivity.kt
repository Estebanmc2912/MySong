package com.masglobal.mysong.ui.main

import com.masglobal.mysong.ui.main.entities.SongEntity

interface IMainActivity {

fun onClickAddSongFavourite(songEntity: SongEntity)

fun onCLickDelSongFavourite(songEntity: SongEntity)

}