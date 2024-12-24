package com.example.andorid_dz3.homescreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.andorid_dz3.Montserrat
import com.example.andorid_dz3.R
import com.example.andorid_dz3.models.Appointment
import com.example.andorid_dz3.repository.ClinicRepository

@Composable
fun AboutAppointmentScreen(
    appointmentId: Int,
    navController: NavController,
    viewModel: HomeScreenViewModel = viewModel(
        factory = HomeScreenViewModelFactory(repository = ClinicRepository())
    )
) {
    LaunchedEffect(appointmentId) {
        Log.d("AboutAppointmentScreen", "appointmentId: $appointmentId")
        viewModel.getAppointmentById(appointmentId)
    }

    val appointment = viewModel.appointment.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value

    if (isLoading) {
        // Индикатор загрузки
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (appointment != null) {
        // Данные загружены
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Информация о записи",
                style = androidx.compose.ui.text.TextStyle(
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = colorResource(id = R.color.light_text_primary)
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Информация о докторе
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberAsyncImagePainter(appointment.doctor.image),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = appointment.doctor.fullName, style = androidx.compose.ui.text.TextStyle(
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.light_text_primary)
                    ),)
                    Text(text = appointment.doctor.specialty, style = androidx.compose.ui.text.TextStyle(
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.light_text_primary)
                    ),)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Дата и время записи
            Text(text = "Дата: ${appointment.date}", style = androidx.compose.ui.text.TextStyle(
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = colorResource(id = R.color.light_text_primary)
            ),)
            Text(text = "Время: ${appointment.timeRange}", style = androidx.compose.ui.text.TextStyle(
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = colorResource(id = R.color.light_text_primary)
            ),)

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Комментарий: Нет комментария", style = androidx.compose.ui.text.TextStyle(
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp,
                color = colorResource(id = R.color.light_text_primary)
            ),)

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Назад",
                    style = androidx.compose.ui.text.TextStyle(
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.light_bg_primary)
                ),
                )
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Запись не найдена",
                style = androidx.compose.ui.text.TextStyle(
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = colorResource(id = R.color.light_text_primary)
                ),
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}
