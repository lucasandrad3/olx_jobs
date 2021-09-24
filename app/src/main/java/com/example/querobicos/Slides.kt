package com.example.querobicos

import android.content.Intent
import android.os.Bundle
import com.example.querobicos.Form.FormLogin
import com.google.firebase.auth.FirebaseAuth
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide

class Slides : IntroActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_slides)


        verificarUserLogado()


        isButtonBackVisible = false

        addSlide(
            SimpleSlide.Builder()
                .title("Encontre Serviços")
                .description("Encontre serviços temporários e ganhe dinheiro para resolve-los")
                .background(R.color.roxo)
                .image(R.drawable.money)
                .build()
        )

        addSlide(
            SimpleSlide.Builder()
                .title("Encontre Profissionais")
                .background(R.color.roxo)
                .description("Divulgue algum serviço, e encontre um profissional que resolva")
                .image(R.drawable.client)
                .build()
        )


    }
    private fun verificarUserLogado(){
        val bd = Bd(applicationContext)
        if(bd.userLogado()){
            openPrincipal()
        }
    }
    private fun openPrincipal(){
        val intent = Intent(this, TelaPrincipal::class.java)
        startActivity(intent)
        finish()
    }


    override fun onDestroy() {
        val intent = Intent(this, FormLogin::class.java)
        startActivity(intent)
        super.onDestroy()
    }
}