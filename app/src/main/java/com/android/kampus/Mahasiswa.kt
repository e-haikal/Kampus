package com.android.kampus

import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.ByteArrayInputStream

class Mahasiswa : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mahasiswa)
        val rv_mahasiswa:RecyclerView = findViewById(R.id.rv_mahasiswa)
        val txt_nama_user:TextView = findViewById(R.id.txt_nama_user)

        //dapatkan nama user yang login dari session
        val tiket:SharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
        val nama_user:String = tiket.getString("nama_user", null).toString()
        txt_nama_user.text = nama_user

        val id_mahasiswa:MutableList<String> = mutableListOf()
        val nama:MutableList<String> = mutableListOf()
        val nim:MutableList<String> = mutableListOf()
        val foto:MutableList<Bitmap> = mutableListOf()

        //panggil database kampus
        val dbkampus:SQLiteDatabase = openOrCreateDatabase("kampus", MODE_PRIVATE, null)

        //gali data mahasiswa
        val gali_mahasiswa = dbkampus.rawQuery("SELECT * FROM mahasiswa", null)

        while (gali_mahasiswa.moveToNext())
        {
            try {
                val bis = ByteArrayInputStream(gali_mahasiswa.getBlob(3))
                val gambarbitmap:Bitmap = BitmapFactory.decodeStream(bis)
                foto.add(gambarbitmap)
            } catch (e:Exception) {
                val gambarbitmap:Bitmap = BitmapFactory.decodeResource(this.resources,R.drawable.noimage)
                foto.add(gambarbitmap)
            }

            id_mahasiswa.add(gali_mahasiswa.getString(0))
            nama.add(gali_mahasiswa.getString(1))
            nim.add(gali_mahasiswa.getString(2))
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

        //bila btn_logout ditekan
        val btn_logout:Button = findViewById(R.id.btn_logout)
        btn_logout.setOnClickListener {
            //menghapus session/tiket user
            val edittiket = tiket.edit()
            edittiket.clear()
            edittiket.commit()

            //pindah halaman
            val pindah:Intent = Intent(this, Login::class.java)
            startActivity(pindah)
            finish()
        }
    }
}