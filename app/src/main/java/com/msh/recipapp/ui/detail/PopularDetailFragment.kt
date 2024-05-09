package com.msh.recipapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.request.CachePolicy
import com.msh.recipapp.R
import com.msh.recipapp.base.BaseFragment
import com.msh.recipapp.data.database.entity.bookmark.RecipeBookMarkEntity
import com.msh.recipapp.databinding.FragmentPopularDetailBinding
import com.msh.recipapp.models.detail.ResponseDetail
import com.msh.recipapp.ui.detail.adapter.IngredientAdapter
import com.msh.recipapp.utils.MY_API_KEY
import com.msh.recipapp.utils.NetworkChecker
import com.msh.recipapp.utils.NetworkRequest
import com.msh.recipapp.utils.isVisible
import com.msh.recipapp.utils.minToHour
import com.msh.recipapp.utils.setupRecyclerview
import com.msh.recipapp.utils.showSnackBar
import com.msh.recipapp.viewmodel.detail.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PopularDetailFragment : BaseFragment<FragmentPopularDetailBinding>(
    R.layout.fragment_popular_detail,
    FragmentPopularDetailBinding::class
) {

    private val detailViewModel: DetailViewModel by viewModels()
    private val args: PopularDetailFragmentArgs by navArgs()
    private val ingredientAdapter by lazy { IngredientAdapter() }

    private var popularId = 0
    private var isExistCache = false
    private var isExistRecipeBookMark = false

    @Inject
    lateinit var networkChecker: NetworkChecker
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.let {
            popularId = args.popularId
            if (popularId > 0)
                checkExistsDetailInCache(popularId)
        }

        //check internet connection
        viewLifecycleOwner.lifecycleScope.launch {
            networkChecker.checkNetworkAvailability().collect { state ->
                delay(200)
                if (isExistCache.not()) {
                    initInternetLayout(state)
                }
                if (state) {
                    loadDetailDataFromApi()
                }
            }
        }

        //back page
        binding.backImg.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    //--Detail--//
    //remote//
    private fun loadDetailDataFromApi() {
        detailViewModel.callDetailApi(popularId, MY_API_KEY)
        binding.apply {
            detailViewModel.detailData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkRequest.Loading -> {
                        loading.isVisible(true, contentLay)
                    }

                    is NetworkRequest.Success -> {
                        loading.isVisible(false, contentLay)
                        response.data?.let { data ->
                            initViewWithData(data)
                        }
                    }

                    is NetworkRequest.Error -> {
                        loading.isVisible(false, contentLay)
                        binding.root.showSnackBar(response.message!!)
                    }
                }
            }
        }
    }

    private fun checkExistsDetailInCache(id: Int) {
        detailViewModel.existsDetail(id)

        detailViewModel.existsDetailData.observe(viewLifecycleOwner) {
            isExistCache = it
            if (it) {
                loadDetailDataFromDb()
                binding.contentLay.isVisible = true
            }
        }
    }

    //local//
    private fun loadDetailDataFromDb() {
        detailViewModel.readDetailFromDb(popularId).observe(viewLifecycleOwner) {
            initViewWithData(it.result)
        }
    }

    private fun initViewWithData(data: ResponseDetail) {
        binding.apply {

            detailViewModel.existsRecipeBookMark(data.id!!)
            checkExistsRecipeBookMark()

            favoriteImg.setOnClickListener {
                if (isExistRecipeBookMark) deleteRecipeBookMark(data) else saveRecipeBookMark(data)
            }
            val image = data.image
            coverImg.load(image) {
                crossfade(true)
                crossfade(800)
                memoryCachePolicy(CachePolicy.ENABLED)
                error(R.drawable.ic_placeholder)
            }

            data.sourceUrl?.let {url->
                sourceImg.isVisible = true
                sourceImg.setOnClickListener {
                    val direction = PopularDetailFragmentDirections.actionPopularDetailFragment2ToWebViewFragment(url)
                    findNavController().navigate(direction)
                }
            }
            timeTxt.text = data.readyInMinutes!!.minToHour()
            txtTitlePopularDetail.text = data.title
            txtIngredientsCount.text =
                "${data.extendedIngredients!!.size} ${getString(R.string.Recipe_txt_items)}"
            data.extendedIngredients.toMutableList().let { initIngredientList(it) }
        }
    }

    private fun initIngredientList(list: MutableList<ResponseDetail.ExtendedIngredient?>) {
        if (list.isNotEmpty()) {
            ingredientAdapter.setData(list)
            binding.ingredientList.setupRecyclerview(
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false),
                ingredientAdapter
            )
        }
    }

    private fun initInternetLayout(isConnected: Boolean) {
        binding.internetLay.isVisible = isConnected.not()
    }

    //--BookMark--//
    private fun saveRecipeBookMark(data: ResponseDetail) {
        val entity = RecipeBookMarkEntity(data.id!!, data)
        detailViewModel.saveRecipeBookMark(entity)
        binding.favoriteImg.apply {
            DrawableCompat.setTint(drawable, resources.getColor(R.color.Primary_50))
        }
    }

    private fun deleteRecipeBookMark(data: ResponseDetail) {
        val entity = RecipeBookMarkEntity(data.id!!, data)
        detailViewModel.deleteRecipeBookMark(entity)
        binding.favoriteImg.apply {
            DrawableCompat.setTint(drawable, resources.getColor(R.color.mediumGray))
        }
    }

    private fun checkExistsRecipeBookMark() {
        detailViewModel.existsRecipeBookMarkData.observe(viewLifecycleOwner) {
            isExistRecipeBookMark = it
            if (it) {
                binding.favoriteImg.apply {
                    DrawableCompat.setTint(drawable, resources.getColor(R.color.Primary_50))
                }
            } else {
                binding.favoriteImg.apply {
                    DrawableCompat.setTint(drawable, resources.getColor(R.color.mediumGray))
                }
            }
        }
    }
}