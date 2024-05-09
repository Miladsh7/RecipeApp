package com.msh.recipapp.ui.more.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.msh.recipapp.R
import com.msh.recipapp.databinding.ItemVideoTrendingBinding
import com.msh.recipapp.models.home.ResponseVideo
import com.msh.recipapp.utils.toThreeDigits

class VideoMoreVh(
    private val binding: ItemVideoTrendingBinding,
    private val onItemClickListener: (ResponseVideo.Video) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ResponseVideo.Video) = with(binding) {
        txtStarRatingVideoTrending.text = item.views?.toThreeDigits()
        txtTitleVideoTrending.text = item.shortTitle
        txtTimeVideoTrending.text = item.length.toString()

        imgVideoTrending.load(item.thumbnail) {
            memoryCachePolicy(CachePolicy.ENABLED)
            placeholder(R.drawable.ic_placeholder)
        }

        root.setOnClickListener {
            onItemClickListener(item)
        }
    }
}