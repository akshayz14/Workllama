package com.example.wassignment.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.wassignment.models.Content

class DiffUtilCallback: DiffUtil.ItemCallback<Content>()  {
    override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Content, newItem: Content): Boolean {
        return oldItem.id == newItem.id
                && oldItem.name == newItem.name
                && oldItem.phone == newItem.phone
    }
}