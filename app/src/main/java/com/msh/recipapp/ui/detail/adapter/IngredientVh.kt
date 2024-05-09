package com.msh.recipapp.ui.detail.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.msh.recipapp.R
import com.msh.recipapp.databinding.ItemIngredientsBinding
import com.msh.recipapp.models.detail.ResponseDetail
import com.msh.recipapp.utils.BASE_URL_IMAGE_INGREDIENTS

class IngredientVh(
    private val binding: ItemIngredientsBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item:ResponseDetail.ExtendedIngredient) = with(binding){
        txtTitleIngredients.text = item.name
        txtUnitIngredients.text = "${item.amount} ${item.unit}"
        val image = "$BASE_URL_IMAGE_INGREDIENTS${item.image}"
        imgIngredient.load(image) {
            memoryCachePolicy(CachePolicy.ENABLED)
            error(R.drawable.ic_placeholder)
        }
    }
}