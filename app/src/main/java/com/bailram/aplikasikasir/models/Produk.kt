package com.bailram.aplikasikasir.models

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json


data class Produk (
        @Json(name = "id") val id: String?,
        @Json(name = "nama") val nama: String?,
        @Json(name = "harga") val harga: String?,
        @Json(name = "qty") var qty: Int = 0
) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readInt()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(id)
                parcel.writeString(nama)
                parcel.writeString(harga)
                parcel.writeInt(qty)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<Produk> {
                override fun createFromParcel(parcel: Parcel): Produk {
                        return Produk(parcel)
                }

                override fun newArray(size: Int): Array<Produk?> {
                        return arrayOfNulls(size)
                }
        }
}