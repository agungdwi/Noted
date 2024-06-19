package com.compose.noted.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    val id: Int?,
    val title: String,
    val content: String,
    val color: Int,
    val timeStamp: Long,
) : Parcelable
