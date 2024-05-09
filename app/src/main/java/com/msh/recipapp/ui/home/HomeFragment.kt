package com.msh.recipapp.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.msh.recipapp.R
import com.msh.recipapp.base.BaseFragment
import com.msh.recipapp.data.Type
import com.msh.recipapp.data.database.entity.home.VideoEntity
import com.msh.recipapp.databinding.FragmentHomeBinding
import com.msh.recipapp.models.home.ResponsePopular
import com.msh.recipapp.models.home.ResponseRecent
import com.msh.recipapp.models.home.ResponseVideo
import com.msh.recipapp.ui.home.adapter.popular.PopularAdapter
import com.msh.recipapp.ui.home.adapter.recent.RecentAdapter
import com.msh.recipapp.ui.home.adapter.video.VideoAdapter
import com.msh.recipapp.utils.BREAD
import com.msh.recipapp.utils.BREAKFAST
import com.msh.recipapp.utils.DELAY_TIME
import com.msh.recipapp.utils.DESSERT
import com.msh.recipapp.utils.DRINK
import com.msh.recipapp.utils.NetworkChecker
import com.msh.recipapp.utils.NetworkRequest
import com.msh.recipapp.utils.REPEAT_TIME
import com.msh.recipapp.utils.SALAD
import com.msh.recipapp.utils.SAUCE
import com.msh.recipapp.utils.SNACK
import com.msh.recipapp.utils.SOUP
import com.msh.recipapp.utils.onceObserve
import com.msh.recipapp.utils.setupRecyclerview
import com.msh.recipapp.utils.showSnackBar
import com.msh.recipapp.viewmodel.home.HomeViewModel
import com.todkars.shimmer.ShimmerRecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    R.layout.fragment_home,
    FragmentHomeBinding::class
) {

    private val videoAdapter by lazy { VideoAdapter(onItemClickListener = { goDetailPage(it) }) }
    private val popularAdapter by lazy {
        PopularAdapter(onItemClickListener = {
            gotoDetailPage(it)
        })
    }

    private val recentAdapter by lazy {
        RecentAdapter(onItemClickListener = {
            gotoRecentDetailPage(it)
        })
    }

    private val homeViewModel: HomeViewModel by viewModels()

    private var autoScrollIndex = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Call data
        callVideoData()
        callRecentData()

        //Load data
        loadVideoData()
        loadRecentData()
        loadPopularApi()

        //Btn
        defaultBtn()
        setUpBtn()

        binding.apply {

            txtMoreTrending.setOnClickListener {
                gotoMoreVideoTrending()
            }

            txtMoreRecent.setOnClickListener {
                gotoMoreRecentRecipe()
            }
        }

    }

    //--VideoTrending--//
    private fun callVideoData() {
        initVideoRecycler()
        homeViewModel.readVideoFromDb
            .onceObserve(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    database[0].response.videos?.let { result ->
                        setupLoading(false, binding.videoList)
                        fillVideoAdapter(result.toMutableList())
                    }
                } else {
                    homeViewModel.callTrendVideoApi(homeViewModel.trendingVideoQueries())
                }
            }

    }

    private fun loadVideoData() {
        binding.apply {
            homeViewModel.trendingVideoData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkRequest.Loading -> {
                        setupLoading(true, videoList)
                    }

                    is NetworkRequest.Success -> {
                        setupLoading(false, videoList)
                        response.data.let { data ->
                            if (data?.videos!!.isNotEmpty()) {
                                data.videos.toMutableList().let { fillVideoAdapter(it) }
                            }
                        }
                    }

                    is NetworkRequest.Error -> {
                        setupLoading(false, videoList)
                        root.showSnackBar(response.message!!)
                    }

                    else -> {}
                }
            }
        }
    }


    private fun fillVideoAdapter(result: MutableList<ResponseVideo.Video?>) {
        videoAdapter.setData(result)
        autoScrollVideoTrending(result)
    }

    private fun initVideoRecycler() {
        val snapHelper = LinearSnapHelper()
        binding.videoList.setupRecyclerview(
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
            videoAdapter
        )

        //snap
        snapHelper.attachToRecyclerView(binding.videoList)

    }

    private fun autoScrollVideoTrending(list: List<ResponseVideo.Video?>) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeat(REPEAT_TIME) {
                delay(DELAY_TIME)
                if (autoScrollIndex < list.size) {
                    autoScrollIndex += 1
                } else {
                    autoScrollIndex = 0
                }
                binding.videoList.smoothScrollToPosition(autoScrollIndex)
            }
        }
    }

    private fun defaultBtn() {
        initPopularRecycler()

        homeViewModel.selectReadPopularDb(Type.BREAKFAST.ordinal)
            .onceObserve(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    database[0].response.results?.let { result ->
                        setupLoading(false, binding.popularList)
                        popularAdapter.setData(result)
                    }

                } else {
                    homeViewModel.callPopularApi(
                        homeViewModel.popularQueries(
                            BREAKFAST
                        ), Type.BREAKFAST.name
                    )

                }
            }

    }

    private fun setUpBtn() {

        //Default
        binding.txtSalad.isSelected = false
        binding.txtDessert.isSelected = false
        binding.txtSoup.isSelected = false
        binding.txtSnack.isSelected = false
        binding.txtBread.isSelected = false
        binding.txtDrink.isSelected = false
        binding.txtSauce.isSelected = false
        binding.txtBreakfast.isSelected = true

        binding.txtSalad.setOnClickListener {
            binding.apply {
                txtDessert.isSelected = false
                txtBreakfast.isSelected = false
                txtSoup.isSelected = false
                txtSnack.isSelected = false
                txtBread.isSelected = false
                txtDrink.isSelected = false
                txtSauce.isSelected = false
                txtSalad.isSelected = true
                callPopularApi(txtSalad)
            }
        }

        binding.txtBreakfast.setOnClickListener {
            binding.apply {
                txtSalad.isSelected = false
                txtDessert.isSelected = false
                txtSoup.isSelected = false
                txtSnack.isSelected = false
                txtBread.isSelected = false
                txtDrink.isSelected = false
                txtSauce.isSelected = false
                txtBreakfast.isSelected = true
                callPopularApi(txtBreakfast)
            }
        }

        binding.txtSoup.setOnClickListener {
            binding.apply {
                txtSalad.isSelected = false
                txtBreakfast.isSelected = false
                txtDessert.isSelected = false
                txtSnack.isSelected = false
                txtBread.isSelected = false
                txtDrink.isSelected = false
                txtSauce.isSelected = false
                txtSoup.isSelected = true
                callPopularApi(txtSoup)
            }
        }

        binding.txtSnack.setOnClickListener {
            binding.apply {
                txtSalad.isSelected = false
                txtBreakfast.isSelected = false
                txtSoup.isSelected = false
                txtDessert.isSelected = false
                txtBread.isSelected = false
                txtDrink.isSelected = false
                txtSauce.isSelected = false
                txtSnack.isSelected = true
                callPopularApi(txtSnack)
            }
        }

        binding.txtBread.setOnClickListener {
            binding.apply {
                txtSalad.isSelected = false
                txtBreakfast.isSelected = false
                txtSoup.isSelected = false
                txtSnack.isSelected = false
                txtDessert.isSelected = false
                txtDrink.isSelected = false
                txtSauce.isSelected = false
                txtBread.isSelected = true
                callPopularApi(txtBread)
            }
        }

        binding.txtSauce.setOnClickListener {
            binding.apply {
                txtSalad.isSelected = false
                txtBreakfast.isSelected = false
                txtSoup.isSelected = false
                txtSnack.isSelected = false
                txtBread.isSelected = false
                txtDrink.isSelected = false
                txtDessert.isSelected = false
                txtSauce.isSelected = true
                callPopularApi(txtSauce)
            }
        }

        binding.txtDrink.setOnClickListener {
            binding.apply {
                txtSalad.isSelected = false
                txtBreakfast.isSelected = false
                txtSoup.isSelected = false
                txtSnack.isSelected = false
                txtBread.isSelected = false
                txtDessert.isSelected = false
                txtSauce.isSelected = false
                txtDrink.isSelected = true
                callPopularApi(txtDrink)
            }
        }

        binding.txtDessert.setOnClickListener {
            binding.apply {
                txtSalad.isSelected = false
                txtBreakfast.isSelected = false
                txtSoup.isSelected = false
                txtSnack.isSelected = false
                txtBread.isSelected = false
                txtDrink.isSelected = false
                txtSauce.isSelected = false
                txtDessert.isSelected = true
                callPopularApi(txtDessert)
            }
        }
    }

    //--Popular--//
    private fun callPopularApi(textView: AppCompatTextView) {
        when (textView) {
            binding.txtSalad -> {
                initPopularRecycler()

                homeViewModel.selectReadPopularDb(Type.SALAD.ordinal)
                    .onceObserve(viewLifecycleOwner) { database ->
                        if (database.isNotEmpty()) {
                            database[0].response.results?.let { result ->
                                setupLoading(false, binding.popularList)
                                popularAdapter.setData(result)
                            }

                        } else {
                            homeViewModel.callPopularApi(
                                homeViewModel.popularQueries(SALAD), Type.SALAD.name
                            )
                        }
                    }

            }

            binding.txtBreakfast -> {
                initPopularRecycler()

                homeViewModel.selectReadPopularDb(Type.BREAKFAST.ordinal)
                    .onceObserve(viewLifecycleOwner) { database ->
                        if (database.isNotEmpty()) {
                            database[0].response.results?.let { result ->
                                setupLoading(false, binding.popularList)
                                popularAdapter.setData(result)
                            }

                        } else {
                            homeViewModel.callPopularApi(
                                homeViewModel.popularQueries(
                                    BREAKFAST
                                ), Type.BREAKFAST.name
                            )
                        }
                    }

            }

            binding.txtBread -> {
                initPopularRecycler()
                homeViewModel.selectReadPopularDb(Type.BREAD.ordinal)
                    .onceObserve(viewLifecycleOwner) { database ->
                        if (database.isNotEmpty()) {
                            database[0].response.results?.let { result ->
                                setupLoading(false, binding.popularList)
                                popularAdapter.setData(result)
                            }

                        } else {
                            homeViewModel.callPopularApi(
                                homeViewModel.popularQueries(BREAD),
                                Type.BREAD.name
                            )
                        }
                    }

            }

            binding.txtSoup -> {
                initPopularRecycler()
                homeViewModel.selectReadPopularDb(Type.SOUP.ordinal)
                    .onceObserve(viewLifecycleOwner) { database ->
                        if (database.isNotEmpty()) {
                            database[0].response.results?.let { result ->
                                setupLoading(false, binding.popularList)
                                popularAdapter.setData(result)
                            }

                        } else {
                            homeViewModel.callPopularApi(
                                homeViewModel.popularQueries(SOUP),
                                Type.SOUP.name
                            )
                        }
                    }

            }

            binding.txtSnack -> {
                initPopularRecycler()
                homeViewModel.selectReadPopularDb(Type.SNACK.ordinal)
                    .onceObserve(viewLifecycleOwner) { database ->
                        if (database.isNotEmpty()) {
                            database[0].response.results?.let { result ->
                                setupLoading(false, binding.popularList)
                                popularAdapter.setData(result)
                            }

                        } else {
                            homeViewModel.callPopularApi(
                                homeViewModel.popularQueries(SNACK),
                                Type.SNACK.name
                            )
                        }
                    }
            }

            binding.txtSauce -> {
                initPopularRecycler()
                homeViewModel.selectReadPopularDb(Type.SAUCE.ordinal)
                    .onceObserve(viewLifecycleOwner) { database ->
                        if (database.isNotEmpty()) {
                            database[0].response.results?.let { result ->
                                setupLoading(false, binding.popularList)
                                popularAdapter.setData(result)
                            }

                        } else {
                            homeViewModel.callPopularApi(
                                homeViewModel.popularQueries(SAUCE),
                                Type.SAUCE.name
                            )
                        }
                    }
            }

            binding.txtDrink -> {
                initPopularRecycler()
                homeViewModel.selectReadPopularDb(Type.DRINK.ordinal)
                    .onceObserve(viewLifecycleOwner) { database ->
                        if (database.isNotEmpty()) {
                            database[0].response.results?.let { result ->
                                setupLoading(false, binding.popularList)
                                popularAdapter.setData(result)
                            }

                        } else {
                            homeViewModel.callPopularApi(
                                homeViewModel.popularQueries(DRINK),
                                Type.DRINK.name
                            )
                        }
                    }
            }

            binding.txtDessert -> {
                initPopularRecycler()
                homeViewModel.selectReadPopularDb(Type.DESSERT.ordinal)
                    .onceObserve(viewLifecycleOwner) { database ->
                        if (database.isNotEmpty()) {
                            database[0].response.results?.let { result ->
                                setupLoading(false, binding.popularList)
                                popularAdapter.setData(result)
                            }

                        } else {
                            homeViewModel.callPopularApi(
                                homeViewModel.popularQueries(DESSERT),
                                Type.DESSERT.name
                            )
                        }
                    }

            }
        }
    }

    private fun fillPopularData(result: MutableList<ResponsePopular.Result?>) {
        popularAdapter.setData(result)
    }

    private fun initPopularRecycler() {
        binding.popularList.setupRecyclerview(
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            ), popularAdapter
        )


    }

    private fun loadPopularApi() {
        binding.apply {
            homeViewModel.popularData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkRequest.Loading -> {
                        setupLoading(true, popularList)
                    }

                    is NetworkRequest.Success -> {
                        setupLoading(false, popularList)
                        response.data.let { data ->
                            if (data?.results!!.isNotEmpty()) {
                                data.results.toMutableList().let { fillPopularData(it) }
                            }
                        }
                    }

                    is NetworkRequest.Error -> {
                        setupLoading(false, videoList)
                        root.showSnackBar(response.message!!)
                    }

                    else -> {}
                }
            }
        }
    }

    //--Recent--//
    private fun callRecentData() {
        initRecentRecycler()
        homeViewModel.readRecentFromDb.onceObserve(viewLifecycleOwner) { database ->
            if (database.isNotEmpty()) {
                database[0].response.results?.let { results ->
                    setupLoading(false, binding.recentList)
                    recentAdapter.setData(results)
                }
            } else {
                homeViewModel.callRecentApi(homeViewModel.recentQueries())
            }

        }

    }

    private fun loadRecentData() {
        binding.apply {
            homeViewModel.recentData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkRequest.Loading -> {
                        setupLoading(true, recentList)
                    }

                    is NetworkRequest.Success -> {
                        setupLoading(false, recentList)
                        response.data.let { data ->
                            if (data?.results!!.isNotEmpty()) {
                                data.results.toList().let { fillRecentData(it) }
                            }
                        }
                    }

                    is NetworkRequest.Error -> {
                        setupLoading(false, recentList)
                        root.showSnackBar(response.message!!)
                    }

                    else -> {}
                }
            }
        }
    }

    private fun fillRecentData(result: List<ResponseRecent.Result?>) {
        recentAdapter.setData(result)
    }

    private fun initRecentRecycler() {
        binding.recentList.setupRecyclerview(
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            ), recentAdapter
        )
    }

    private fun setupLoading(isShownLoading: Boolean, shimmer: ShimmerRecyclerView) {
        shimmer.apply {
            if (isShownLoading) showShimmer() else hideShimmer()
        }
    }

    private fun gotoDetailPage(id: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToPopularDetailFragment2(id)
        findNavController().navigate(action)
    }

    private fun gotoRecentDetailPage(id: Int) {
        val direction = HomeFragmentDirections.actionHomeFragmentToRecentDetailFragment(id)
        findNavController().navigate(direction)
    }

    private fun goDetailPage(data: ResponseVideo.Video) {
        val action = HomeFragmentDirections.actionHomeFragmentToVideoDetailFragment(data)
        findNavController().navigate(action)
    }

    private fun gotoMoreVideoTrending() {
        val action = HomeFragmentDirections.actionHomeFragmentToTrendingVideoFragment()
        findNavController().navigate(action)
    }

    private fun gotoMoreRecentRecipe() {
        val action = HomeFragmentDirections.actionHomeFragmentToRecentRecipeFragment()
        findNavController().navigate(action)
    }
}
