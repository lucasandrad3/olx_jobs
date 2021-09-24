package com.example.querobicos.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.querobicos.Model.RegisterBicos
import com.example.querobicos.R
import com.example.querobicos.databinding.ActivityFormLoginBinding
import com.example.querobicos.databinding.FragmentBicosBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class Bicos : Fragment() {
    private lateinit var Adapter:GroupAdapter<GroupieViewHolder>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bicos, container, false)

    }

    private var fragmentsBicos: FragmentBicosBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bindigBicos= FragmentBicosBinding.bind(view)
        fragmentsBicos = bindigBicos
        val recycle_bicos = bindigBicos.recycleBicos


        Adapter = GroupAdapter()
        recycle_bicos.adapter =Adapter

        listarBicos()

    }

    private inner class bicosItem(internal val docBicos:RegisterBicos): Item<GroupieViewHolder>() {

        override fun bind(viewHolder: GroupieViewHolder, position: Int) {

            viewHolder.itemView.findViewById<TextView>(R.id.text_title).text = docBicos.title.toString()
            viewHolder.itemView.findViewById<TextView>(R.id.text_description).text = docBicos.description.toString()
            viewHolder.itemView.findViewById<TextView>(R.id.text_price).text = docBicos.value.toString()

            Picasso.get().load(docBicos.idImage).into(viewHolder.itemView.findViewById<ImageView>(R.id.image_bico))

        }

        override fun getLayout(): Int {
            return R.layout.lista_bicos
        }

    }

    private fun listarBicos() {
        FirebaseFirestore.getInstance().collection("Bicos")
            .addSnapshotListener { value, error ->
                error?.let {
                    return@addSnapshotListener
                }

                value?.let {
                    for (doc in value){
                        val docBicos = doc.toObject(RegisterBicos::class.java)
                        Adapter.add(bicosItem(docBicos))
                    }
                }
            }
    }


}