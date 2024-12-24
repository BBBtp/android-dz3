// repository/ClinicRepository.kt
package com.example.andorid_dz3.repository

import com.example.andorid_dz3.models.Appointment
import com.example.andorid_dz3.models.Clinic
import com.example.andorid_dz3.models.Doctor
import com.example.andorid_dz3.models.Service
import kotlinx.coroutines.delay

class ClinicRepository {

    suspend fun fetchDoctors(): List<Doctor> {
        delay(2000) // Эмуляция задержки
        return listOf(
            Doctor(1, "Иванов Иван Иванович", "Терапевт", "https://example.com/photo1.jpg"),
            Doctor(2, "Петрова Мария Сергеевна", "Хирург", "https://example.com/photo2.jpg"),
            Doctor(3, "Сидоров Алексей Дмитриевич", "Стоматолог", "https://example.com/photo3.jpg")
        )
    }

    suspend fun fetchServices(): List<Service> {
        delay(2000)
        return listOf(
            Service(1, "Консультация терапевта", "Полная диагностика состояния здоровья.", "https://example.com/service1.jpg"),
            Service(2, "Удаление зуба", "Профессиональное удаление зубов любой сложности.", "https://example.com/service2.jpg"),
            Service(3, "МРТ", "Магнитно-резонансная томография с высокой точностью.", "https://example.com/service3.jpg")
        )
    }

    suspend fun fetchClinics(): List<Clinic> {
        delay(2000)
        return listOf(
            Clinic(1, "Клиника Здоровье", "Современная многопрофильная клиника.", "https://example.com/clinic1.jpg"),
            Clinic(2, "Медицинский Центр Лидер", "Полный спектр медицинских услуг.", "https://example.com/clinic2.jpg"),
            Clinic(3, "Клиника Улыбка", "Стоматология и челюстно-лицевая хирургия.", "https://example.com/clinic3.jpg")
        )
    }

    suspend fun fetchAppointments(): List<Appointment> {
        delay(2000)
        return listOf(
            Appointment(1, fetchDoctors()[0], "2024-12-25", "08:00 - 10:00"),
            Appointment(2, fetchDoctors()[1], "2024-12-26", "10:00 - 12:00"),
            Appointment(3, fetchDoctors()[2], "2024-12-27", "12:00 - 14:00")
        )
    }
}
