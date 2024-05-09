package com.msh.recipapp.ui.notification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msh.recipapp.data.notification.Item
import com.msh.recipapp.databinding.ItemDataNotificationBinding
import com.msh.recipapp.databinding.ItemHeaderNotificationBinding
import java.lang.IllegalArgumentException

class ItemNotificationAdapter(
    private val items: List<Item>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            Item.VIEW_TYPE_DATA -> {DataViewHolder(ItemDataNotificationBinding.inflate(
                LayoutInflater.from(parent.context),parent,false))}
            Item.VIEW_TYPE_HEADER -> {HeaderViewHolder(ItemHeaderNotificationBinding.inflate(
                LayoutInflater.from(parent.context),parent,false))}
            else -> throw IllegalArgumentException("Unknown view type")
        }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is DataViewHolder -> holder.bind(items[position] as Item.DataItem)
            is HeaderViewHolder -> holder.bind(items[position] as Item.HeaderItem)
        }
    }

    override fun getItemViewType(position: Int): Int = items[position].viewType

}