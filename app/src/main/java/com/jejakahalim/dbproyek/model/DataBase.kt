package com.jejakahalim.dbproyek.model

import android.os.Parcel
import android.os.Parcelable

data class DataBase(
    var id: Int = 0,
    var no: Int = 0,
    var nama_proyek: String = "",
    var ptpj_nama: String = "",
    var ptpj_alamat: String = "",
    var kontrak_no: String = "",
    var kontrak_nilai: String = "",
    var pekerjaan_mulai: String = "",
    var pekerjaan_selesai: String = "",
    var tahun: Int = 0,
    var unit_operasi: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(no)
        parcel.writeString(nama_proyek)
        parcel.writeString(ptpj_nama)
        parcel.writeString(ptpj_alamat)
        parcel.writeString(kontrak_no)
        parcel.writeString(kontrak_nilai)
        parcel.writeString(pekerjaan_mulai)
        parcel.writeString(pekerjaan_selesai)
        parcel.writeInt(tahun)
        parcel.writeString(unit_operasi)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataBase> {
        override fun createFromParcel(parcel: Parcel): DataBase {
            return DataBase(parcel)
        }

        override fun newArray(size: Int): Array<DataBase?> {
            return arrayOfNulls(size)
        }
    }
}