package com.bailram.aplikasikasir.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bailram.aplikasikasir.R
import com.bailram.aplikasikasir.databinding.ItemDetailPenjualanBinding
import com.bailram.aplikasikasir.models.Produk
import com.bailram.aplikasikasir.utils.CurrencyFormat

class KonfirmasiAdapter(private val dataPenjualan: List<Produk>) : RecyclerView.Adapter<KonfirmasiAdapter.KonfirmasiViewHolder>() {
    inner class KonfirmasiViewHolder(val itemDetailPenjualanBinding: ItemDetailPenjualanBinding) : RecyclerView.ViewHolder(itemDetailPenjualanBinding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = KonfirmasiViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_detail_penjualan, parent, false)
    )

    override fun getItemCount() = dataPenjualan.size

    override fun onBindViewHolder(holder: KonfirmasiAdapter.KonfirmasiViewHolder, position: Int) {
        holder.itemDetailPenjualanBinding.textViewNamaProduk.text = dataPenjualan[position].nama
        val deskripsi = dataPenjualan[position].qty.toString() + " * " + dataPenjualan[position].harga?.let { CurrencyFormat.convertCurrency(it) }
        holder.itemDetailPenjualanBinding.textViewDetailProduk.text = deskripsi
        val total = dataPenjualan[position].qty * (dataPenjualan[position].harga?.toInt() ?: 0)
        holder.itemDetailPenjualanBinding.textViewHarga.text = CurrencyFormat.convertCurrency(total.toString())
    }
}