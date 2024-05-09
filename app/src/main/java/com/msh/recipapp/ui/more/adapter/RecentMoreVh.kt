package com.msh.recipapp.ui.more.adapter

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.msh.recipapp.R
import com.msh.recipapp.databinding.ItemRecentRecipeBinding
import com.msh.recipapp.models.home.ResponseRecent

class RecentMoreVh(
    private val binding: ItemRecentRecipeBinding,
    private val onItemClickListener: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ResponseRecent.Result) = with(binding) {
        txtTitleRecentRecipe.text = item.title
        imgRecentRecipe.loadImage(item.image)

        root.setOnClickListener {
            onItemClickListener(item.id!!)
        }
    }

}

fun ImageView.loadImage(data: Any?) {
    load(data) {
        crossfade(true)
        crossfade(800)
        placeholder(R.drawable.ic_placeholder)
    }
}