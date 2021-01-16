package com.bailram.aplikasikasir.adapters

import android.view.View

interface RecyclerViewClickListener {
    fun onItemClicked(view: View, idPenjualan: String, totalHarga: String)
}
