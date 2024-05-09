package com.msh.recipapp.ui.home.adapter.video

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.msh.recipapp.R
import com.msh.recipapp.databinding.ItemVideoBinding
import com.msh.recipapp.models.home.ResponseVideo
import com.msh.recipapp.utils.toMinutesAndSeconds
import com.msh.recipapp.utils.toThreeDigits

class VideoVh(
    private val binding: ItemVideoBinding,
    private val onItemClickListener: (ResponseVideo.Video) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: ResponseVideo.Video) = with(binding) {

        txtStarRating.text = item.views?.toThreeDigits().toString()
        txtTitleVideo.text = item.shortTitle
        imgVideo.load(item.thumbnail) {
            memoryCachePolicy(CachePolicy.ENABLED)
            placeholder(R.drawable.ic_placeholder)
        }

        val time = item.length?.toMinutesAndSeconds()
        timeMinute.text = time?.first
        timeSecond.text = time?.second

        root.setOnClickListener {
            onItemClickListener(item)
        }
    }
}