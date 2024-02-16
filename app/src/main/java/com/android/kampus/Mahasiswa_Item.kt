package com.android.kampus

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.time.temporal.ValueRange

class Mahasiswa_Item (val ini:Context, val id_mahasiswa:MutableList<String>, val nama:MutableList<String>, val nim:MutableList<String>, val foto:MutableList<Int>) : RecyclerView.Adapter<Mahasiswa_Item.ViewHolder>(){
    //dapatkan dulu layoutnya mahasiswa_item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Mahasiswa_Item.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mahasiswa_item, parent, false)
        return ViewHolder(view)
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView){
        val txt_nama:TextView = ItemView.findViewById(R.id.txt_nama)
        val txt_nim:TextView = ItemView.findViewById(R.id.txt_nim)
        val iv_foto:ImageView = ItemView.findViewById(R.id.iv_foto)
        val btn_hapus:Button = ItemView.findViewById(R.id.btn_hapus)
    }
    override fun getItemCount(): Int {
        return nama.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txt_nama.text = nama.get(position)
        holder.txt_nim.text = nim.get(position)
        holder.iv_foto.setImageResource(foto.get(position))

        //btn_hapus ditekan
        holder.btn_hapus.setOnClickListener {
            //dapatkan id_mahasiswa sesuai urutan tombol yang ditekan
            val id_mahasiswa_terpilih:String = id_mahasiswa.get(position)

            //larikan ke activity mahasiswa_hapus
            val pindah:Intent = Intent(ini, Mahasiswa_Hapus::class.java)
            pindah.putExtra("id_mahasiswa_terpilih", id_mahasiswa_terpilih)
            ini.startActivity(pindah)
        }
    }
}
