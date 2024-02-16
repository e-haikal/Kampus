package com.android.kampus

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Mahasiswa : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mahasiswa)

        val rv_mahasiswa:RecyclerView = findViewById(R.id.rv_mahasiswa)

        val id_mahasiswa:MutableList<String> = mutableListOf()
        val nama:MutableList<String> = mutableListOf()
        val nim:MutableList<String> = mutableListOf()
        val foto:MutableList<Int> = mutableListOf()

        //panggil database kampus
        val dbkampus:SQLiteDatabase = openOrCreateDatabase("kampus", MODE_PRIVATE, null)

        //gali data mahasiswa
        val gali_mahasiswa = dbkampus.rawQuery("SELECT * FROM mahasiswa", null)

        while (gali_mahasiswa.moveToNext())
        {
            id_mahasiswa.add(gali_mahasiswa.getString(0))
            nama.add(gali_mahasiswa.getString(1))
            nim.add(gali_mahasiswa.getString(2))
            foto.add(R.drawable.noimage)
        }

        val mi = Mahasiswa_Item(this,   id_mahasiswa, nama, nim, foto)

        rv_mahasiswa.adapter = mi
        rv_mahasiswa.layoutManager = GridLayoutManager (this, 2)

        //btn_tambah
        val btn_tambah:Button = findViewById(R.id.btn_tambah)
        btn_tambah.setOnClickListener {
            val pindah:Intent = Intent(this, Mahasiswa_Tambah::class.java)
            startActivity(pindah)
        }
    }
}