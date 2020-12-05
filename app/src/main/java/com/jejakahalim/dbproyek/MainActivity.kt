package com.jejakahalim.dbproyek

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jejakahalim.dbproyek.model.DataBase
import com.jejakahalim.dbproyek.model.DataBaseAdapter
import com.jejakahalim.dbproyek.utils.getJsonDataFromAsset
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    lateinit var dataBaseAdapter: DataBaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMain.setHasFixedSize(true)

        imgFilter.setOnClickListener {
            showFilterPopup()
        }

        var strQuery = ""
        spnFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                val selected: String = spnFilter.selectedItem.toString()
                val sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(selectedItemView.context).edit()
                sharedPreferencesEditor.putString("FILTER_DIVISI", selected).apply()
                dataBaseAdapter.filter.filter(strQuery)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }

        svMain.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                strQuery = newText.toString()
                dataBaseAdapter.filter.filter(newText)
                return false
            }
        })

        setRecyclerViewData()
    }

    private fun showFilterPopup() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("Cari texs di ...")
        val items = arrayOf(
            "Nama Proyek",
            "Nama Pemberi Tugas/Pengguna Jasa",
            "Alamat/Telpon Pemberi Tugas/Pengguna Jasa",
            "Tahun",
            "Unit Operasi"
        )

        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val filterName = arrayOf(
            "filter_nama_proyek",
            "filter_ptpj_nama",
            "filter_ptpj_alamat",
            "filter_tahun",
            "filter_unit_operasi"
        )
        val checkedItem = booleanArrayOf(
            prefs.getBoolean(filterName[0], true),
            prefs.getBoolean(filterName[1], true),
            prefs.getBoolean(filterName[2], false),
            prefs.getBoolean(filterName[3], false),
            prefs.getBoolean(filterName[4], false)
        )
        alertDialog.setMultiChoiceItems(
            items, checkedItem
        ) { _, which, isChecked ->
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
            val editor = sharedPreferences.edit()
            when (which) {
                0 -> if (isChecked)
                    editor.putBoolean(filterName[0], true).apply()
                else
                    editor.putBoolean(filterName[0], false).apply()
                1 -> if (isChecked)
                    editor.putBoolean(filterName[1], true).apply()
                else
                    editor.putBoolean(filterName[1], false).apply()
                2 -> if (isChecked)
                    editor.putBoolean(filterName[2], true).apply()
                else
                    editor.putBoolean(filterName[2], false).apply()
                3 -> if (isChecked)
                    editor.putBoolean(filterName[3], true).apply()
                else
                    editor.putBoolean(filterName[3], false).apply()
                4 -> if (isChecked)
                    editor.putBoolean(filterName[4], true).apply()
                else
                    editor.putBoolean(filterName[4], false).apply()
            }
        }
        val alert: AlertDialog = alertDialog.create()
        alert.show()
    }

    private fun setRecyclerViewData() {
        val jsonFileString = getJsonDataFromAsset(this, "db_proyek.json")
        val arrayJSON = JSONArray(jsonFileString)
        val list = arrayListOf<DataBase>()
        for (i in 0 until arrayJSON.length()) {
            val dataJson: JSONObject = arrayJSON.getJSONObject(i)
            val dataBase = DataBase()
            dataBase.id = i
            dataBase.no = if (dataJson.isNull("No")) 0 else dataJson.getInt("No")
            dataBase.nama_proyek = dataJson.getString("Nama_Proyek")
            dataBase.ptpj_nama = dataJson.getString("PTPJ_Nama")
            dataBase.ptpj_alamat = dataJson.getString("PTPJ_Alamat")
            dataBase.kontrak_no = dataJson.getString("Kontrak_No")
            dataBase.kontrak_nilai = dataJson.getString("Kontrak_Nilai")
            dataBase.pekerjaan_mulai = dataJson.getString("Pekerjaan_Mulai")
            dataBase.pekerjaan_selesai = dataJson.getString("Pekerjaan_Selesai")
            dataBase.tahun = if (dataJson.isNull("Tahun")) 0 else dataJson.getInt("Tahun")
            dataBase.unit_operasi = dataJson.getString("Unit_Operasi")
            list.add(dataBase)
        }

        val listData: ArrayList<DataBase> = arrayListOf()
        listData.addAll(list)
        rvMain.layoutManager = LinearLayoutManager(this)
        dataBaseAdapter = DataBaseAdapter(listData)
        rvMain.adapter = dataBaseAdapter
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, R.string.toast_double_back_close, Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }
}