package com.example.andorid_dz3.models

data class Doctor(
    val id: Int,
    val name: String,
    val specialization: String,
    val photoUrl: String,
    val services: List<Service>
)
