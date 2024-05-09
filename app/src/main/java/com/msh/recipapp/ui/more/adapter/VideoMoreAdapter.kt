package com.msh.recipapp.ui.more.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.msh.recipapp.base.BaseDiffUtils
import com.msh.recipapp.databinding.ItemVideoTrendingBinding
import com.msh.recipapp.models.home.ResponseVideo

class VideoMoreAdapter(
    private var onItemClickListener: (ResponseVideo.Video) -> Unit)
    : RecyclerView.Adapter<VideoMoreVh>() {

    private var items = emptyList<ResponseVideo.Video?>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoMoreVh {
        val view = ItemVideoTrendingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VideoMoreVh(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: VideoMoreVh, position: Int) {
        holder.bind(items[position]!!)
    }

    override fun getItemCount(): Int = items.size

    fun setData(data: List<ResponseVideo.Video?>) {
        val adapterDiffUtils = BaseDiffUtils(items, data)
        val diffUtils = DiffUtil.calculateDiff(adapterDiffUtils)
        items = data
        diffUtils.dispatchUpdatesTo(this)
    }
}