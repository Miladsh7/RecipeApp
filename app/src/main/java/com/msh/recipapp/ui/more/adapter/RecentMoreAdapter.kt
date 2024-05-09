package com.msh.recipapp.ui.more.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.msh.recipapp.base.BaseDiffUtils
import com.msh.recipapp.databinding.ItemRecentRecipeBinding
import com.msh.recipapp.models.home.ResponseRecent

class RecentMoreAdapter(
    private var onItemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<RecentMoreVh>() {

    private var items = emptyList<ResponseRecent.Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentMoreVh {
        val view = ItemRecentRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecentMoreVh(view, onItemClickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecentMoreVh, position: Int) {
        holder.bind(items[position])
    }

    fun setData(data: List<ResponseRecent.Result?>) {
        val adapterDiffUtils = BaseDiffUtils(items, data)
        val diffUtils = DiffUtil.calculateDiff(adapterDiffUtils)
        items = data as List<ResponseRecent.Result>
        diffUtils.dispatchUpdatesTo(this)
    }
}