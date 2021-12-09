package com.example.wassignment.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ContactKeys(
    @PrimaryKey
    val pageNumber: Int
)