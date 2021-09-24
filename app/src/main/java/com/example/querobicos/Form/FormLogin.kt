package com.example.querobicos.Form

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.example.querobicos.Bd
import com.example.querobicos.MainActivity
import com.example.querobicos.R
import com.example.querobicos.TelaPrincipal
import com.example.querobicos.databinding.ActivityFormLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class FormLogin : AppCompatActivity() {

    private lateinit var binding:ActivityFormLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        verificarUserLogado()

        val textCreateAccount = binding.textCreateAccount

        val bt_login = binding.btLogin

        textCreateAccount.setOnClickListener {
            val intent = Intent(this, FormCadastro::class.java)
            startActivity(intent)
        }
        bt_login.setOnClickListener {
            authUser()
        }




    }
    private fun authUser(){
        val email = binding.editEmailLogin.text.toString()
        val senha = binding.editPasswordLogin.text.toString()
        if(email.isEmpty() || senha.isEmpty()){
            msgErro("Preencha todos os campos!!")

        }else {

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
                .addOnSuccessListener {
                    binding.loadScreen.visibility = View.VISIBLE
                    Handler().postDelayed({openPrincipal()}, 2000)
                }.addOnFailureListener {
                msgErro("Erro")
            }
        }
    }

    private fun msgErro(text:String){
        Snackbar.make(binding.idLayoutLogin, text, Snackbar.LENGTH_INDEFINITE)
            .setBackgroundTint(Color.parseColor("#673ab7"))
            .setTextColor(Color.WHITE)
            .setAction("ok", View.OnClickListener {  })
            .show()
    }
    private fun openPrincipal(){
        val intent = Intent(this, TelaPrincipal::class.java)
        startActivity(intent)
        finish()
    }
    private fun verificarUserLogado(){
        val bd = Bd(applicationContext)
        if(bd.userLogado()){
            openPrincipal()
        }
    }

}