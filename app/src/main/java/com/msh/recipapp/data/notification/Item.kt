package com.msh.recipapp.data.notification

sealed class Item(val viewType: Int) {
    data class DataItem(val title: String, val desc: String) : Item(VIEW_TYPE_DATA)
    data class HeaderItem(val title: String) : Item(VIEW_TYPE_HEADER)

    companion object {
        const val VIEW_TYPE_DATA = 0
        const val VIEW_TYPE_HEADER = 1
    }
}