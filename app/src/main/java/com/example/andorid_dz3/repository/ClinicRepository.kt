// repository/ClinicRepository.kt
package com.example.andorid_dz3.repository

import android.util.Log
import com.example.andorid_dz3.R
import com.example.andorid_dz3.models.Appointment
import com.example.andorid_dz3.models.Clinic
import com.example.andorid_dz3.models.Doctor
import com.example.andorid_dz3.models.Service
import kotlinx.coroutines.delay

class ClinicRepository {

    suspend fun fetchDoctors(): List<Doctor> {
        delay(2000) // Эмуляция задержки
        return listOf(
            Doctor(1, "Иванов Иван", "Терапевт", R.drawable.doctor1),
            Doctor(2, "Петрова Мария", "Хирург", R.drawable.doctor2),
            Doctor(3, "Сидоров Алексей", "Стоматолог", R.drawable.doctor3)
        )
    }

    suspend fun fetchServices(): List<Service> {
        delay(2000)
        return listOf(
            Service(1, "Кардиолог", "Полная диагностика состояния сердца.", R.drawable.heart ),
            Service(2, "Травматолог", "Лечение сломанных костей", R.drawable.broke),
            Service(3, "Стоматолог", "Профессиональное удаление зубов любой сложности.", R.drawable.tooth),
            Service(3, "Кардиолог", "Полная диагностика состояния сердца.", R.drawable.heart ),
            Service(4, "Травматолог", "Лечение сломанных костей", R.drawable.broke),
            Service(5, "Стоматолог", "Профессиональное удаление зубов любой сложности.", R.drawable.tooth)
        )
    }

    suspend fun fetchClinics(): List<Clinic> {
        delay(2000)
        return listOf(
            Clinic(1, "Клиника Здоровье", "Москва", "Арбатская", R.drawable.clinic1),
            Clinic(2, "Медицинский Центр Лидер", "Москва","Новая", R.drawable.clinic2),
            Clinic(3, "Клиника Улыбка", "Москва","Коломенская", R.drawable.clinic3)
        )
    }

    suspend fun fetchAppointments(): List<Appointment> {
        delay(2000)
        return listOf(
            Appointment(1, fetchDoctors()[0], "25.12.2024", "08:00 - 10:00"),
            Appointment(2, fetchDoctors()[1], "12.12.2024", "10:00 - 12:00"),
            Appointment(3, fetchDoctors()[2], "13.12.2024", "12:00 - 14:00")
        )
    }

    suspend fun getAppointmentById(appointmentId: Int): Appointment? {
        val appointments = fetchAppointments()
        delay(100)
        Log.d("ClinicRepository", "Fetched appointments: $appointments")
        return appointments.find { it.id == appointmentId }
    }
}
