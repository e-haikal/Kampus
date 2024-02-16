package com.android.kampus

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Mahasiswa_Hapus : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.mahasiswa_hapus)

        //tangkap dulu id_mahasiswa_terpilih, karena dia yang mau kita hapus
        val id_mahasiswa_terpilih:String = intent.getStringExtra("id_mahasiswa_terpilih").toString()

        //connect ke db
        val dbkampus: SQLiteDatabase = openOrCreateDatabase("kampus", MODE_PRIVATE, null)

        //query hapus data
        val query = dbkampus.rawQuery("DELETE  FROM mahasiswa WHERE id_mahasiswa='$id_mahasiswa_terpilih'", null)
        query.moveToNext()

        //pindah ke halaman utama
        val pindah:Intent = Intent(this, Mahasiswa::class.java)
        startActivity(pindah)
    }
}