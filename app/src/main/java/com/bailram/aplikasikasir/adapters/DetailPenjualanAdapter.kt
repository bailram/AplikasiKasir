package com.bailram.aplikasikasir.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bailram.aplikasikasir.R
import com.bailram.aplikasikasir.databinding.ItemDetailPenjualanBinding
import com.bailram.aplikasikasir.models.DetailPenjualan
import com.bailram.aplikasikasir.utils.CurrencyFormat

class DetailPenjualanAdapter(private val detailPenjualan: List<DetailPenjualan>) : RecyclerView.Adapter<DetailPenjualanAdapter.DetailPenjualanViewHolder>() {
    inner class DetailPenjualanViewHolder(val itemDetailPenjualanBinding: ItemDetailPenjualanBinding) : RecyclerView.ViewHolder(itemDetailPenjualanBinding.root) {
    }

    override fun getItemCount() = detailPenjualan.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DetailPenjualanViewHolder(
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_detail_penjualan, parent, false)
    )

    override fun onBindViewHolder(holder: DetailPenjualanViewHolder, position: Int) {
        holder.itemDetailPenjualanBinding.textViewNamaProduk.text = detailPenjualan[position].nama
        val deksripsi = detailPenjualan[position].qty +
                " * " + CurrencyFormat.convertCurrency(detailPenjualan[position].harga)
        holder.itemDetailPenjualanBinding.textViewDetailProduk.text = deksripsi
        val total = detailPenjualan[position].qty.toInt() * detailPenjualan[position].harga.toInt()
        holder.itemDetailPenjualanBinding.textViewHarga.text = CurrencyFormat.convertCurrency(total.toString())
    }
}