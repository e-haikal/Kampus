package com.android.kampus

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Mahasiswa_Tambah : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mahasiswa_tambah)
        val edt_nim:EditText = findViewById(R.id.edt_nim)
        val edt_nama:EditText = findViewById(R.id.edt_nama)
        val btn_simpan:Button = findViewById(R.id.btn_simpan)

        //btn_simpan ditekan
        btn_simpan.setOnClickListener{
            val isi_nim:String = edt_nim.text.toString()
            val isi_nama:String = edt_nama.text.toString()

            //query simpan ke database
            val dbkampus:SQLiteDatabase = openOrCreateDatabase("kampus", MODE_PRIVATE, null)
            val eksekutor = dbkampus.rawQuery("INSERT INTO mahasiswa (nim_mahasiswa, nama_mahasiswa) VALUES ('$isi_nim','$isi_nama')", null)
            eksekutor.moveToNext()

            //pindah lagi dari Mahasiswa_Tambah ke Mahasiswa
            val pindah:Intent = Intent(this, Mahasiswa::class.java)
            startActivity(pindah)
        }
    }
}