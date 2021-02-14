package com.masglobal.mysong.ui.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.masglobal.mysong.R
import com.masglobal.mysong.app.connection.ApiClient
import com.masglobal.mysong.app.connection.ApiInterface
import com.masglobal.mysong.ui.main.adapters.SearchSongCardViewAdapter
import com.masglobal.mysong.ui.main.entities.Song
import com.masglobal.mysong.ui.main.entities.SongResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), SearchSongCardViewAdapter.OnItemClickListener {

    lateinit var songsList: MutableList<Song>
    var listSongsMutableList : MutableList<Song> = ArrayList()
    var apiclient : ApiClient ?= ApiClient()
    lateinit var adapter : SearchSongCardViewAdapter
    lateinit var layoutManager: LinearLayoutManager
    lateinit var artistname : String
    var userFilter : Int ?= null
    var page = 0
    var isLoading = false
    var limitPaging : Int = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecycler()
        sv_main.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                   if(::adapter.isInitialized){
                       page=0
                       songsList.clear()
                       listSongsMutableList.clear()
                       }
                   onClickSearch()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        rv_songssearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {


            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = layoutManager.findFirstCompletelyVisibleItemPosition()
                val pastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()
                val total = adapter.itemCount
                if (!isLoading) {
                    if (pastVisibleItem+1 == total!! && listSongsMutableList.isNotEmpty()) {
                        page++
                        showSongs(songsList!!)
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })


    }

     fun onClickSearch(){
        if(!sv_main.query.isNullOrBlank()){
            artistname = sv_main.query.toString()
            if(et_main_filter.text.isNullOrBlank()){
                userFilter = limitPaging
                songsListSearch(sv_main.query.toString(), limitPaging.toString())
            }else{
                userFilter = et_main_filter.text.toString().toInt()
                songsListSearch(sv_main.query.toString(), userFilter.toString())
            }

        }else{
            Toast.makeText(this, "Search is Empty", Toast.LENGTH_LONG).show()
        }
    }


    fun songsListSearch(artistName: String?, filter: String) {
        startProgressBar(pb_rv_songssearch)
        val apiService: ApiInterface = apiclient?.getClient()!!.create(ApiInterface::class.java)
        val call: Call<SongResponse?>? = apiService.getSongDetails(artistName, filter)
        call?.enqueue(object : Callback<SongResponse?> {

            override fun onFailure(call: Call<SongResponse?>?, t: Throwable) {
                stopProgressBar(pb_rv_songssearch)
                Log.d("TAG", "onFailure: Failure Occurred " + t.message)
            }

            override fun onResponse(call: Call<SongResponse?>, response: Response<SongResponse?>) {
                Log.d("TAG", "onResponse: Response Code " + response.code())
                if (response.isSuccessful) {
                    if (response.body()!!.results.isNotEmpty()) {
                        stopProgressBar(pb_rv_songssearch)
                        songsList = response.body()!!.results as MutableList<Song>
                        showSongs(songsList!!)
                        Log.d("TAG", "onResponse: Songs List " + songsList?.size)
                    } else {
                        //mView.showError("Couldn't find any songs")
                        stopProgressBar(pb_rv_songssearch)
                    }
                } else {
                    stopProgressBar(pb_rv_songssearch)
                }
            }
        })
    }


    fun showSongs(songsList: MutableList<Song>) {
        isLoading = true
        startProgressBar(pb_rv_songssearch)
        if(userFilter!! > limitPaging){
            val start = ((page) * limitPaging) + 1
            var end = (page+1) * limitPaging
            if(end>=songsList.size){end = songsList.size}

                for(number in start..end){
                    listSongsMutableList.add(songsList[number-1])
                }

                 Handler(Looper.getMainLooper()).postDelayed({
                     if(!(::adapter.isInitialized)){
                         adapter = SearchSongCardViewAdapter(this, this)
                         rv_songssearch.adapter = adapter
                         adapter!!.notifyDataSetChanged()
                     }
                     adapter!!.notifyDataSetChanged()
                     stopProgressBar(pb_rv_songssearch)
                }, 5000)

        }else{
            if(!(::adapter.isInitialized)){
                listSongsMutableList = songsList
                adapter = SearchSongCardViewAdapter(this, this)
                rv_songssearch.adapter = adapter
                adapter!!.notifyDataSetChanged()
            }
        }
        isLoading = false
    }


    private fun setupRecycler() {
        layoutManager = LinearLayoutManager(this)
        rv_songssearch.layoutManager = layoutManager
    }

    override fun onSongItemClicked(song: Song?, position: Int) {

    }

    fun startProgressBar(progressBar: ProgressBar){
        progressBar.visibility = View.VISIBLE
    }

    fun stopProgressBar(progressBar: ProgressBar){
        progressBar.visibility = View.GONE
    }

}