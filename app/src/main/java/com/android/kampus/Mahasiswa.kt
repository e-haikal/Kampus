package com.android.kampus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Mahasiswa : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mahasiswa)

        val rv_mahasiswa:RecyclerView = findViewById(R.id.rv_mahasiswa)

        val nama:MutableList<String> = mutableListOf()
        nama.add("Chintia")
        nama.add("Akira")
        nama.add("Laras")
        nama.add("Bilqis")

        val nim:MutableList<String> = mutableListOf()
        nim.add("23.22.0001")
        nim.add("23.22.0002")
        nim.add("23.22.0003")
        nim.add("23.22.0004")

        val foto:MutableList<Int> = mutableListOf()
        foto.add(R.drawable.produk_alesha)
        foto.add(R.drawable.produk_bergo)
        foto.add(R.drawable.produk_pasmina)
        foto.add(R.drawable.produk_zayana)

        val mi = Mahasiswa_Item(nama, nim, foto)

        rv_mahasiswa.adapter = mi
        rv_mahasiswa.layoutManager = GridLayoutManager (this, 2)
    }
}