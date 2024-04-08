package com.pal.clipsyncxapp

import com.google.firebase.Timestamp

data class VideoModel(
    var videoId : String = "",
    var title : String = "",
    var url : String = "",
    var uploaderId : String = "",
    var createdTime : Timestamp = Timestamp.now()
)
