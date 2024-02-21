package com.android.kampus

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import java.io.ByteArrayOutputStream
import java.io.InputStream

class Mahasiswa_Ubah : AppCompatActivity() {
    // Deklarasi variabel yang diperlukan
    var urlgambar: Uri? = null
    var bitmapgambar: Bitmap? = null
    var iv_upload: ImageView? = null
    var id_mahasiswa_terpilih: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mahasiswa_ubah)

        // Mendapatkan id mahasiswa yang akan diubah dari intent
        id_mahasiswa_terpilih = intent.getStringExtra("id_mahasiswa_terpilih")

        // Membuka database kampus
        val dbkampus: SQLiteDatabase = openOrCreateDatabase("kampus", MODE_PRIVATE, null)

        // Mengambil data mahasiswa yang akan diubah dari database
        val ambil: Cursor = dbkampus.rawQuery("SELECT * FROM mahasiswa WHERE id_mahasiswa='$id_mahasiswa_terpilih'", null)

        // Menampilkan data mahasiswa yang akan diubah pada form
        if (ambil.moveToFirst()) {
            val isi_nim: String = ambil.getString(1)
            val isi_nama: String = ambil.getString(2)
            val isi_foto: ByteArray? = ambil.getBlob(3)

            val edt_nim: EditText = findViewById(R.id.edt_nim)
            val edt_nama: EditText = findViewById(R.id.edt_nama)
            val btn_simpan: Button = findViewById(R.id.btn_simpan)
            iv_upload = findViewById(R.id.iv_upload)

            // Menampilkan data awal pada form
            edt_nim.setText(isi_nim)
            edt_nama.setText(isi_nama)

            // Menampilkan foto mahasiswa jika ada
            if (isi_foto != null) {
                val gambarbitmap: Bitmap = BitmapFactory.decodeByteArray(isi_foto, 0, isi_foto.size)
                iv_upload?.setImageBitmap(gambarbitmap)
            }

            // Menangani aksi klik pada ImageView untuk memilih gambar dari galeri
            iv_upload?.setOnClickListener {
                val bukagaleri: Intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                pilih_gambar.launch(bukagaleri)
            }

            // Menangani aksi klik pada tombol simpan
            btn_simpan.setOnClickListener {
                val nim_baru: String = edt_nim.text.toString()
                val nama_baru: String = edt_nama.text.toString()

                val bos = ByteArrayOutputStream()
                if (bitmapgambar != null) {
                    bitmapgambar?.compress(Bitmap.CompressFormat.JPEG, 100, bos)
                } else if (isi_foto != null) {
                    bos.write(isi_foto)
                }
                val bytearraygambar = bos.toByteArray()

                // Memperbarui data mahasiswa dalam database
                val sql = "UPDATE mahasiswa SET nim_mahasiswa=?, nama_mahasiswa=?, foto_mahasiswa=? WHERE id_mahasiswa=?"
                val statement = dbkampus.compileStatement(sql)
                statement.clearBindings()
                statement.bindString(1, nim_baru)
                statement.bindString(2, nama_baru)
                statement.bindBlob(3, bytearraygambar)
                statement.bindString(4, id_mahasiswa_terpilih)
                statement.executeUpdateDelete()

                // Kembali ke halaman Mahasiswa setelah perubahan disimpan
                val pindah: Intent = Intent(this, Mahasiswa::class.java)
                startActivity(pindah)
            }
        } else {
            // Menampilkan pesan kesalahan jika terjadi masalah dalam mengambil data mahasiswa
            Log.d("Mahasiswa", "Maaf, terjadi kesalahan")
        }
    }

    // Fungsi untuk menangani pemilihan gambar dari galeri menggunakan Activity Result API
    val pilih_gambar = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val gambardiperoleh = result.data
            if (gambardiperoleh != null) {
                urlgambar = gambardiperoleh.data
                try {
                    val inputStream: InputStream? = contentResolver.openInputStream(urlgambar!!)
                    bitmapgambar = BitmapFactory.decodeStream(inputStream)
                    iv_upload?.setImageBitmap(bitmapgambar)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
