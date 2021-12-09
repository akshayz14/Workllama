package com.example.wassignment.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Entity(tableName = "contactData")
@Parcelize
data class Content(

    @SerializedName("email")
    val email: String,

    @SerializedName("id")
    @PrimaryKey
    val id: Int,

    @SerializedName("isStarred")
    var isStarred: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("thumbnail")
    val thumbnail: String
) : Parcelable