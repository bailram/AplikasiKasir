package com.bailram.aplikasikasir.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DetailPenjualanViewModelFactory(private val id: String): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailPenjualanViewModel(id) as T
    }
}