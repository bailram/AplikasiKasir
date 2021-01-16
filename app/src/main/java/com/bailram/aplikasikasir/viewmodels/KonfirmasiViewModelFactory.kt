package com.bailram.aplikasikasir.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bailram.aplikasikasir.models.Produk
import java.util.ArrayList

class KonfirmasiViewModelFactory(private val idProduk: List<String>, private val qty: List<String>) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return KonfirmasiViewModel(idProduk, qty) as T
    }
}