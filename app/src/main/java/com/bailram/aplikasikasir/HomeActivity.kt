package com.bailram.aplikasikasir

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bailram.aplikasikasir.adapters.ProdukAdapter
import com.bailram.aplikasikasir.adapters.ProdukRecyclerViewEditListener
import com.bailram.aplikasikasir.databinding.ActivityHomeBinding
import com.bailram.aplikasikasir.models.Produk
import com.bailram.aplikasikasir.utils.CurrencyFormat
import com.bailram.aplikasikasir.viewmodels.ProdukViewModel
import retrofit2.Call
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timerTask

class HomeActivity : AppCompatActivity(), ProdukRecyclerViewEditListener {
    private var totalHarga : Int = 0
    private var listProduk: List<Produk> = ArrayList<Produk>()
    private var buttonState: Boolean = false
    // inisialisasi binding
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: ProdukViewModel
    private var backPressedtoExitOnce: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        val actionbar = supportActionBar
        actionbar!!.title = "APLIKASI KASIR"

        viewModel = ViewModelProviders.of(this).get(ProdukViewModel::class.java)

        viewModel.data.observe({ lifecycle }, {
            val produkAdapter = ProdukAdapter(it)
            val recyclerView = binding.recyclerViewProduk

            produkAdapter.listener = this

            recyclerView.apply {
                this.adapter = produkAdapter
                this.layoutManager = LinearLayoutManager(this@HomeActivity)
            }
        })

        viewModel.response.observe({ lifecycle }, {
            if(it.isNotEmpty()){
                Log.d("HomeActivity", it)
            }
        })
//        val params = HashMap<String, String>()
//        params.put("id_produk[]","4")
//        params.put("qty[]","2")
//        params.put("id_produk[]","3")
//        params.put("qty[]","5")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if(id == R.id.menu_riwayat_transaksi){
            startActivity(Intent(this, RiwayatTransaksiActivity::class.java))
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onValueChange(produk: List<Produk>) {
            totalHarga = 0
            (listProduk!! as ArrayList).clear()
            runOnUiThread {
                for (item in produk) {
                    if(item.qty > 0){
                        totalHarga = totalHarga + ((item.harga?.toInt() ?: 0) *item.qty)
                        (listProduk!! as ArrayList).add(item)
                    }
                }

                if(totalHarga>0){
                    binding.linearLayoutTotal.setBackgroundResource(R.drawable.rounded_primary)
                    buttonState = true
                } else {
                    binding.linearLayoutTotal.setBackgroundResource(R.drawable.rounded_grey)
                    buttonState = false
                }

                binding.textViewTotalHargaProduk.text = CurrencyFormat.convertCurrency(totalHarga.toString())
                Log.d("HomeActivity", "sizeList: "+listProduk.size)

                if(buttonState){
                    binding.linearLayoutTotal.setOnClickListener{
                        intent = Intent(this, KonfirmasiActivity::class.java)
                        intent.putExtra("totalHargaProduk", totalHarga)
                        intent.putParcelableArrayListExtra("listProduk", (listProduk!! as ArrayList))
                        startActivity(intent)
                    }
                }
            }
    }

    override fun onEmptyValue(editText: EditText) {
        if (editText.text.toString().equals("")){
            runOnUiThread {
                editText.setText("0")
            }
        }
    }

    override fun onBackPressed() {
        if (backPressedtoExitOnce) {
            super.onBackPressed()
        } else {
            backPressedtoExitOnce = true
            Toast.makeText(this, "Tekan lagi untuk keluar", Toast.LENGTH_SHORT).show()
            Timer().schedule(timerTask {
                backPressedtoExitOnce = false
            }, 2000)
//            Handler().postDelayed(Runnable { backPressedtoExitOnce = false },2000)
        }
    }
}