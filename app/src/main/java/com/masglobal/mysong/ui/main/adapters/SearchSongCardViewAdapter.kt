package com.masglobal.mysong.ui.main.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.masglobal.mysong.R
import com.masglobal.mysong.ui.main.MainActivity
import com.masglobal.mysong.ui.main.entities.Song
import com.masglobal.mysong.ui.main.utils.UtilsConversions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.song_item.view.*


class SearchSongCardViewAdapter (activity: MainActivity, onItemClickListener : OnItemClickListener) : RecyclerView.Adapter<SearchSongCardViewAdapter.ViewHolder> () {

    private var songsList = activity.listSongsMutableList
    private var onItemClickListener = onItemClickListener

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchSongCardViewAdapter.ViewHolder {
       return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.song_item, parent, false))
    }

    override fun getItemCount(): Int {
        return songsList?.size!!
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (artistName, trackName, _, artworkUrl100, _, trackTimeMillis, _, _, _, _, _, _, primaryGenreName) = songsList!![position]
        holder.tv_track_name?.text = trackName
        holder.tv_artist_name?.text = artistName
        holder.tv_genre_name?.text = primaryGenreName
        if(!trackTimeMillis.isNullOrBlank()){holder.tv_track_time?.text = UtilsConversions.millisToString(trackTimeMillis?.toDouble()!!)}
        Picasso.get().load(artworkUrl100).into(holder.iv_artwork100)
        holder.itemView.setOnClickListener { onItemClickListener?.onSongItemClicked(songsList!![position], position) }
        holder.itemView.btn_song_item_favorite.setOnClickListener { holder.itemView.btn_song_item_favorite.isActivated }

    }



}