package com.example.andorid_dz3

import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.andorid_dz3.models.Doctor
import kotlinx.coroutines.launch
import androidx.compose.ui.text.font.FontWeight
import com.example.andorid_dz3.R
import androidx.compose.ui.unit.sp
import com.example.andorid_dz3.repository.ClinicRepository


@Composable
fun DoctorScreen() {
    val coroutineScope = rememberCoroutineScope()
    val doctorList = remember { mutableStateOf<List<Doctor>?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    val selectedDoctorId = remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            doctorList.value = ClinicRepository().fetchDoctors()
            isLoading.value = false
        }
    }

    if (selectedDoctorId.value != null) {
        ServicesListScreen(doctorId = selectedDoctorId.value!!)
    } else {

        if (isLoading.value) {
            LoadingState()
        } else if (doctorList.value.isNullOrEmpty()) {
            EmptyState()
        } else {
            DoctorList(
                Doctors = doctorList.value ?: emptyList(),
                onDoctorClick = { doctorId ->
                    selectedDoctorId.value = doctorId
                }
            )
        }
    }
}


@Composable
fun DoctorList(Doctors: List<Doctor>, onDoctorClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Врачи",
            fontFamily = Montserrat,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 8.dp),
            color = Color(0xFF2F6690)
        )
        Text(
            text = "Наши доктора всегда готовы проконсультировать вас и помочь даже в самых сложных ситуациях.\n" +
                    "Выберите специальность врача и ознакомьтесь со списком лучших специалистов.",
            fontSize = 16.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(bottom = 16.dp),
            color = Color(0xFF2F6690)
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(Doctors) { doctor ->
                DoctorListItem(doctor = doctor, onClick = onDoctorClick)
            }
        }
    }
}



@Composable
fun DoctorListItem(doctor: Doctor, onClick: (Int) -> Unit) {
    Button(
        onClick = { onClick(doctor.id) },
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(41.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEAF4F4))
    ) {
        Text(
            text = doctor.specialization,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Normal,
            fontSize = 22.sp,
            color = Color(0xFF2F6690)
        )
    }
}

