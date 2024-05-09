package com.msh.recipapp.ui.bookMark.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.msh.recipapp.base.BaseDiffUtils
import com.msh.recipapp.data.database.entity.bookmark.VideoBookmarkEntity
import com.msh.recipapp.databinding.ItemVideoBookmarkBinding
import com.msh.recipapp.models.home.ResponseVideo
import com.msh.recipapp.utils.toMinutesAndSeconds
import com.msh.recipapp.utils.toThreeDigits
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import javax.inject.Inject

class VideoBookMarkAdapter @Inject constructor(
    private val lifecycle: Lifecycle
) : RecyclerView.Adapter<VideoBookMarkAdapter.VideoVh>() {

    private var items = emptyList<VideoBookmarkEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoVh {
        val binding = ItemVideoBookmarkBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VideoVh(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VideoVh, position: Int) {
        holder.onBind(items[position])
    }

    inner class VideoVh(
        private val binding: ItemVideoBookmarkBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private var youTubePlayer: YouTubePlayer? = null

        fun onBind(item: VideoBookmarkEntity) {
            binding.apply {

                txtStarRating.text = item.result.views?.toThreeDigits().toString()
                txtTitleVideo.text = item.result.shortTitle

                val time = item.result.length?.toMinutesAndSeconds()
                timeMinute.text = time?.first
                timeSecond.text = time?.second

                overlayView.setOnClickListener { youTubePlayer?.play() }
                lifecycle.addObserver(youtubePlayerView)
                youtubePlayerView.addYouTubePlayerListener(object :
                    AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        this@VideoVh.youTubePlayer = youTubePlayer
                        item.result.youTubeId?.let { youTubePlayer.cueVideo(it, 0f) }
                    }

                    override fun onStateChange(
                        youTubePlayer: YouTubePlayer,
                        state: PlayerConstants.PlayerState
                    ) {
                        when (state) {
                            PlayerConstants.PlayerState.VIDEO_CUED ->
                                overlayView.visibility = View.VISIBLE

                            else -> overlayView.visibility = View.GONE
                        }
                    }
                })

            }
        }
    }

    private var onItemClickListener: ((ResponseVideo.Video) -> Unit)? = null
    fun setOnItemClickListener(listener: (ResponseVideo.Video) -> Unit) {
        onItemClickListener = listener
    }

    fun setData(data: List<VideoBookmarkEntity>) {
        val adapterDiffUtils = BaseDiffUtils(items, data)
        val diffUtils = DiffUtil.calculateDiff(adapterDiffUtils)
        items = data
        diffUtils.dispatchUpdatesTo(this)
    }
}