package com.masglobal.mysong.ui.main.tophits

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.masglobal.mysong.R
import com.masglobal.mysong.app.connection.ApiClient
import com.masglobal.mysong.app.connection.ApiInterface
import com.masglobal.mysong.ui.main.adapters.TopHitsSongAdapter
import com.masglobal.mysong.ui.main.entities.FeedResponse
import com.masglobal.mysong.ui.main.entities.Song
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.fragment_top_hits.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class TopHitsFragment : Fragment() , TopHitsSongAdapter.OnItemClickListener{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    lateinit var adapterTopHits : TopHitsSongAdapter
    var isLoading = false
    var songsHitsList : MutableList<Song> = ArrayList()
    var fragmentView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (fragmentView != null) {
            return fragmentView;
        }

        var v =  inflater.inflate(R.layout.fragment_top_hits, container, false)
        songsTopHits(v)
        fragmentView = v
        return v
    }


    fun songsTopHits(v: View){
        val apiService: ApiInterface = ApiClient()?.getClientRSS()!!.create(ApiInterface::class.java)
        val call: Call<FeedResponse?>? = apiService.getTopHits("10")
        call?.enqueue(object : Callback<FeedResponse?> {

            override fun onFailure(call: Call<FeedResponse?>?, t: Throwable) {
                stopProgressBar(v.pb_rv_songssearch)
                Log.d("TAG", "onFailure: Failure Occurred " + t.message)
            }


            override fun onResponse(call: Call<FeedResponse?>, response: Response<FeedResponse?>) {
                if (response.isSuccessful) {
                    if (response.body()!!.feed.results != null) {
//                        stopProgressBar(v.pb_rv_songssearch)
                        songsHitsList = response.body()!!.feed.results as MutableList<Song>
                        showHitsSongs(v)
                        Log.d("TAG", "onResponse: Songs List " + songsHitsList?.size)
                    } else {
                        //mView.showError("Couldn't find any songs")
                        Toast.makeText(
                            context as Context,
                            "Couldn't find any songs",
                            Toast.LENGTH_LONG
                        ).show()
                        stopProgressBar(v.pb_rv_songssearch)
                    }
                } else {
                    stopProgressBar(v.pb_rv_songssearch)
                }
            }
        })

    }

    fun showHitsSongs(v: View){
        if(!(::adapterTopHits.isInitialized)) {
            setupRecycler(v)
            adapterTopHits = TopHitsSongAdapter(this, this)
            v.rv_songshits.adapter = adapterTopHits
            adapterTopHits!!.notifyDataSetChanged()
        }
    }

    private fun setupRecycler(v: View) {
        v.rv_songshits.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        v.rv_songshits.setPageTransformer(MarginPageTransformer(100))
        v.rv_songshits.offscreenPageLimit = 3
        v.rv_songshits.setPageTransformer(SliderTransformer(3))
        //v.rv_songshits.la
        //v.rv_songshits.layout
        //v.rv_songshits.layoutManager = layoutManager
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
            TopHitsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onSongItemClicked(song: Song?, position: Int) {

    }
}