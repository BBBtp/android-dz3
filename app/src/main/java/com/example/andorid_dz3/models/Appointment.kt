package com.example.andorid_dz3.models

data class Appointment(
    val id: Int,
    val doctor: Doctor,
    val date: String, // Формат: "YYYY-MM-DD"
    val timeRange: String // Формат: "08:00 - 10:00"
)