package com.masglobal.mysong.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.masglobal.mysong.R
import com.masglobal.mysong.app.connection.ApiClient
import com.masglobal.mysong.app.connection.ApiInterface
import com.masglobal.mysong.ui.main.adapters.TopHitsSongAdapter
import com.masglobal.mysong.ui.main.adapters.SearchSongAdapter
import com.masglobal.mysong.ui.main.entities.FeedResponse
import com.masglobal.mysong.ui.main.entities.Song
import com.masglobal.mysong.ui.main.entities.SongResponse
import com.masglobal.mysong.ui.main.login.LoginActivity
import com.masglobal.mysong.ui.main.search.SearchFragment
import com.masglobal.mysong.ui.main.song.SongFragment
import com.masglobal.mysong.ui.main.tophits.TopHitsFragment
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchFragment = SearchFragment()
        val topHitsFragment = TopHitsFragment()

        makeCurrentFragment(searchFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_search -> makeCurrentFragment(searchFragment)
                R.id.ic_tophits -> makeCurrentFragment(topHitsFragment)
            }
            true
        }

        setListeners()

    }

    private fun setListeners() {
        iv_main_user.setOnClickListener {
            startActivity(Intent(this@MainActivity,LoginActivity::class.java))
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_main, fragment)
            commit()
        }

}