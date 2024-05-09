package com.msh.recipapp.ui.home.adapter.popular

import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.msh.recipapp.R
import com.msh.recipapp.databinding.ItemPopularBinding
import com.msh.recipapp.models.home.ResponsePopular

class PopularVh(
    private val binding: ItemPopularBinding,
    private val onItemClickListener: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: ResponsePopular.Result) = with(binding) {
        txtTitlePopular.text = item.title
        imgCircle.load(item.image) {
            memoryCachePolicy(CachePolicy.ENABLED)
        }

        root.setOnClickListener {
            onItemClickListener(item.id!!)
        }
    }

    fun initAnimation() {
        binding.root.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.item_anim)
    }

    fun clearAnimation() {
        binding.root.clearAnimation()
    }
}