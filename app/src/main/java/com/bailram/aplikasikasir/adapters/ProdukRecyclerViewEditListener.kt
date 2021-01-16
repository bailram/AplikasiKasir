package com.bailram.aplikasikasir.adapters

import android.view.View
import android.widget.EditText
import com.bailram.aplikasikasir.models.Produk

interface ProdukRecyclerViewEditListener {
    fun onValueChange(produk: List<Produk>)
    fun onEmptyValue(editText: EditText)
}