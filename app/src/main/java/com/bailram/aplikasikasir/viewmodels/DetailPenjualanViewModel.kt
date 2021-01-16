package com.bailram.aplikasikasir.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bailram.aplikasikasir.ApiKasirApp
import com.bailram.aplikasikasir.models.DetailPenjualan
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailPenjualanViewModel(val id: String) : ViewModel(){

    private val _data = MutableLiveData<List<DetailPenjualan>>()
    val data : LiveData<List<DetailPenjualan>>
        get() = _data

    private val _response = MutableLiveData<String>()
    val response : LiveData<String>
        get() = _response

    private val job = Job()
    private val uiScope = CoroutineScope(job + Dispatchers.Main)

    init {
        _response.value = ""
        initData(id)
    }

    fun initData(idPenjualan: String) {
        uiScope.launch {
            try {
                val result = ApiKasirApp.retrofitService.showDetailPenjualan(idPenjualan)

                if (result.isNotEmpty()){
                    _data.value = result
                    _response.value = "Berhasil fetch data penjualan!"
                } else {
                    _response.value = "Data penjualan kosong!"
                }
            } catch (t: Throwable) {
                _response.value = "Tidak ada koneksi internet!"
                Log.d("PenjualanViewModel", t.message.toString())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}