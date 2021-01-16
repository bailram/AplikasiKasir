package com.bailram.aplikasikasir.models

data class Penjualan(
    val id: String,
    val tanggal: String,
    val harga: String,
    val created_at: String,
    val created_by: String,
    val updated_at: String?,
    val updated_by: String?,
    val deleted_at: String?,
    val deleted_by: String?
)