package com.masglobal.mysong.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.masglobal.mysong.R
import com.masglobal.mysong.ui.main.entities.Song
import com.masglobal.mysong.ui.main.home.tophits.TopHitsFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.songhit_item.view.*

class TopHitsSongAdapter (fragment: TopHitsFragment, onItemClickListener : TopHitsSongAdapter.OnItemClickListener) : RecyclerView.Adapter<TopHitsSongAdapter.ViewHolder> () {

    private var songsList = fragment.songsHitsList
    private var onItemClickListener = onItemClickListener

    interface OnItemClickListener {
        fun onSongItemClicked(song: Song?, position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_track_name : TextView = itemView.tv_songhit_item_songname
        var tv_artist_name: TextView? = itemView.tv_songhit_item_artistname
        var iv_artwork100: ImageView? = itemView.iv_songhit_item_albumpicture
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopHitsSongAdapter.ViewHolder {


        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.songhit_item, parent, false))
    }

    override fun getItemCount(): Int {
        return songsList?.size!!
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (artistName, trackName, _, artworkUrl100, _, _, _, _, _, _, _, _, _) = songsList!![position]
        holder.tv_track_name?.text = trackName
        holder.tv_artist_name?.text = artistName
        Picasso.get().load(artworkUrl100).into(holder.iv_artwork100)
        holder.itemView.setOnClickListener { onItemClickListener?.onSongItemClicked(songsList!![position], position) }
    }



}