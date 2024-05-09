package com.msh.recipapp.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.msh.recipapp.R
import com.msh.recipapp.base.BaseFragment
import com.msh.recipapp.databinding.FragmentProfileBinding
import com.msh.recipapp.ui.bookMark.adapter.RecipeBookMarkAdapter
import com.msh.recipapp.ui.bookMark.adapter.VideoBookMarkAdapter
import com.msh.recipapp.utils.setupRecyclerview
import com.msh.recipapp.viewmodel.bookmark.RecipeBookMarkViewModel
import com.msh.recipapp.viewmodel.bookmark.VideoBookMarkViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    R.layout.fragment_profile,
    FragmentProfileBinding::class
) {

    @Inject
    lateinit var videoBookMarkAdapter: VideoBookMarkAdapter

    private val recipeBookMarkAdapter by lazy { RecipeBookMarkAdapter(onItemClickListener = {goDetailPage(it)}) }
    private val videoBookMarkViewModel: VideoBookMarkViewModel by viewModels()
    private val recipeBookMarkViewModel: RecipeBookMarkViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            imgProfile.load(R.drawable.my_photo)

            videoBookMarkViewModel.readVideoBookMarkData.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    videoBookMarkAdapter.setData(it)
                    rvProfile.setupRecyclerview(
                        LinearLayoutManager(requireContext()),
                        videoBookMarkAdapter
                    )
                    rvProfile.visibility = View.VISIBLE
                    emptyLay.visibility = View.GONE
                }else{
                    rvProfile.visibility = View.GONE
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
                        rvProfile.setupRecyclerview(
                            LinearLayoutManager(requireContext()),
                            videoBookMarkAdapter
                        )
                        rvProfile.visibility = View.VISIBLE
                        emptyLay.visibility = View.GONE
                    }else{
                        rvProfile.visibility = View.GONE
                        emptyLay.visibility = View.VISIBLE
                    }
                }
            }

            txtBookmarkRecipe.setOnClickListener {
                txtBookmarkVideo.isSelected = false
                txtBookmarkRecipe.isSelected = true

                recipeBookMarkViewModel.readRecipeBookMarkData.observe(viewLifecycleOwner) {
                    if (it.isNotEmpty()) {
                        recipeBookMarkAdapter.setData(it)
                        rvProfile.setupRecyclerview(
                            LinearLayoutManager(requireContext()),
                            recipeBookMarkAdapter
                        )
                        rvProfile.visibility = View.VISIBLE
                        emptyLay.visibility = View.GONE
                    }else{
                        rvProfile.visibility = View.GONE
                        emptyLay.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun goDetailPage(id: Int) {
        val action = ProfileFragmentDirections.actionProfileFragmentToPopularDetailFragment2(id)
        findNavController().navigate(action)
    }

}