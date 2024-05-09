package com.msh.recipapp.ui.notification.adapter

import androidx.recyclerview.widget.RecyclerView
import com.msh.recipapp.data.notification.Item
import com.msh.recipapp.databinding.ItemDataNotificationBinding


class DataViewHolder(
    private val binding: ItemDataNotificationBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Item.DataItem) = with(binding) {
        txtTitleNotification.text = item.title
        txtDescNotification.text = item.desc
    }
}