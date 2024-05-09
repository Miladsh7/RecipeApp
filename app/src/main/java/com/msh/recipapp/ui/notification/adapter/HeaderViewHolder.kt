package com.msh.recipapp.ui.notification.adapter

import androidx.recyclerview.widget.RecyclerView
import com.msh.recipapp.data.notification.Item
import com.msh.recipapp.databinding.ItemHeaderNotificationBinding

class HeaderViewHolder(
    private val binding: ItemHeaderNotificationBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item:Item.HeaderItem) = with(binding){
        txtTitleHeaderNotification.text = item.title
    }
}