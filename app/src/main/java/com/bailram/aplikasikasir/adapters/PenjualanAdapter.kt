package com.bailram.aplikasikasir.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bailram.aplikasikasir.R
import com.bailram.aplikasikasir.databinding.ItemPenjualanBinding
import com.bailram.aplikasikasir.models.Penjualan
import com.bailram.aplikasikasir.utils.CurrencyFormat

class PenjualanAdapter(private val penjualan: List<Penjualan>) : RecyclerView.Adapter<PenjualanAdapter.PenjualanViewHolder>() {

    var listener: RecyclerViewClickListener? = null

    inner class PenjualanViewHolder(val itemPenjualanBinding: ItemPenjualanBinding) : RecyclerView.ViewHolder(itemPenjualanBinding.root) {
    }

    override fun getItemCount() = penjualan.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PenjualanViewHolder(
        DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_penjualan, parent, false)
    )

    override fun onBindViewHolder(holder: PenjualanViewHolder, position: Int) {
        holder.itemPenjualanBinding.textViewTanggal.text = penjualan[position].tanggal
        holder.itemPenjualanBinding.textViewHarga.text = CurrencyFormat.convertCurrency(penjualan[position].harga)
        holder.itemPenjualanBinding.listPenjualan.setOnClickListener {
            listener?.onItemClicked(it, penjualan[position].id, penjualan[position].harga)
        }
    }
}