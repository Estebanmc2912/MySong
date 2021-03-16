package com.masglobal.mysong.ui.main.favourite

import android.content.Context
import android.os.Bundle
import android.os.Looper
import android.os.PersistableBundle
import android.view.View
import android.widget.GridLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.masglobal.mysong.R
import com.masglobal.mysong.app.database.UserDatabase
import com.masglobal.mysong.app.database.dao.UserDao
import com.masglobal.mysong.ui.main.adapters.FavouritesSongAdapter
import com.masglobal.mysong.ui.main.adapters.TopHitsSongAdapter
import com.masglobal.mysong.ui.main.entities.Song
import com.masglobal.mysong.ui.main.entities.SongEntity
import com.masglobal.mysong.ui.main.user.UserOptions
import com.masglobal.mysong.ui.main.utils.UtilsPopUps
import kotlinx.android.synthetic.main.activity_favourites.*
import kotlinx.android.synthetic.main.fragment_top_hits.view.*

class FavouriteActivity : AppCompatActivity(),FavouritesSongAdapter.OnItemClickListener {

    lateinit var adapterFavourites : FavouritesSongAdapter
    lateinit var dao : UserDao
    lateinit var listSongs : List<SongEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)
        dao = UserDatabase.getInstance(this).userDao()

        loadFavourites()
        setListeners()

    }

    private fun loadFavourites() {
        val thread = Thread(Runnable() {
            kotlin.run {
                Looper.prepare()
                listSongs = dao.getSongsWithUser(UserOptions.userId)
                if(listSongs.isNotEmpty()){
                    showHitsSongs()
                }

                try {
                    Thread.sleep(10)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                Looper.loop()
            }
        })
        thread!!.start()
    }

    private fun setListeners() {
    }


    fun showHitsSongs(){
        if(!(::adapterFavourites.isInitialized)) {
            setupRecycler()
            adapterFavourites = FavouritesSongAdapter(listSongs as ArrayList<SongEntity>, this)
            rv_favourites.adapter = adapterFavourites
            adapterFavourites!!.notifyDataSetChanged()
        }
    }

    private fun setupRecycler() {
        rv_favourites.layoutManager = GridLayoutManager(this,2)
    }


    override fun onSongItemClicked(song: SongEntity?, position: Int) {
        UtilsPopUps.listSongs(this@FavouriteActivity, listSongs as ArrayList<SongEntity>,position)

    }

    override fun onBackPressed() {
        this.finish()
    }

}