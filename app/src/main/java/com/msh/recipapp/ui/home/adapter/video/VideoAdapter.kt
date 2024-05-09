package com.msh.recipapp.ui.home.adapter.video

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.msh.recipapp.R
import com.msh.recipapp.base.BaseDiffUtils
import com.msh.recipapp.databinding.ItemVideoBinding
import com.msh.recipapp.models.home.ResponseVideo
import com.msh.recipapp.ui.home.adapter.popular.PopularVh
import com.msh.recipapp.ui.home.adapter.video.VideoVh
import com.msh.recipapp.utils.toMinutesAndSeconds
import com.msh.recipapp.utils.toThreeDigits

class VideoAdapter(
    private val onItemClickListener: (ResponseVideo.Video) -> Unit
) : RecyclerView.Adapter<VideoVh>() {

    private var items = emptyList<ResponseVideo.Video?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoVh {
        val binding = ItemVideoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VideoVh(binding,onItemClickListener)
    }

    override fun onBindViewHolder(holder: VideoVh, position: Int) =
        holder.onBind(items[position]!!)

    override fun getItemCount() = items.size
    fun setData(data: List<ResponseVideo.Video?>) {
        val adapterDiffUtils = BaseDiffUtils(items, data)
        val diffUtils = DiffUtil.calculateDiff(adapterDiffUtils)
        items = data
        diffUtils.dispatchUpdatesTo(this)
    }
}