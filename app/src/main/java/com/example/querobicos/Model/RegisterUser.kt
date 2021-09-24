package com.example.querobicos.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class RegisterUser(
    val nome:String? = null,
    val email:String? = null,
    val senha:String? = null,
    var cpf:Int? = null

):Parcelable
