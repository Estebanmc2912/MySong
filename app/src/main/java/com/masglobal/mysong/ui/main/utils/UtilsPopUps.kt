package com.masglobal.mysong.ui.main.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.view.View
import android.view.Window
import com.bumptech.glide.Glide
import com.masglobal.mysong.R
import com.masglobal.mysong.app.database.entitiy.SongEntity
import com.masglobal.mysong.app.database.entitiy.UserEntity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.alertdialog_charging.view.*
import kotlinx.android.synthetic.main.dialog_musiclist_itemview.view.*
import java.util.*


object UtilsPopUps {

    private lateinit var player : MediaPlayer

    fun listSongs(context: Context, listSongs: ArrayList<SongEntity>, itemSelected: Int){
        val view = View.inflate(context, R.layout.dialog_musiclist_itemview, null)
        val dialog = buildWarning(view, context)
        var currentItem = itemSelected
        var songEntity : SongEntity = listSongs.get(itemSelected)
        player = MediaPlayer()
        showSongInformation(view, songEntity, context)

        view.btn_song_next.setOnClickListener {
            if( !( ((currentItem+1) >= listSongs.size) or ((currentItem+1) < 0) ) ){
                if(currentItem+1!=listSongs.size){
                    currentItem++
                }
                songEntity = listSongs.get(currentItem)
                showSongInformation(view, songEntity, context)
            }
        }

        view.btn_song_back.setOnClickListener {
            if( !( ((currentItem-1) > listSongs.size) or ((currentItem-1) < 0 )) ){
                if(currentItem>0){ currentItem-- }
                else{ currentItem=0}
                songEntity = listSongs.get(currentItem)
                showSongInformation(view, songEntity, context)
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

    fun listUsers(context: Context, listUsers: ArrayList<UserEntity>, itemSelected: Int){
        val view = View.inflate(context, R.layout.dialog_musiclist_itemview, null)
        val dialog = buildWarning(view, context)
        var currentItem = itemSelected
        var userEntity = listUsers[itemSelected]

        showUserInformation(view, userEntity, context)

        view.btn_song_next.setOnClickListener {
            if( !( ((currentItem+1) >= listUsers.size) or ((currentItem+1) < 0) ) ){
                if(currentItem+1!=listUsers.size){
                    currentItem++
                }
                userEntity = listUsers.get(currentItem)
                showUserInformation(view, userEntity, context)
            }
        }

        view.btn_song_back.setOnClickListener {
            if( !( ((currentItem-1) > listUsers.size) or ((currentItem-1) < 0 )) ){
                if(currentItem>0){ currentItem-- }
                else{ currentItem=0}
                userEntity = listUsers.get(currentItem)
                showUserInformation(view, userEntity, context)
            }
        }

        view.alert_iv_songplay.visibility = View.GONE


    }

    private fun showUserInformation(view: View, userEntity: UserEntity, context: Context) {
        view.alert_iv_songplay.isActivated =  false
        if(userEntity.image.isNotBlank()){ Picasso.get().load(userEntity.image).into(view.alert_iv_song) }
        else{view.alert_iv_song.setImageResource(R.drawable.ic_user)}
        view.alert_tv_songname.text = userEntity.userId
        view.alert_tv_songartist.text = "Password: " + userEntity.password
        view.alert_tv_songartist.textAlignment = View.TEXT_ALIGNMENT_CENTER
        view.alert_tv_songgenre.visibility = View.INVISIBLE
        view.alert_tv_songdate.visibility = View.GONE
    }

    fun showSongInformation(view: View, song: SongEntity, context: Context){
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
        player.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )
        val uri = Uri.parse(song.songPreview)
        player.setDataSource(context, uri)
        player.prepareAsync()
    }


    fun charging(context: Context): Dialog {
        val view = View.inflate(context, R.layout.alertdialog_charging, null)
        val dialog = buildWarningTransparent(view, context)
        Glide.with(context).asGif().load(R.raw.loading_headphone).into(view.iv_loading);
        val t = Timer()

        t.schedule(object : TimerTask() {
            override fun run() {
                dialog.dismiss() // when the task active then close the dialog
                t.cancel() // also just top the timer thread, otherwise, you may receive a crash report
            }
        }, 4000)

        return dialog
    }

    fun buildWarning(view: View, context: Context) : AlertDialog {
        val builder = AlertDialog.Builder(context)
        builder.setView(view)

        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource((android.R.color.transparent))


        return dialog
    }

    fun buildWarningTransparent(view: View, context: Context) : Dialog{
        val alertDialog = Dialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog.setContentView(view)
        alertDialog.create()
        alertDialog.setCancelable(false)
        alertDialog.setCanceledOnTouchOutside(false)
        //alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //change color for background with android resources
        alertDialog.show()
        return alertDialog
    }

}