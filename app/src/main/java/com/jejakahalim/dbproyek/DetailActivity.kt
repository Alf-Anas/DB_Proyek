package com.jejakahalim.dbproyek

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.jejakahalim.dbproyek.model.DataBase
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val DETAIL_PROYEK = "detail_proyek"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val dataBase = intent.getParcelableExtra<DataBase>(DETAIL_PROYEK) as DataBase
        tvNamaProyek.text = dataBase.nama_proyek
        tvUnitOperasi.text = dataBase.unit_operasi
        tvTahun.text = dataBase.tahun.toString()
        edtPTPJNama.setText(dataBase.ptpj_nama)
        edtPTPJAlamat.setText(dataBase.ptpj_alamat)
        edtKontrakNo.setText(dataBase.kontrak_no)
        edtKontrakNilai.setText("Rp." + dataBase.kontrak_nilai)
        edtPekerjaanMulai.setText(dataBase.pekerjaan_mulai)
        edtPekerjaanSelesai.setText(dataBase.pekerjaan_selesai)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}