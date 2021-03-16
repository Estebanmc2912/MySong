package com.masglobal.mysong.ui.main.adapters


import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.masglobal.mysong.R
import com.masglobal.mysong.ui.main.IMainActivity
import com.masglobal.mysong.ui.main.MainActivity
import com.masglobal.mysong.ui.main.entities.Song
import com.masglobal.mysong.ui.main.entities.SongEntity
import com.masglobal.mysong.ui.main.search.SearchFragment
import com.masglobal.mysong.ui.main.utils.UtilsConversions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.song_item.view.*


class SearchSongAdapter (context : Context, fragment: SearchFragment, onItemClickListener : OnItemClickListener) : RecyclerView.Adapter<SearchSongAdapter.ViewHolder> () {

    private var context = context
    private var songsList = fragment.listSongsMutableList
    private var onItemClickListener = onItemClickListener
    lateinit var iMainActivity : IMainActivity

    interface OnItemClickListener {
        fun onSongItemClicked(song: Song?, position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_track_name : TextView = itemView.tv_song_item_songname
        var tv_artist_name: TextView? = itemView.tv_song_item_artistname
        var tv_genre_name: TextView? = itemView.tv_song_item_genre
        var tv_track_time: TextView? = itemView.tv_song_item_time
        var iv_artwork100: ImageView? = itemView.iv_song_item_albumpicture
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchSongAdapter.ViewHolder {
        iMainActivity = context as IMainActivity
       return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.song_item, parent, false))
    }

    override fun getItemCount(): Int {
        return songsList?.size!!
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

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


        val (artistName, trackName, _, artworkUrl100, _, trackTimeMillis, _, _, _, _, releaseDate, previewUrl, primaryGenreName) = songsList!![position]
        holder.tv_track_name?.text = trackName
        holder.tv_artist_name?.text = artistName
        holder.tv_genre_name?.text = primaryGenreName
        if(!trackTimeMillis.isNullOrBlank()){holder.tv_track_time?.text = UtilsConversions.millisToString(trackTimeMillis?.toDouble()!!)}
        Picasso.get().load(artworkUrl100).into(holder.iv_artwork100)
        holder.itemView.setOnClickListener { onItemClickListener?.onSongItemClicked(songsList!![position], position) }
        holder.itemView.btn_song_item_favorite.setOnClickListener {

            holder.itemView.btn_song_item_favorite.isActivated = !holder.itemView.btn_song_item_favorite.isActivated
            Log.d("state heart", (holder.itemView.btn_song_item_favorite.isActivated).toString())
            var song = SongEntity(0, "",
                trackName,
                artistName,
                artworkUrl100,
                primaryGenreName,
                releaseDate,
                previewUrl)
            if(holder.itemView.btn_song_item_favorite.isActivated){
                Log.d("heart", "addeeeeeeeeeeeeeeeeeed--------------------------------------------")
                iMainActivity.onClickAddSongFavourite(song)
            } else{
                iMainActivity.onCLickDelSongFavourite(song)
            }


        }

    }



}