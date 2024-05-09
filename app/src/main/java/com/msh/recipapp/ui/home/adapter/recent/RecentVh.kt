package com.msh.recipapp.ui.home.adapter.recent

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.msh.recipapp.R
import com.msh.recipapp.databinding.ItemRecentBinding
import com.msh.recipapp.models.home.ResponseRecent

class RecentVh(
    private val binding: ItemRecentBinding,
    private val onItemClickListener: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: ResponseRecent.Result) = with(binding) {

        txtTitleRecent.text = item.title
        imgRecent.load(item.image) {
            memoryCachePolicy(CachePolicy.ENABLED)
            placeholder(R.drawable.ic_placeholder)
        }

        root.setOnClickListener {
            onItemClickListener(item.id!!)
        }
    }
}