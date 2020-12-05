package com.jejakahalim.dbproyek.model

import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.jejakahalim.dbproyek.DetailActivity
import com.jejakahalim.dbproyek.R
import java.util.*
import kotlin.collections.ArrayList


class DataBaseAdapter(private val listDataBase: ArrayList<DataBase>) :
    RecyclerView.Adapter<DataBaseAdapter.ListViewHolder>(), Filterable {

    var dataBaseFilterList = ArrayList<DataBase>()

    init {
        dataBaseFilterList = listDataBase
    }

    val filterName = arrayOf(
        "filter_nama_proyek",
        "filter_ptpj_nama",
        "filter_ptpj_alamat",
        "filter_tahun",
        "filter_unit_operasi"
    )
    lateinit var prefs: SharedPreferences

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_data, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val dataBase = dataBaseFilterList[position]
        holder.tvNo.text = dataBase.no.toString()
        holder.tvNamaProyek.text = dataBase.nama_proyek
        holder.tvNama.text = dataBase.ptpj_nama
        holder.itemView.setOnClickListener {
            val setObjectIntent = Intent(it.context, DetailActivity::class.java)
            setObjectIntent.putExtra(DetailActivity.DETAIL_PROYEK, dataBase)
            it.context.startActivity(setObjectIntent)
        }
        prefs = PreferenceManager.getDefaultSharedPreferences(holder.itemView.context)
    }

    override fun getItemCount(): Int {
        return dataBaseFilterList.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNo: TextView = itemView.findViewById(R.id.tv_item_no)
        var tvNamaProyek: TextView = itemView.findViewById(R.id.tv_item_nama_proyek)
        var tvNama: TextView = itemView.findViewById(R.id.tv_item_nama)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                val filterDivisi =
                    prefs.getString("FILTER_DIVISI", "semua")?.replace(" ", "")?.replace("-", "")
                        ?.toLowerCase(Locale.ROOT)

                dataBaseFilterList = if (charSearch.isEmpty() && filterDivisi == "semua") {
                    listDataBase
                } else {
                    val resultList = ArrayList<DataBase>()
                    for (row in listDataBase) {
                        val checkedItem = booleanArrayOf(
                            prefs.getBoolean(filterName[0], true),
                            prefs.getBoolean(filterName[1], true),
                            prefs.getBoolean(filterName[2], false),
                            prefs.getBoolean(filterName[3], false),
                            prefs.getBoolean(filterName[4], false)
                        )

                        val divisi = row.unit_operasi.toLowerCase(Locale.ROOT).replace(" ", "")
                            .replace("-", "")
                        var divisiOK = true

                        if (filterDivisi != "semua") {
                            divisiOK = filterDivisi?.equals(divisi) ?: false
                        }

                        if (checkedItem[0] && row.nama_proyek.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT)) && divisiOK
                        ) {
                            resultList.add(row)
                        } else if (checkedItem[1] && row.ptpj_nama.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT)) && divisiOK
                        ) {
                            resultList.add(row)
                        } else if (checkedItem[2] && row.ptpj_alamat.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT)) && divisiOK
                        ) {
                            resultList.add(row)
                        } else if (checkedItem[3] && row.tahun.toString().toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT)) && divisiOK
                        ) {
                            resultList.add(row)
                        } else if (checkedItem[4] && row.unit_operasi.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT)) && divisiOK
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = dataBaseFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dataBaseFilterList = results?.values as ArrayList<DataBase>
                notifyDataSetChanged()
            }

        }
    }

}