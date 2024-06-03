package com.edwinyosua.ceritaapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edwinyosua.ceritaapp.databinding.StoriesItemBinding
import com.edwinyosua.ceritaapp.network.apiresponse.ListStoryItem

class StoryAdapter() :
    PagingDataAdapter<ListStoryItem, StoryAdapter.StoryHolder>(DIFF_CALLBACK) {


    class StoryHolder(private val binding: StoriesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: ListStoryItem) {
            binding.storiesTitle.text = story.name
            binding.storiesDesc.text = story.description
            Glide.with(binding.root.context)
                .load(story.photoUrl)
                .into(binding.storiesImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryHolder =
        StoryHolder(
            StoriesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: StoryHolder, position: Int) {
        val stories = getItem(position)
        if (stories != null) {
            holder.bind(stories)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}