package com.rafiul.whatcanido.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rafiul.whatcanido.data.source.Task

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Task>?) {
    items?.let {
        (listView.adapter as TaskAdapter).submitList(items)
    }
}