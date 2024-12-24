package com.example.andorid_dz3

import androidx.compose.runtime.Composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.andorid_dz3.models.Service
import com.example.andorid_dz3.repository.ClinicRepository
import kotlinx.coroutines.launch
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.andorid_dz3.R
import androidx.compose.ui.unit.sp

val Montserrat = FontFamily(
    Font(R.font.montserrat_medium, FontWeight.Normal),
    Font(R.font.montserrat_bold, FontWeight.Bold)
)
val Robot = FontFamily(
    Font(R.font.roboto_bold, FontWeight.Bold),
)


@Composable
fun ServicesScreen() {
    val coroutineScope = rememberCoroutineScope()
    val serviceList = remember { mutableStateOf<List<Service>?>(null) }
    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            serviceList.value = ClinicRepository().fetchServices()
            isLoading.value = false
        }
    }

    if (isLoading.value) {
        LoadingState()
    } else if (serviceList.value.isNullOrEmpty()) {
        EmptyState()
    } else {
        ServiceList(services = serviceList.value ?: emptyList())
    }
}

@Composable
fun ServicesListScreen(doctorId: Int) {
    val coroutineScope = rememberCoroutineScope()
    val serviceList = remember { mutableStateOf<List<Service>?>(null) }
    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(doctorId) {
        coroutineScope.launch {
            serviceList.value = ClinicRepository().getServicesByDoctorId(doctorId)
            isLoading.value = false
        }
    }

    if (isLoading.value) {
        LoadingState()
    } else if (serviceList.value.isNullOrEmpty()) {
        EmptyState()
    } else {
        ServiceList(services = serviceList.value ?: emptyList())
    }
}




@Composable
fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun EmptyState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Список услуг пуст", style = MaterialTheme.typography.headlineSmall)
    }
}



@Composable
fun ServiceList(services: List<Service>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Услуги",
            fontFamily = Montserrat,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 8.dp),
            color = Color(0xFF2F6690)
        )
        Text(
            text = "Наша частная клиника предлагает широкий спектр медицинских услуг для диагностики, лечения и профилактики различных заболеваний. Мы используем современное оборудование и методы диагностики, чтобы точно определить состояние здоровья пациентов.",
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
            items(services) { service ->
                ServiceListItem(service)
            }
        }
    }
}

@Composable
fun ServiceListItem(service: Service) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(41.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEAF4F4))
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        )
        {
            Text(
                text = service.name,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                fontSize = 22.sp,
                color = Color(0xFF2F6690)
            )
        }
    }
}

