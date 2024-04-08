package com.pal.clipsyncxapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.pal.clipsyncxapp.VideoModel
import com.pal.clipsyncxapp.databinding.ActivitySingleVideoBinding

class SingleVideoActivity : AppCompatActivity() {
    lateinit var binding : ActivitySingleVideoBinding
    var videoId = ""
    lateinit var adapter : VideoListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        videoId = intent.getStringExtra("videoId")!!
        setViewPager()
    }

    private fun setViewPager() {
       val option = FirestoreRecyclerOptions.Builder<VideoModel>()
           .setQuery(Firebase.firestore.collection("videos").
           whereEqualTo("videoId",videoId),VideoModel::class.java).build()
        adapter = VideoListAdapter(option)
        binding.viewPager.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}