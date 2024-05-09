package com.msh.recipapp.ui.home.adapter.recent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.msh.recipapp.R
import com.msh.recipapp.base.BaseDiffUtils
import com.msh.recipapp.databinding.ItemRecentBinding
import com.msh.recipapp.models.home.ResponseRecent
import com.msh.recipapp.ui.home.adapter.recent.RecentVh
import javax.inject.Inject

class RecentAdapter(
    private val onItemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<RecentVh>() {

    private var items = emptyList<ResponseRecent.Result>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentVh {
        val binding = ItemRecentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecentVh(binding,onItemClickListener)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecentVh, position: Int) {
        holder.onBind(items[position])
    }

    fun setData(data: List<ResponseRecent.Result?>) {
        val adapterDiffUtils = BaseDiffUtils(items, data)
        val diffUtils = DiffUtil.calculateDiff(adapterDiffUtils)
        items = data as List<ResponseRecent.Result>
        diffUtils.dispatchUpdatesTo(this)
    }
}