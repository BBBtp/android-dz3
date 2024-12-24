package com.example.andorid_dz3.homescreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.andorid_dz3.models.Appointment
import com.example.andorid_dz3.models.Clinic
import com.example.andorid_dz3.models.Doctor
import com.example.andorid_dz3.models.Service
import com.example.andorid_dz3.repository.ClinicRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val repository: ClinicRepository) : ViewModel() {

    private val _doctors = MutableStateFlow<List<Doctor>>(emptyList())
    val doctors: StateFlow<List<Doctor>> = _doctors

    private val _services = MutableStateFlow<List<Service>>(emptyList())
    val services: StateFlow<List<Service>> = _services

    private val _clinics = MutableStateFlow<List<Clinic>>(emptyList())
    val clinics: StateFlow<List<Clinic>> = _clinics

    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments

    // Добавляем состояние загрузки
    private val _isLoading = MutableStateFlow(true) // Изначально данные загружаются
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _appointment = MutableStateFlow<Appointment?>(null) // Переменная для текущей записи
    val appointment: StateFlow<Appointment?> = _appointment
    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _doctors.value = repository.fetchDoctors()
            _services.value = repository.fetchServices()
            _clinics.value = repository.fetchClinics()
            _appointments.value = repository.fetchAppointments()
            _isLoading.value = false
        }
    }

    suspend fun getAppointmentById(appointmentId: Int) {
        _appointment.value = repository.getAppointmentById(appointmentId)
        _isLoading.value = false
    }
}

class HomeScreenViewModelFactory(
    private val repository: ClinicRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) {
            HomeScreenViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

