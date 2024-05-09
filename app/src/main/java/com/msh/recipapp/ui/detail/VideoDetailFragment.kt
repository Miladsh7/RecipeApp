package com.msh.recipapp.ui.detail

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.DrawableCompat.setTint
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.msh.recipapp.R
import com.msh.recipapp.base.BaseFragment
import com.msh.recipapp.data.database.entity.bookmark.VideoBookmarkEntity
import com.msh.recipapp.databinding.FragmentVideoDetailBinding
import com.msh.recipapp.models.home.ResponseVideo
import com.msh.recipapp.utils.NetworkChecker
import com.msh.recipapp.utils.minToHour
import com.msh.recipapp.viewmodel.bookmark.VideoBookMarkViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class VideoDetailFragment : BaseFragment<FragmentVideoDetailBinding>(
    R.layout.fragment_video_detail,
    FragmentVideoDetailBinding::class
) {

    private val args: VideoDetailFragmentArgs by navArgs()
    private val videoBookMarkViewModel: VideoBookMarkViewModel by viewModels()

    @Inject
    lateinit var networkChecker: NetworkChecker
    lateinit var videoData: ResponseVideo.Video
    lateinit var youTubePlayer: YouTubePlayer

    private var isExistCache = false
    private var isExistVideoBookMark = false
    private var orientationType: Int? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videoData = args.videoData

        //check internet connection
        viewLifecycleOwner.lifecycleScope.launch {
            networkChecker.checkNetworkAvailability().collect { state ->
                delay(200)
                if (isExistCache.not()) {
                    initInternetLayout(state)
                }
                if (state) {
                    initWithData(videoData)
                    binding.contentLay.isVisible = true
                }
            }
        }

        //back//
        binding.backImg.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initWithData(videoData: ResponseVideo.Video) {
        binding.apply {

            videoBookMarkViewModel.existsVideoBookMark(videoData.youTubeId!!)
            checkExistsVideoBookMark()

            favoriteImg.setOnClickListener {
                if (isExistVideoBookMark) deleteVideoBookMark(videoData) else saveVideoBookMark(
                    videoData
                )
            }

            val iFramePlayerOptions =
                IFramePlayerOptions.Builder()
                    .controls(1)
                    .fullscreen(1) // enable full screen button
                    .autoplay(1)
                    .build()
            youtubePlayerView.enableAutomaticInitialization = false
            youtubePlayerView.initialize(
                object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        this@VideoDetailFragment.youTubePlayer = youTubePlayer
                        youTubePlayer.loadVideo(videoData.youTubeId, 0f)
                    }
                },
                iFramePlayerOptions,
            )
            lifecycle.addObserver(youtubePlayerView)

            txtTitleVideoDetail.text = videoData.title
            timeTxt.text = videoData.length?.minToHour()

            val fullscreenListener = object : FullscreenListener {

                override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {

                    val landScape = 1
                    orientationType = landScape
                    requireActivity().requestedOrientation =
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

                }

                override fun onExitFullscreen() {

                    val portRate = 0
                    orientationType = portRate
                    requireActivity().requestedOrientation =
                        ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

                }
            }

            when (orientationType) {
                0 -> {
                    toolbarLayVideo.visibility = View.VISIBLE
                }

                1 -> {toolbarLayVideo.visibility = View.GONE}
            }
            // Register the FullscreenListener with the YouTubePlayerView
            youtubePlayerView.addFullscreenListener(fullscreenListener)


        }
    }

    private fun initInternetLayout(isConnected: Boolean) {
        binding.internetLay.isVisible = isConnected.not()
    }

    //--BookMark--//
    private fun saveVideoBookMark(data: ResponseVideo.Video) {
        val entity = VideoBookmarkEntity(data.youTubeId!!, data)
        videoBookMarkViewModel.saveVideoBookMark(entity)
        binding.favoriteImg.apply {
            setTint(drawable, resources.getColor(R.color.Primary_50))
        }
    }

    private fun deleteVideoBookMark(data: ResponseVideo.Video) {
        val entity = VideoBookmarkEntity(data.youTubeId!!, data)
        videoBookMarkViewModel.deleteVideoBookMark(entity)
        binding.favoriteImg.apply {
            setTint(drawable, resources.getColor(R.color.mediumGray))
        }
    }

    private fun checkExistsVideoBookMark() {
        videoBookMarkViewModel.existsVideoBookMarkData.observe(viewLifecycleOwner) {
            isExistVideoBookMark = it
            if (it) {
                binding.favoriteImg.apply {
                    setTint(drawable, resources.getColor(R.color.Primary_50))
                }
            } else {
                binding.favoriteImg.apply {
                    setTint(drawable, resources.getColor(R.color.mediumGray))
                }
            }
        }
    }
}