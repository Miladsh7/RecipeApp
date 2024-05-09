package com.msh.recipapp.ui.bookMark.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.msh.recipapp.data.database.entity.bookmark.RecipeBookMarkEntity
import com.msh.recipapp.databinding.ItemRecipeBookmarkBinding

class RecipeVh(
    private val binding: ItemRecipeBookmarkBinding,
    private val onItemClickListener: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: RecipeBookMarkEntity) = with(binding) {
        txtTitleRecipe.text = item.result.title
        txtStarRating.text = item.result.aggregateLikes.toString()
        txtIngredientsRecipe.text = item.result.extendedIngredients?.size.toString()

        imgVideo.load(item.result.image)

        root.setOnClickListener {
            onItemClickListener(item.id)
        }
    }
}