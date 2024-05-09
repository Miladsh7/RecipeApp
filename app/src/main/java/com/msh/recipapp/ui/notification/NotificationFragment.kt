package com.msh.recipapp.ui.notification

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.msh.recipapp.R
import com.msh.recipapp.base.BaseFragment
import com.msh.recipapp.data.notification.Item
import com.msh.recipapp.databinding.FragmentNotificationBinding
import com.msh.recipapp.ui.notification.adapter.ItemNotificationAdapter
import com.msh.recipapp.utils.setupRecyclerview

class NotificationFragment : BaseFragment<FragmentNotificationBinding>(
    R.layout.fragment_notification,
    FragmentNotificationBinding::class
) {

    lateinit var itemNotificationAdapter: ItemNotificationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            //--Default--//
            txtAllNotification.isSelected = true
            txtReadNotification.isSelected = false
            txtUnreadNotification.isSelected = false

            itemNotificationAdapter = ItemNotificationAdapter(myDataList(txtAllNotification))
            rvNotification.setupRecyclerview(
                LinearLayoutManager(requireContext()),
                itemNotificationAdapter
            )

            //All//
            txtAllNotification.setOnClickListener {

                txtAllNotification.isSelected = true
                txtReadNotification.isSelected = false
                txtUnreadNotification.isSelected = false

                itemNotificationAdapter = ItemNotificationAdapter(myDataList(txtAllNotification))
                rvNotification.setupRecyclerview(
                    LinearLayoutManager(requireContext()),
                    itemNotificationAdapter
                )
            }

            //unread//
            txtUnreadNotification.setOnClickListener {

                txtAllNotification.isSelected = false
                txtReadNotification.isSelected = false
                txtUnreadNotification.isSelected = true

                itemNotificationAdapter = ItemNotificationAdapter(myDataList(txtUnreadNotification))
                rvNotification.setupRecyclerview(
                    LinearLayoutManager(requireContext()),
                    itemNotificationAdapter
                )
            }

            //Read//
            txtReadNotification.setOnClickListener {

                txtAllNotification.isSelected = false
                txtReadNotification.isSelected = true
                txtUnreadNotification.isSelected = false

                itemNotificationAdapter = ItemNotificationAdapter(myDataList(txtReadNotification))
                rvNotification.setupRecyclerview(
                    LinearLayoutManager(requireContext()),
                    itemNotificationAdapter
                )
            }
        }
    }

    private fun myDataList(textview: AppCompatTextView): List<Item> {
        val itemList: MutableList<Item> = mutableListOf()
        when (textview) {
            binding.txtAllNotification -> {

                itemList.add(Item.HeaderItem("Today"))
                itemList.add(
                    Item.DataItem(
                        "New recipe", "Far far away, behind the word mountains, " +
                                "far from the countries.Far far away, " +
                                "behind the word mountains, far from the countries."
                    )
                )
                itemList.add(
                    Item.DataItem(
                        "Don’t forget to try your saved recipe", "Far far away, " +
                                "behind the word mountains, far from the countries."
                    )
                )
                itemList.add(Item.HeaderItem("Yesterday"))
                itemList.add(
                    Item.DataItem(
                        "New recipe", "Far far away, behind the word mountains, " +
                                "far from the countries.Far far away, " +
                                "behind the word mountains, far from the countries."
                    )
                )
                itemList.add(
                    Item.DataItem(
                        "Don’t forget to try your saved recipe", "Far far away, " +
                                "behind the word mountains, far from the countries."
                    )
                )
            }

            binding.txtReadNotification -> {
                itemList.add(Item.HeaderItem("Yesterday"))
                itemList.add(
                    Item.DataItem(
                        "New recipe", "Far far away, behind the word mountains, " +
                                "far from the countries.Far far away, " +
                                "behind the word mountains, far from the countries."
                    )
                )
                itemList.add(
                    Item.DataItem(
                        "Don’t forget to try your saved recipe", "Far far away, " +
                                "behind the word mountains, far from the countries."
                    )
                )
            }

            binding.txtUnreadNotification -> {
                itemList.add(Item.HeaderItem("Today"))
                itemList.add(
                    Item.DataItem(
                        "New recipe", "Far far away, behind the word mountains, " +
                                "far from the countries.Far far away, " +
                                "behind the word mountains, far from the countries."
                    )
                )
                itemList.add(
                    Item.DataItem(
                        "Don’t forget to try your saved recipe", "Far far away, " +
                                "behind the word mountains, far from the countries."
                    )
                )
            }
        }

        return itemList
    }
}