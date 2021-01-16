package com.bailram.aplikasikasir

import com.bailram.aplikasikasir.models.DetailPenjualan
import com.bailram.aplikasikasir.models.Penjualan
import com.bailram.aplikasikasir.models.Produk
import com.bailram.aplikasikasir.models.SavePenjualanResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

const val BASE_URL = "http://demo73.energeek.co.id/kasir-app/public/api/"

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

// API Data
interface ApiService {
    @GET("produk")
    suspend fun showListProduk(): List<Produk>

    @Headers(value = ["x-key: bd0b3ae6651538fac2515baafc9326c5",
                "Content-Type: application/json"])
    @GET("penjualan/")
    suspend fun showListPenjualan(): List<Penjualan>

    @Headers(value = ["x-key: bd0b3ae6651538fac2515baafc9326c5",
        "Content-Type: application/json"])
    @GET("penjualan/detail")
    suspend fun showDetailPenjualan(@Query("id") idParam: String): List<DetailPenjualan>

    @Headers(value = ["x-key: bd0b3ae6651538fac2515baafc9326c5",
        "Content-Type: application/json"])
    @POST("penjualan/save")
    suspend fun savePenjualan(@Query("id_produk[]") idProduk: List<String>,
                              @Query("qty[]") qty: List<String>): SavePenjualanResponse
}

object ApiKasirApp{
    val retrofitService = retrofit.create(ApiService::class.java)
}