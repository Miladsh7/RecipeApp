package com.msh.recipapp.ui.bookMark.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.msh.recipapp.base.BaseDiffUtils
import com.msh.recipapp.data.database.entity.bookmark.RecipeBookMarkEntity
import com.msh.recipapp.databinding.ItemRecipeBookmarkBinding

class RecipeBookMarkAdapter(
    private val onItemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<RecipeVh>() {

    private var items = emptyList<RecipeBookMarkEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeVh {
        val binding = ItemRecipeBookmarkBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecipeVh(binding, onItemClickListener)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecipeVh, position: Int) {
        holder.onBind(items[position])
    }


    fun setData(data: List<RecipeBookMarkEntity>) {
        val adapterDiffUtils = BaseDiffUtils(items, data)
        val diffUtils = DiffUtil.calculateDiff(adapterDiffUtils)
        items = data
        diffUtils.dispatchUpdatesTo(this)
    }
}