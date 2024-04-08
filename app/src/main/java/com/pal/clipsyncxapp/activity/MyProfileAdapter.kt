package com.pal.clipsyncxapp.activity

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.pal.clipsyncxapp.VideoModel
import com.pal.clipsyncxapp.databinding.ProfileVideoItemRowBinding


class MyProfileAdapter(
    options: FirestoreRecyclerOptions<VideoModel>,
    private val onItemClick: (VideoModel) -> Unit
) : FirestoreRecyclerAdapter<VideoModel, MyProfileAdapter.ProfileViewHolder>(options) {

    inner class ProfileViewHolder(private val binding: ProfileVideoItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(videoModel: VideoModel) {
        Glide.with(binding.thumbnailImageView).load(videoModel.url).into(binding.thumbnailImageView)
            binding.thumbnailImageView.setOnClickListener {
                onItemClick(videoModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyProfileAdapter.ProfileViewHolder {
        val binding = ProfileVideoItemRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, p1: Int, model: VideoModel) {
        holder.bind(model)
    }


}
