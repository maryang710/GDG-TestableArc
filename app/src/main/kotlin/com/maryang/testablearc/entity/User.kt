package com.maryang.testablearc.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("id")
    override val id: Long,
    @SerializedName("login")
    val userName: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
) : Identifier, Parcelable
