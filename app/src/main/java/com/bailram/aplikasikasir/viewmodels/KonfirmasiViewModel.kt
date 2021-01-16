package com.bailram.aplikasikasir.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bailram.aplikasikasir.ApiKasirApp
import com.bailram.aplikasikasir.models.SavePenjualanResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class KonfirmasiViewModel(val idProduk: List<String>, val qty: List<String>) : ViewModel() {
    private val _data = MutableLiveData<SavePenjualanResponse>()
    val data: LiveData<SavePenjualanResponse>
        get() = _data

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    private val job = Job()
    private val uiScope = CoroutineScope(job + Dispatchers.Main)

    init {
        _response.value = ""
        initData(idProduk, qty)
    }

    fun initData(idProduk: List<String>, qty: List<String>) {
        uiScope.launch {
            try {
                val result = ApiKasirApp.retrofitService.savePenjualan(idProduk, qty)

                if (result.code.equals("200")){
                    _data.value = result
                    _response.value = "Berhasil menambahkan data!"
                } else {
                    _response.value = "Gagal menambahkan data!"
                }
            } catch (t: Throwable) {
                _response.value = "Tidak ada koneksi internet!"
                Log.d("KonfirmasiViewModel", t.message.toString())
            }
        }
    }
}