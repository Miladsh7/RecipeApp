package com.msh.recipapp.ui.bookMark

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.msh.recipapp.R
import com.msh.recipapp.base.BaseFragment
import com.msh.recipapp.databinding.FragmentBookMarkBinding
import com.msh.recipapp.models.home.ResponseVideo
import com.msh.recipapp.ui.bookMark.adapter.RecipeBookMarkAdapter
import com.msh.recipapp.ui.bookMark.adapter.VideoBookMarkAdapter
import com.msh.recipapp.utils.setupRecyclerview
import com.msh.recipapp.viewmodel.bookmark.RecipeBookMarkViewModel
import com.msh.recipapp.viewmodel.bookmark.VideoBookMarkViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BookMarkFragment : BaseFragment<FragmentBookMarkBinding>(
    R.layout.fragment_book_mark,
    FragmentBookMarkBinding::class
) {

    @Inject
    lateinit var videoBookMarkAdapter: VideoBookMarkAdapter

    private val recipeBookMarkAdapter by lazy {
        RecipeBookMarkAdapter(
            onItemClickListener = {goDetailPage(it)}
        )}

    private val videoBookMarkViewModel: VideoBookMarkViewModel by viewModels()
    private val recipeBookMarkViewModel: RecipeBookMarkViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            //--default--//
            videoBookMarkViewModel.readVideoBookMarkData.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    videoBookMarkAdapter.setData(it)
                    rvSavedRecipe.setupRecyclerview(
                        LinearLayoutManager(requireContext()),
                        videoBookMarkAdapter
                    )
                    rvSavedRecipe.visibility = View.VISIBLE
                    emptyLay.visibility = View.GONE

                } else {
                    rvSavedRecipe.visibility = View.GONE
                    emptyLay.visibility = View.VISIBLE
                }
            }

            txtBookmarkVideo.isSelected = true
            txtBookmarkRecipe.isSelected = false

            txtBookmarkVideo.setOnClickListener {
                txtBookmarkVideo.isSelected = true
                txtBookmarkRecipe.isSelected = false

                videoBookMarkViewModel.readVideoBookMarkData.observe(viewLifecycleOwner) {
                    if (it.isNotEmpty()) {
                        videoBookMarkAdapter.setData(it)
                        rvSavedRecipe.setupRecyclerview(
                            LinearLayoutManager(requireContext()),
                            videoBookMarkAdapter
                        )

                        rvSavedRecipe.visibility = View.VISIBLE
                        emptyLay.visibility = View.GONE

                    } else {
                        rvSavedRecipe.visibility = View.GONE
                        emptyLay.visibility = View.VISIBLE
                    }
                }

                videoBookMarkAdapter.setOnItemClickListener {
                    goDetailPage(it)
                }
            }

            txtBookmarkRecipe.setOnClickListener {
                txtBookmarkVideo.isSelected = false
                txtBookmarkRecipe.isSelected = true

                recipeBookMarkViewModel.readRecipeBookMarkData.observe(viewLifecycleOwner) {
                    if (it.isNotEmpty()) {
                        recipeBookMarkAdapter.setData(it)
                        rvSavedRecipe.setupRecyclerview(
                            LinearLayoutManager(requireContext()),
                            recipeBookMarkAdapter
                        )

                        rvSavedRecipe.visibility = View.VISIBLE
                        emptyLay.visibility = View.GONE

                    } else {
                        rvSavedRecipe.visibility = View.GONE
                        emptyLay.visibility = View.VISIBLE
                    }
                }

            }
        }
    }

    private fun goDetailPage(id: Int) {
        val action = BookMarkFragmentDirections.actionBookMarkFragmentToPopularDetailFragment2(id)
        findNavController().navigate(action)
    }

    private fun goDetailPage(data: ResponseVideo.Video) {
        val action = BookMarkFragmentDirections.actionBookMarkFragmentToVideoDetailFragment(data)
        findNavController().navigate(action)
    }
}