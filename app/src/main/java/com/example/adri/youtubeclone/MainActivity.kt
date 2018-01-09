package com.example.adri.youtubeclone

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //recyclerView_main.setBackgroundColor(Color.BLUE)
        recyclerView_main.layoutManager = LinearLayoutManager(this)
        //recyclerView_main.adapter = MainAdapter()

        fetchJSON()
    }

    fun fetchJSON() {
        val URL = "https://api.letsbuildthatapp.com/youtube/home_feed"

        val request = Request.Builder().url(URL).build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()

                val gson = GsonBuilder().create()

                val homeFeed = gson.fromJson(body, HomeFeed::class.java)

                // runs exception otherwise
                runOnUiThread {
                    recyclerView_main.adapter = MainAdapter(homeFeed) // Datasource/Delegate for recyclerview. Helps rendering the cells
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute")
            }

        })
    }
}

class HomeFeed(val videos: List<Video>)

class Video(val id: Int, val name: String, val link: String, val imageURL: String, val numberOfViews: Int, val channel: Channel)

class Channel(val name: String, val profileImageUrl: String, val numberOfSubscribers: Int)
