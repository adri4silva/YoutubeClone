package com.example.adri.youtubeclone

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.course_lesson_row.view.*
import okhttp3.*
import java.io.IOException

/**
 * Created by adri on 9/1/18.
 */

class CourseDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        recyclerView_main.layoutManager = LinearLayoutManager(this)

        // change the nav bar title
        val navBarTitle = intent.getStringExtra(CustomViewHolder.VIDEO_TITLE_KEY)
        supportActionBar?.title = navBarTitle


        fetchJSON()
    }

    private fun fetchJSON() {
        val videoId = intent.getIntExtra(CustomViewHolder.VIDEO_TITLE_KEY, -1)
        val courseDetailUrl = "https://api.letsbuildthatapp.com/youtube/course_detail?id=" + videoId
        println(videoId)
        val client = OkHttpClient()
        val request = Request.Builder().url(courseDetailUrl).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                println("failed")
            }

            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()

                val lessons = gson.fromJson(body, Array<Lesson>::class.java)

                runOnUiThread {
                    recyclerView_main.adapter = CourseDetailAdapter(lessons)
                }
            }

        })
    }

    private class CourseDetailAdapter(val courseLessons: Array<Lesson>) : RecyclerView.Adapter<CourseDetailLessonHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CourseDetailLessonHolder {
            val layoutInflater = LayoutInflater.from(parent?.context)
            val customView = layoutInflater.inflate(R.layout.course_lesson_row, parent, false)
            return CourseDetailLessonHolder(customView)
        }

        override fun getItemCount(): Int {
            return courseLessons.size
        }

        override fun onBindViewHolder(holder: CourseDetailLessonHolder?, position: Int) {
            val lesson = courseLessons[position]
            val imageViewLesson = holder?.customView?.imageView_video_lesson
            Picasso.with(holder?.customView?.context).load(lesson.imageUrl).into(imageViewLesson)
            holder?.customView?.textView_title?.text = lesson.name
        }

    }

    private class CourseDetailLessonHolder(val customView: View) : RecyclerView.ViewHolder(customView) {

    }
}