package com.android.kampus

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.ByteArrayOutputStream

class Mahasiswa_Tambah : AppCompatActivity() {
    var urlgambar:Uri? = null
    var bitmapgambar:Bitmap? = null
    var iv_upload:ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mahasiswa_tambah)
        val edt_nim:EditText = findViewById(R.id.edt_nim)
        val edt_nama:EditText = findViewById(R.id.edt_nama)
        val btn_simpan:Button = findViewById(R.id.btn_simpan)

        iv_upload = findViewById<ImageView>(R.id.iv_upload)

        //iv_upload diklik, buka galeri
        iv_upload?.setOnClickListener {
            val bukagaleri:Intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            pilih_gambar.launch(bukagaleri)
        }


        //btn_simpan ditekan
        btn_simpan.setOnClickListener{
            //dapatkan isi dari edt_nama dan edt_nim
            val isi_nim:String = edt_nim.text.toString()
            val isi_nama:String = edt_nama.text.toString()

            //dapatkan gambar yang dipilih lalu dijadikan bytearray
            val bos = ByteArrayOutputStream()
            bitmapgambar?.compress(Bitmap.CompressFormat.JPEG, 100, bos)
            val bytearraygambar = bos.toByteArray()

            //query simpan ke database
            val dbkampus:SQLiteDatabase = openOrCreateDatabase("kampus", MODE_PRIVATE, null)

            val sql = "INSERT INTO mahasiswa (nim_mahasiswa, nama_mahasiswa, foto_mahasiswa) VALUES (?,?,?)"
            val statement = dbkampus.compileStatement(sql)
            statement.clearBindings()
            statement.bindString(1, isi_nim)
            statement.bindString(2, isi_nama)
            statement.bindBlob(3, bytearraygambar)
            statement.executeInsert()

            //pindah lagi dari Mahasiswa_Tambah ke Mahasiswa
            val pindah:Intent = Intent(this, Mahasiswa::class.java)
            startActivity(pindah)
        }
    }
    val pilih_gambar = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode==Activity.RESULT_OK){
            val gambardiperoleh = it.data

            if (gambardiperoleh!=null){
                //dapatkan urlnya gambar
                urlgambar = gambardiperoleh.data

                //konversi ke bitmap
                bitmapgambar = MediaStore.Images.Media.getBitmap(contentResolver, urlgambar)
                iv_upload?.setImageBitmap(bitmapgambar)
            }
        }
    }
}