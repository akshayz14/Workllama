package com.example.wassignment.models.star

data class UpdatedContactInfo(
    val email: String,
    val id: Int,
    val isStarred: Boolean,
    val name: String,
    val phone: String,
    val thumbnail: String
)