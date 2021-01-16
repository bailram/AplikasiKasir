package com.bailram.aplikasikasir.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bailram.aplikasikasir.HomeActivity
import com.bailram.aplikasikasir.R
import com.bailram.aplikasikasir.databinding.ItemProdukBinding
import com.bailram.aplikasikasir.models.Produk
import com.bailram.aplikasikasir.utils.CurrencyFormat
import java.util.*
import kotlin.concurrent.timerTask

class ProdukAdapter(private val produk: List<Produk>) : RecyclerView.Adapter<ProdukAdapter.ProdukViewHolder>() {
    inner class ProdukViewHolder(val itemProdukBinding: ItemProdukBinding) : RecyclerView.ViewHolder(itemProdukBinding.root) {
    }

    var listener: ProdukRecyclerViewEditListener? = null

    override fun getItemCount() = produk.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProdukViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_produk, parent, false)
    )

    override fun onBindViewHolder(holder: ProdukAdapter.ProdukViewHolder, position: Int) {
        holder.itemProdukBinding.textViewNamaProduk.text = produk[position].nama
        holder.itemProdukBinding.textViewHarga.text = produk[position].harga?.let { CurrencyFormat.convertCurrency(it) }
        holder.itemProdukBinding.editTextQty.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Timer().schedule(timerTask {
                    listener?.onEmptyValue(holder.itemProdukBinding.editTextQty)
                }, 500)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Timer().cancel()
                Timer().schedule(timerTask {
                    if (!holder.itemProdukBinding.editTextQty.text.toString().equals("0")){
                        produk[position].qty = s.toString().toInt()
                        listener?.onValueChange(produk)
                    }
                }, 1000)
            }
        })
    }
}