package com.msh.recipapp.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.msh.recipapp.R
import com.msh.recipapp.base.BaseDiffUtils
import com.msh.recipapp.databinding.ItemIngredientsBinding
import com.msh.recipapp.models.detail.ResponseDetail
import com.msh.recipapp.utils.BASE_URL_IMAGE_INGREDIENTS

class IngredientAdapter : RecyclerView.Adapter<IngredientVh>() {

    private var items = emptyList<ResponseDetail.ExtendedIngredient?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientVh {
        val binding = ItemIngredientsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IngredientVh(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: IngredientVh, position: Int) {
        items[position]?.let { holder.onBind(it) }
    }

    fun setData(data: List<ResponseDetail.ExtendedIngredient?>) {
        val adapterDiffUtils = BaseDiffUtils(items, data)
        val diffUtils = DiffUtil.calculateDiff(adapterDiffUtils)
        items = data
        diffUtils.dispatchUpdatesTo(this)
    }
}