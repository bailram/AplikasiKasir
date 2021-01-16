package com.bailram.aplikasikasir

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bailram.aplikasikasir.adapters.PenjualanAdapter
import com.bailram.aplikasikasir.adapters.RecyclerViewClickListener
import com.bailram.aplikasikasir.databinding.ActivityRiwayatTransaksiBinding
import com.bailram.aplikasikasir.viewmodels.PenjualanViewModel

class RiwayatTransaksiActivity : AppCompatActivity(), RecyclerViewClickListener {
    // inisialisasi binding
    private lateinit var binding: ActivityRiwayatTransaksiBinding
    private lateinit var viewModel: PenjualanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_riwayat_transaksi)
        val actionbar = supportActionBar
        actionbar!!.title = "RIWAYAT TRANSAKSI"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayShowHomeEnabled(true)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_riwayat_transaksi)
        viewModel = ViewModelProviders.of(this).get(PenjualanViewModel::class.java)

        viewModel.data.observe({ lifecycle }, {
            val penjualanAdapter = PenjualanAdapter(it)
            val recyclerView = binding.recyclerViewPenjualan

            // set listener
            penjualanAdapter.listener = this

            recyclerView.apply {
                this.adapter = penjualanAdapter
                this.layoutManager = LinearLayoutManager(this@RiwayatTransaksiActivity)
            }
        })

        viewModel.response.observe({ lifecycle }, {
            if (it.isNotEmpty()) {
                Log.d("RiwayatTransaksiAct", it)
//                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onItemClicked(view: View, idPenjualan: String, totalHarga: String) {
        val intent = Intent(this, DetailTransaksiActivity::class.java)
        intent.putExtra("idPenjualan", idPenjualan)
        intent.putExtra("totalHarga", totalHarga)
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}