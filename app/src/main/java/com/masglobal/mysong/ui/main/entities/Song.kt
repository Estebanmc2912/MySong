package com.masglobal.mysong.ui.main.entities

import com.google.gson.annotations.SerializedName

data class Song (
    @SerializedName("artistName") var artistName : String,
    @SerializedName("trackName") var trackName : String,
    @SerializedName("artworkUrl30") var artworkUrl30 : String,
    @SerializedName("artworkUrl100") var artworkUrl100 : String,
    @SerializedName("trackPrice") var trackPrice : String,
    @SerializedName("trackTimeMillis") var trackTimeMillis : String,
    @SerializedName("collectionName") var collectionName : String,
    @SerializedName("collectionViewUrl") var collectionViewUrl : String,
    @SerializedName("trackViewUrl") var trackViewUrl : String,
    @SerializedName("collectionPrice") var collectionPrice : String,
    @SerializedName("releaseDate") var releaseDate : String,
    @SerializedName("previewUrl") var previewUrl : String,
    @SerializedName("primaryGenreName") var primaryGenreName : String,
    @SerializedName("trackId") var trackId : String )

