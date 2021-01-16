package com.bailram.aplikasikasir

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bailram.aplikasikasir.adapters.DetailPenjualanAdapter
import com.bailram.aplikasikasir.databinding.ActivityDetailTransaksiBinding
import com.bailram.aplikasikasir.utils.CurrencyFormat
import com.bailram.aplikasikasir.viewmodels.DetailPenjualanViewModel
import com.bailram.aplikasikasir.viewmodels.DetailPenjualanViewModelFactory

class DetailTransaksiActivity : AppCompatActivity() {
    // inisialisasi binding
    private lateinit var binding: ActivityDetailTransaksiBinding
    private lateinit var viewModel: DetailPenjualanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_transaksi)

        val idPenjualan = intent.getStringExtra("idPenjualan")
        val totalHarga = intent.getStringExtra("totalHarga")

        val actionbar = supportActionBar
        actionbar!!.title = "DETAIL TRANSAKSI"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayShowHomeEnabled(true)

        binding.textViewTotalHarga.text = totalHarga?.let { CurrencyFormat.convertCurrency(it) }
        viewModel = ViewModelProviders.of(this, DetailPenjualanViewModelFactory(idPenjualan.toString()))
            .get(DetailPenjualanViewModel::class.java)

        viewModel.data.observe({ lifecycle }, {
            val detailPenjualanAdapter = DetailPenjualanAdapter(it)
            val recyclerView = binding.recyclerViewDetailPenjualan

            recyclerView.apply {
                this.adapter = detailPenjualanAdapter
                this.layoutManager = LinearLayoutManager(this@DetailTransaksiActivity)
            }
        })

        viewModel.response.observe({ lifecycle }, {
            if(it.isNotEmpty()) {
                Log.d("DetailTransaksiAct", it)
//                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}