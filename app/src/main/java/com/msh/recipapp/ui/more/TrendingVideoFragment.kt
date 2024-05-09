package com.msh.recipapp.ui.more

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.msh.recipapp.R
import com.msh.recipapp.base.BaseFragment
import com.msh.recipapp.databinding.FragmentTrendingVideoBinding
import com.msh.recipapp.models.home.ResponseVideo
import com.msh.recipapp.ui.more.adapter.VideoMoreAdapter
import com.msh.recipapp.utils.NetworkRequest
import com.msh.recipapp.utils.onceObserve
import com.msh.recipapp.utils.setupRecyclerview
import com.msh.recipapp.utils.showSnackBar
import com.msh.recipapp.viewmodel.more.TrendingVideoViewModel
import com.todkars.shimmer.ShimmerRecyclerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrendingVideoFragment : BaseFragment<FragmentTrendingVideoBinding>(
    R.layout.fragment_trending_video,
    FragmentTrendingVideoBinding::class
) {

    private val trendingVideoViewModel: TrendingVideoViewModel by viewModels()
    private val videoMoreAdapter: VideoMoreAdapter by lazy {
        VideoMoreAdapter(onItemClickListener = { result -> goToDetailVideoPage(result) })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //call data
        callVideoData()
        //load data
        loadVideoData()

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun callVideoData() {
        initVideoRecycler()
        trendingVideoViewModel.readMoreVideoFromDb
            .onceObserve(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    database[0].response.videos?.let { result ->
                        setupLoading(false, binding.trendingVideoList)
                        fillVideoAdapter(result.toMutableList())
                    }
                } else {
                    trendingVideoViewModel.callTrendVideoApi(trendingVideoViewModel.trendingVideoQueries())
                }

            }

    }

    private fun loadVideoData() {
        binding.apply {
            trendingVideoViewModel.trendingVideoData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkRequest.Loading -> {
                        setupLoading(true, trendingVideoList)
                    }

                    is NetworkRequest.Success -> {
                        setupLoading(false, trendingVideoList)
                        response.data.let { data ->
                            if (data?.videos!!.isNotEmpty()) {
                                data.videos.toMutableList().let { fillVideoAdapter(it) }
                            }
                        }
                    }

                    is NetworkRequest.Error -> {
                        setupLoading(false, trendingVideoList)
                        root.showSnackBar(response.message!!)
                    }

                    else -> {}
                }
            }
        }
    }

    private fun fillVideoAdapter(result: MutableList<ResponseVideo.Video?>) {
        videoMoreAdapter.setData(result)
    }

    private fun initVideoRecycler() {
        binding.trendingVideoList.setupRecyclerview(
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false),
            videoMoreAdapter
        )
    }

    private fun setupLoading(isShownLoading: Boolean, shimmer: ShimmerRecyclerView) {
        shimmer.apply {
            if (isShownLoading) showShimmer() else hideShimmer()
        }
    }

    private fun goToDetailVideoPage(data: ResponseVideo.Video) {
        val direction = TrendingVideoFragmentDirections.actionTrendingVideoFragmentToVideoDetailFragment(data)
        findNavController().navigate(direction)
    }
}