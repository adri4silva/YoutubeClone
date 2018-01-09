package com.example.adri.youtubeclone

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.video_row.view.*

/**
 * Created by adri on 8/1/18.
 */

class MainAdapter(val homeFeed: HomeFeed) : RecyclerView.Adapter<CustomViewHolder>() {

    val videoTitles = listOf<String>("Perfect Holiday!", "The Best Winter Sport", "Hanging Out With My Friends", "Getting Graduated!", "Going Dinner, You Wont Believe What Happened Later!", "Hola Diana!")

    // numberOfItems
    override fun getItemCount(): Int {
        return homeFeed.videos.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CustomViewHolder {
        // render the cell view
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.video_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder?, position: Int) {
        val video = homeFeed.videos[position]
        val channel = video.channel
        holder?.view?.textView_video_title?.text = video.name
        holder?.view?.textView_channel_name?.text = channel.name

        val videoImage = holder?.view?.imageView_video
        val channelImage = holder?.view?.imageView_user

        println(video.imageUrl)

        Picasso.with(holder?.view?.context).load(video.imageUrl).into(videoImage)
        Picasso.with(holder?.view?.context).load(channel.profileImageUrl).into(channelImage)

    }
}

class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

}