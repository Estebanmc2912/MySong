package com.masglobal.mysong.ui.main.search

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.masglobal.mysong.R
import com.masglobal.mysong.app.connection.ApiClient
import com.masglobal.mysong.app.connection.ApiInterface
import com.masglobal.mysong.ui.main.adapters.SearchSongAdapter
import com.masglobal.mysong.ui.main.adapters.TopHitsSongAdapter
import com.masglobal.mysong.ui.main.entities.FeedResponse
import com.masglobal.mysong.ui.main.entities.Song
import com.masglobal.mysong.ui.main.entities.SongEntity
import com.masglobal.mysong.ui.main.entities.SongResponse
import com.masglobal.mysong.ui.main.utils.UtilsPopUps
import kotlinx.android.synthetic.main.fragment_search.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SearchFragment : Fragment(), SearchSongAdapter.OnItemClickListener{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    lateinit var songsList: MutableList<Song>
    var listSongsMutableList : MutableList<Song> = ArrayList()
    lateinit var adapterSearch : SearchSongAdapter
    lateinit var layoutManager: LinearLayoutManager
    lateinit var artistname : String
    var userFilter : Int ?= null
    var page = 0
    var isLoading = false
    var limitPaging : Int = 5
    lateinit var principalActivity : Activity
    var fragmentView: View? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        principalActivity = context as Activity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (fragmentView!=null){
            return fragmentView
        }

        var v : View = inflater.inflate(R.layout.fragment_search, container, false)

        setupRecycler(v)
        v.sv_main.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if(::adapterSearch.isInitialized){
                    page=0
                    songsList.clear()
                    listSongsMutableList.clear()
                }
                onClickSearch(v)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        v.rv_songssearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = layoutManager.findFirstCompletelyVisibleItemPosition()
                val pastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()
                val total = adapterSearch.itemCount
                if (!isLoading) {
                    if (pastVisibleItem+1 == total!! && listSongsMutableList.isNotEmpty()) {
                        page++
                        showSongs(songsList!!,v)
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        fragmentView = v
        return v
    }



    fun onClickSearch(v : View){
        if(!v.sv_main.query.isNullOrBlank()){
            artistname = v.sv_main.query.toString()
            if(v.et_main_filter.text.isNullOrBlank()){
                userFilter = limitPaging
                songsListSearch(v.sv_main.query.toString(), limitPaging.toString(),v)
            }else{
                userFilter = v.et_main_filter.text.toString().toInt()
                songsListSearch(v.sv_main.query.toString(), userFilter.toString(),v)
            }

        }else{
            Toast.makeText(activity as Context, "Search is Empty", Toast.LENGTH_LONG).show()
        }
    }


    fun songsListSearch(artistName: String?, filter: String, v : View) {
        startProgressBar(v.pb_rv_songssearch)
        val apiService: ApiInterface = ApiClient()?.getClient()!!.create(ApiInterface::class.java)
        val call: Call<SongResponse?>? = apiService.getSongDetails(artistName, filter)
        call?.enqueue(object : Callback<SongResponse?> {

            override fun onFailure(call: Call<SongResponse?>?, t: Throwable) {
                stopProgressBar(v.pb_rv_songssearch)
                Log.d("TAG", "onFailure: Failure Occurred " + t.message)
            }

            override fun onResponse(call: Call<SongResponse?>, response: Response<SongResponse?>) {
                if (response.isSuccessful) {
                    if (response.body()!!.results.isNotEmpty()) {
                        stopProgressBar(v.pb_rv_songssearch)
                        songsList = response.body()!!.results as MutableList<Song>
                        showSongs(songsList!!,v)
                        Log.d("TAG", "onResponse: Songs List " + songsList?.size)
                    } else {
                        //mView.showError("Couldn't find any songs")
                        stopProgressBar(v.pb_rv_songssearch)
                    }
                } else {
                    stopProgressBar(v.pb_rv_songssearch)
                }
            }
        })
    }


    fun showSongs(songsList: MutableList<Song>, v: View) {
        isLoading = true
        startProgressBar(v.pb_rv_songssearch)
        if(userFilter!! > limitPaging){
            val start = ((page) * limitPaging) + 1
            var end = (page+1) * limitPaging
            if(end>=songsList.size){end = songsList.size}

            for(number in start..end){
                listSongsMutableList.add(songsList[number-1])
            }

            Handler(Looper.getMainLooper()).postDelayed({
                if(!(::adapterSearch.isInitialized)){
                    adapterSearch = SearchSongAdapter(this, this)
                    v.rv_songssearch.adapter = adapterSearch
                    adapterSearch!!.notifyDataSetChanged()
                }
                adapterSearch!!.notifyDataSetChanged()
                stopProgressBar(v.pb_rv_songssearch)
            }, 5000)

        }else{
            if(!(::adapterSearch.isInitialized)){
                listSongsMutableList = songsList
                adapterSearch = SearchSongAdapter(this, this)
                v.rv_songssearch.adapter = adapterSearch
                adapterSearch!!.notifyDataSetChanged()
            }
        }
        isLoading = false
    }


    private fun setupRecycler(v: View) {
        layoutManager = LinearLayoutManager(context)
        v.rv_songssearch.layoutManager = layoutManager
    }

    override fun onSongItemClicked(song: Song?, position: Int) {
        var list : ArrayList<SongEntity> = arrayListOf()
        for (itemCount in 0..(userFilter?.minus(1))!!){
            var entity = SongEntity(
                    songsList[itemCount].trackName,
                    songsList[itemCount].artistName,
                    songsList[itemCount].artworkUrl100,
                    songsList[itemCount].primaryGenreName,
                    songsList[itemCount].releaseDate,
                    songsList[itemCount].previewUrl)
            list.add(entity)
        }
        UtilsPopUps.listSongs(activity as Context, list,position)
    }

    fun startProgressBar(progressBar: ProgressBar){
        progressBar.visibility = View.VISIBLE
    }

    fun stopProgressBar(progressBar: ProgressBar){
        progressBar.visibility = View.GONE
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SearchFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}