package com.example.adri.youtubeclone

/**
 * Created by adri on 9/1/18.
 */

class HomeFeed(val videos: List<Video>)

class Video(val id: Int, val name: String, val link: String, val imageUrl: String, val numberOfViews: Int, val channel: Channel)

class Channel(val name: String, val profileImageUrl: String, val numberOfSubscribers: Int)

class Lesson(val name: String, val duration: String, val number: Int, val imageUrl: String, val link: String)