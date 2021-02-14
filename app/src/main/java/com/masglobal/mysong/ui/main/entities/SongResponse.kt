package com.masglobal.mysong.ui.main.entities

import com.google.gson.annotations.SerializedName

data class SongResponse (@SerializedName("results") var results : List<Song>)