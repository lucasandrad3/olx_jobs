package com.example.querobicos.Form

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.querobicos.Model.RegisterUser
import com.example.querobicos.R
import com.example.querobicos.databinding.ActivityFormCadastroBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import java.text.SimpleDateFormat

class FormCadastro : AppCompatActivity() {

    private lateinit var binding : ActivityFormCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        val cpf = binding.editCpfCreate
        val bt_create = binding.btCreateAccount


        bt_create.setOnClickListener {
            CreateUser()
        }


    }

    private fun CreateUser() {

        val nome = binding.editNameCreate.text.toString()
        val email = binding.editEmailCreate.text.toString()
        val senha = binding.editPasswordCreate.text.toString()
        val cpf = binding.editCpfCreate.text.toString().toInt()
        if(email.isEmpty() || senha.isEmpty()){
            Snackbar.make(binding.idLayoutCreate, "Preencha todos os campos!!", Snackbar.LENGTH_INDEFINITE)
                .setBackgroundTint(Color.parseColor("#673ab7"))
                .setTextColor(Color.WHITE)
                .setAction("ok", View.OnClickListener {  })
                .show()
        }else{
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha).addOnSuccessListener{
                val uid = it.user?.uid
                val user = RegisterUser(nome, email, senha, cpf)
                FirebaseFirestore.getInstance().collection("User").document(uid!!)
                    .set(user).addOnSuccessListener {
                        Toast.makeText(this, "Cadastro realizado com sucesso",Toast.LENGTH_SHORT).show()
                        binding.editEmailCreate.setText("")
                        binding.editPasswordCreate.setText("")
                        binding.editCpfCreate.setText("")
                        binding.editNameCreate.setText("")
                        finish()
                    }

            }.addOnFailureListener {
                Toast.makeText(this, "Erro ao cadastrar usuário!!",Toast.LENGTH_SHORT).show()
            }
        }
    }
}