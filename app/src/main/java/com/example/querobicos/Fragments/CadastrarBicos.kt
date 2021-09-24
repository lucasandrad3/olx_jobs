package com.example.querobicos.Fragments

import android.content.Intent
import android.graphics.ImageDecoder
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.renderscript.ScriptGroup
import android.widget.Toast
import androidx.core.graphics.decodeBitmap
import com.afollestad.materialdialogs.MaterialDialog
import com.example.querobicos.Model.RegisterBicos
import com.example.querobicos.databinding.ActivityCadastrarBicosBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class CadastrarBicos : AppCompatActivity() {
    private lateinit var binding: ActivityCadastrarBicosBinding
    private var imagePath:Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastrarBicosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val image = binding.imageBico
        val btCadBico = binding.btCadBico

        image.setOnClickListener {
            selectionPhoto()
            Toast.makeText(this, "Selecione uma imagem", Toast.LENGTH_SHORT).show()
        }
        btCadBico.setOnClickListener {
            saveData()
        }

    }

    private fun selectionPhoto(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 0){
            imagePath = data?.data
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val bitmap = ImageDecoder.createSource(contentResolver, imagePath!!)
                val image_bitmap = ImageDecoder.decodeBitmap(bitmap)
                binding.imageBico.setImageBitmap(image_bitmap)



            } else {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imagePath)
                binding.imageBico.setImageBitmap(bitmap)

            }
            rotateImage()
            }
        }

    private fun rotateImage(){
        val image_view = binding.imageBico
        val exif = ExifInterface(imagePath?.path!!)
        val orientacao = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);


        Toast.makeText(this, image_view.rotation.toString(), Toast.LENGTH_SHORT).show()


        image_view.rotation = ((orientacao*(-1)).toFloat())
    }

    private fun saveData(){
        //save Image
        val IdImage = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference(
            "ImagesBicos/${IdImage}"
        )

        imagePath.let {
            ref.putFile(it!!)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener {
                        val title = binding.editTitle.text.toString()
                        val description = binding.editDescricao.text.toString()
                        val value = binding.editValue.text.toString().toDouble()

                        val bicos = RegisterBicos(title, description, value, it.toString())
                        val id = FirebaseAuth.getInstance().uid
                        FirebaseFirestore.getInstance().collection("Bicos").document(id!!)
                            .set(bicos)

                        MaterialDialog(this).show {
                            title(text = "Bico criado")
                            message(text = "Bico cadastrado com sucesso!!")
                        }


                    }
                }

        }


    }

}
