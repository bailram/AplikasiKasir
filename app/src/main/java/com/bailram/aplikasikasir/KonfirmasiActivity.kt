package com.bailram.aplikasikasir

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bailram.aplikasikasir.adapters.KonfirmasiAdapter
import com.bailram.aplikasikasir.databinding.ActivityKonfirmasiBinding
import com.bailram.aplikasikasir.models.Produk
import com.bailram.aplikasikasir.utils.CurrencyFormat
import com.bailram.aplikasikasir.viewmodels.KonfirmasiViewModel
import com.bailram.aplikasikasir.viewmodels.KonfirmasiViewModelFactory
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timerTask

class KonfirmasiActivity : AppCompatActivity() {
    // inisialisasi binding
    private lateinit var binding: ActivityKonfirmasiBinding
    private lateinit var viewModel: KonfirmasiViewModel
    private lateinit var listIdProduk: List<String>
    private lateinit var listQty: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_konfirmasi)

        val actionbar = supportActionBar
        actionbar!!.title = "DETAIL TRANSAKSI"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayShowHomeEnabled(true)

        val listProduk = intent.getParcelableArrayListExtra<Produk>("listProduk")
        val totalHargaProduk = intent.getIntExtra("totalHargaProduk",0)
        binding.textViewTotalHarga.text = CurrencyFormat.convertCurrency(totalHargaProduk.toString())

        val konfirmasiAdapter = listProduk?.let { KonfirmasiAdapter(it) }
        val recyclerView = binding.recyclerViewDetailPenjualan

        recyclerView.apply {
            this.adapter = konfirmasiAdapter
            this.layoutManager = LinearLayoutManager(this@KonfirmasiActivity)
        }

        binding.buttonSelesai.setOnClickListener {
            // initialized list
            listIdProduk = ArrayList<String>()
            listQty = ArrayList<String>()

            if (listProduk != null) {
                for (item in listProduk) {
                    (listIdProduk!! as ArrayList).add(item.id.toString())
                    (listQty!! as ArrayList).add(item.qty.toString())
                }
                viewModel = ViewModelProviders.of(this, KonfirmasiViewModelFactory(listIdProduk, listQty))
                        .get(KonfirmasiViewModel::class.java)

                viewModel.data.observe({ lifecycle }, {
                    Log.d("KonfirmasiActiviy", it.code +" : "+it.msg)
                    if(it.code.equals("200")){
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    }
                })

                viewModel.response.observe({ lifecycle }, {
                    if(it.isNotEmpty()){
                        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}