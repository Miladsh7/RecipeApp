package com.msh.recipapp.ui.home.adapter.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.msh.recipapp.base.BaseDiffUtils
import com.msh.recipapp.databinding.ItemPopularBinding
import com.msh.recipapp.models.home.ResponsePopular

class PopularAdapter(
    private val onItemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<PopularVh>() {

    private var items = emptyList<ResponsePopular.Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularVh {
        val binding = ItemPopularBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PopularVh(binding,onItemClickListener)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: PopularVh, position: Int) {
        holder.onBind(items[position])
    }

    override fun onViewAttachedToWindow(holder: PopularVh) {
        super.onViewAttachedToWindow(holder)
        holder.initAnimation()
    }

    override fun onViewDetachedFromWindow(holder: PopularVh) {
        super.onViewDetachedFromWindow(holder)
        holder.clearAnimation()
    }


    fun setData(data: List<ResponsePopular.Result?>) {
        val adapterDiffUtils = BaseDiffUtils(items, data)
        val diffUtils = DiffUtil.calculateDiff(adapterDiffUtils)
        items = data as List<ResponsePopular.Result>
        diffUtils.dispatchUpdatesTo(this)
    }

}