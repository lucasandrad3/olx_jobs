package com.example.querobicos

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth

class Bd(private val context: Context) {

    fun userLogado(): Boolean {
        val logado = FirebaseAuth.getInstance().currentUser
        return logado != null
    }


}
