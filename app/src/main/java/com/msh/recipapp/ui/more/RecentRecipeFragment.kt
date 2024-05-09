package com.msh.recipapp.ui.more

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.msh.recipapp.R
import com.msh.recipapp.base.BaseFragment
import com.msh.recipapp.databinding.FragmentRecentRecipeBinding
import com.msh.recipapp.models.home.ResponseRecent
import com.msh.recipapp.ui.more.adapter.RecentMoreAdapter
import com.msh.recipapp.utils.NetworkRequest
import com.msh.recipapp.utils.onceObserve
import com.msh.recipapp.utils.setupRecyclerview
import com.msh.recipapp.utils.showSnackBar
import com.msh.recipapp.viewmodel.more.RecentRecipeViewModel
import com.todkars.shimmer.ShimmerRecyclerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecentRecipeFragment : BaseFragment<FragmentRecentRecipeBinding>(
    R.layout.fragment_recent_recipe,
    FragmentRecentRecipeBinding::class
) {

    private val recentRecipeViewModel: RecentRecipeViewModel by viewModels()
    private val recentMoreAdapter: RecentMoreAdapter by lazy {
        RecentMoreAdapter(onItemClickListener = { goToDetailRecentPage(it) })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //call data
        callRecentData()
        //load data
        readRecentData()

        binding.imgBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun callRecentData() {
        initRecentRecycler()
        recentRecipeViewModel.readMoreRecentFromDb.onceObserve(viewLifecycleOwner) { databace ->
            if (databace.isNotEmpty()) {
                databace[0].response.results?.let { results ->
                    setupLoading(false, binding.recentRecipeList)
                    recentMoreAdapter.setData(results)
                }
            } else {
                recentRecipeViewModel.callRecentApi(recentRecipeViewModel.recentRecipeQueries())
            }
        }

    }

    private fun readRecentData() {
        binding.apply {
            recentRecipeViewModel.moreRecentData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkRequest.Loading -> {
                        setupLoading(true, recentRecipeList)
                    }

                    is NetworkRequest.Success -> {
                        setupLoading(false, recentRecipeList)
                        response.data.let { data ->
                            if (data?.results!!.isNotEmpty()) {
                                data.results.toList().let { fillRecentData(it) }
                            }
                        }
                    }

                    is NetworkRequest.Error -> {
                        setupLoading(false, recentRecipeList)
                        root.showSnackBar(response.message!!)
                    }

                    else -> {}
                }
            }
        }
    }

    private fun fillRecentData(result: List<ResponseRecent.Result?>) {
        recentMoreAdapter.setData(result)
    }

    private fun initRecentRecycler() {
        binding.recentRecipeList.setupRecyclerview(
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            ), recentMoreAdapter
        )
    }

    private fun setupLoading(isShownLoading: Boolean, shimmer: ShimmerRecyclerView) {
        shimmer.apply {
            if (isShownLoading) showShimmer() else hideShimmer()
        }
    }

    private fun goToDetailRecentPage(id: Int) {
        val direction =
            RecentRecipeFragmentDirections.actionRecentRecipeFragmentToRecentDetailFragment(id)
        findNavController().navigate(direction)
    }
}