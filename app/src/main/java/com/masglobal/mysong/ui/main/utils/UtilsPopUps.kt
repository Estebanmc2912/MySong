package com.masglobal.mysong.ui.main.utils

import android.app.AlertDialog
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.view.View
import com.masglobal.mysong.R
import com.masglobal.mysong.ui.main.entities.SongEntity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dialog_musiclist_itemview.view.*


object UtilsPopUps {

    private lateinit var player : MediaPlayer

    fun listSongs(
        context: Context,
        listSongs: ArrayList<SongEntity>,
        itemSelected: Int
    ){
        val view = View.inflate(context, R.layout.dialog_musiclist_itemview, null)
        val dialog = buildWarning(view, context)
        var currentItem = itemSelected
        var songEntity : SongEntity = listSongs.get(itemSelected)
        player = MediaPlayer()
        showInformationPopUP(view, songEntity,context)

        view.btn_song_next.setOnClickListener {
            if( !( ((currentItem+1) >= listSongs.size) or ((currentItem+1) < 0) ) ){
                if(currentItem+1!=listSongs.size){
                    currentItem++
                }
                songEntity = listSongs.get(currentItem)
                showInformationPopUP(view, songEntity,context)
            }
        }

        view.btn_song_back.setOnClickListener {
            if( !( ((currentItem-1) > listSongs.size) or ((currentItem-1) < 0 )) ){
                if(currentItem>0){ currentItem-- }
                else{ currentItem=0}
                songEntity = listSongs.get(currentItem)
                showInformationPopUP(view, songEntity,context)
            }
        }

        view.alert_iv_songplay.setOnClickListener {
                view.alert_iv_songplay.isActivated =  !view.alert_iv_songplay.isActivated
               if (view.alert_iv_songplay.isActivated){
                    player.start()
                }
                else if(!view.alert_iv_songplay.isActivated){
                    player.pause()
                }
        }

        dialog.setOnDismissListener {
            if(player.isPlaying){
                player.stop()
            }
        }

    }

    fun showInformationPopUP(view: View, song: SongEntity, context: Context){
        if(player.isPlaying){
            player.stop()
        }
        view.alert_iv_songplay.isActivated =  false
        Picasso.get().load(song.songImage).into(view.alert_iv_song)
        view.alert_tv_songname.text = song.songName
        view.alert_tv_songartist.text = song.songArtist
        view.alert_tv_songgenre.text = song.songGenre
        view.alert_tv_songdate.text = song.songDate
        player = MediaPlayer()
        player.setAudioStreamType(AudioManager.STREAM_MUSIC)
        val uri = Uri.parse(song.songPreview)
        player.setDataSource(context, uri)
        player.prepare()
    }
    


    fun buildWarning(view: View, context: Context) : AlertDialog {
        val builder = AlertDialog.Builder(context)
        builder.setView(view)

        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource((android.R.color.transparent))


        return dialog
    }

}