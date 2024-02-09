package com.android.kampus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Mahasiswa : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mahasiswa)

        val rv_mahasiswa:RecyclerView = findViewById(R.id.rv_mahasiswa)

        val nama:MutableList<String> = mutableListOf()
        nama.add("Haikal")
        nama.add("Arif")
        nama.add("Akira")
        val nim:MutableList<String> = mutableListOf()
        nim.add("23.22.0001")
        nim.add("23.22.0002")
        nim.add("23.22.0003")

        val mi = Mahasiswa_Item(nama, nim)

        rv_mahasiswa.adapter = mi
        rv_mahasiswa.layoutManager = LinearLayoutManager(this)
    }
}