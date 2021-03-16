package com.masglobal.mysong.ui.main.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.masglobal.mysong.R
import com.masglobal.mysong.ui.main.IMainActivity
import com.masglobal.mysong.ui.main.entities.Song
import com.masglobal.mysong.ui.main.entities.SongEntity
import com.masglobal.mysong.ui.main.search.SearchFragment
import com.masglobal.mysong.ui.main.tophits.TopHitsFragment
import com.masglobal.mysong.ui.main.utils.UtilsConversions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.favourite_item.view.*
import kotlinx.android.synthetic.main.song_item.view.*
import kotlinx.android.synthetic.main.songhit_item.view.*

class FavouritesSongAdapter (songsList: ArrayList<SongEntity>, onItemClickListener : FavouritesSongAdapter.OnItemClickListener) : RecyclerView.Adapter<FavouritesSongAdapter.ViewHolder> () {

    private var songsList = songsList
    private var onItemClickListener = onItemClickListener

    interface OnItemClickListener {
        fun onSongItemClicked(song: SongEntity?, position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_track_name : TextView = itemView.tv_favourite
        var iv_artwork100: ImageView? = itemView.iv_favourite
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesSongAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.favourite_item, parent, false))
    }

    override fun getItemCount(): Int {
        return songsList?.size!!
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_track_name?.text = songsList[position].songName
        Picasso.get().load(songsList[position].songImage).into(holder.iv_artwork100)
        holder.itemView.setOnClickListener { onItemClickListener?.onSongItemClicked(songsList[position], position) }
    }



}