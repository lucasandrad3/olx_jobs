package com.example.querobicos.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class RegisterBicos(
    val title:String? = null,
    val description:String? = null,
    val value:Double? = null,
    val idImage:String? = null

):Parcelable