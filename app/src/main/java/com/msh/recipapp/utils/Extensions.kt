package com.msh.recipapp.utils

import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

fun RecyclerView.setupRecyclerview(
    myLayoutManager: RecyclerView.LayoutManager,
    myAdapter: RecyclerView.Adapter<*>
) {
    this.apply {
        layoutManager = myLayoutManager
        setHasFixedSize(true)
        adapter = myAdapter
    }
}

fun View.showSnackBar(message: String) {

    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun Int.toThreeDigits(): String {
    val absValue = Math.abs(this)
    return when {
        absValue < 100 -> "$absValue"
        absValue < 1000 -> "$absValue"
        else -> absValue.toString().substring(0, 3)
    }
}

fun Int.toMinutesAndSeconds(): Pair<String, String> {
    val minutes = this / 60
    val seconds = this % 60

    val formattedMinutes = minutes.toString().padStart(2, '0')
    val formattedSeconds = seconds.toString().padStart(2, '0')

    return formattedMinutes to formattedSeconds
}

fun <T> LiveData<T>.onceObserve(owner: LifecycleOwner, observe: Observer<T>) {
    observe(owner, object : Observer<T> {
        override fun onChanged(t: T) {
            removeObserver(this)
            observe.onChanged(t)
        }
    })
}

fun Int.minToHour(): String {
    val time: String
    val hours: Int = this / 60
    val minutes: Int = this % 60
    time = if (hours > 0) "${hours}h:${minutes}min" else "${minutes}min"
    return time
}

fun View.isVisible(isShownLoading: Boolean, container: View) {
    if (isShownLoading) {
        this.isVisible = true
        container.isVisible = false
    } else {
        this.isVisible = false
        container.isVisible = true
    }
}


