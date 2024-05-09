package com.msh.recipapp.ui.onBoarding

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.msh.recipapp.R
import com.msh.recipapp.base.BaseFragment
import com.msh.recipapp.databinding.FragmentOnBoardingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>(
    R.layout.fragment_on_boarding,
    FragmentOnBoardingBinding::class
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            delay(200)
            binding.apply {
                btnStart.setOnClickListener {
                    findNavController().navigate(R.id.action_onBoardingFragment_to_homeFragment)
                }
            }
        }
    }

}