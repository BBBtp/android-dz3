package com.example.andorid_dz3.homescreen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.andorid_dz3.Montserrat
import com.example.andorid_dz3.R
import com.example.andorid_dz3.models.Appointment
import com.example.andorid_dz3.models.Clinic
import com.example.andorid_dz3.models.Doctor
import com.example.andorid_dz3.models.Service
import com.example.andorid_dz3.repository.ClinicRepository
import java.time.format.TextStyle

@Composable
fun ShimmerItemCard() {
    Box(
        modifier = Modifier
            .size(width = 240.dp, height = 120.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Gray) // Background color for the shimmer effect
    ) {
        ShimmerEffect(modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun ShimmerItemDoctor() {
    Box(
        modifier = Modifier
            .size(width = 380.dp, height = 70.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Gray) // Background color for the shimmer effect
    ) {
        ShimmerEffect(modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun ShimmerItemService() {
    Box(
        modifier = Modifier
            .size(width = 120.dp, height = 30.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Gray) // Background color for the shimmer effect
    ) {
        ShimmerEffect(modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun ShimmerEffect(modifier: Modifier = Modifier) {
    val shimmerTransition = rememberInfiniteTransition(label = "")
    val offset by shimmerTransition.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing)
        )
    )
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val shimmerWidth = width / 2
        drawRect(
            color = Color.Gray.copy(alpha = 0.3f),
            size = size
        )

        drawRect(
            color = Color.LightGray.copy(alpha = 0.5f),
            topLeft = Offset(offset * width, 0f),
            size = Size(shimmerWidth, height)
        )
    }
}

@Composable
fun ShimmerLoadingView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShimmerItemCard()
    }
}

@Composable
fun ShimmerLoadingViewDoctor() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShimmerItemDoctor()
    }
}

@Composable
fun ShimmerLoadingViewService() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShimmerItemService()
    }
}

@Composable
fun HomeScreen(
    navController: NavController, viewModel: HomeScreenViewModel = viewModel(
        factory = HomeScreenViewModelFactory(repository = ClinicRepository())
    )
) {
    val doctors = viewModel.doctors.collectAsState().value
    val clinics = viewModel.clinics.collectAsState().value
    val appointments = viewModel.appointments.collectAsState().value
    val services = viewModel.services.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(26.dp)
            .verticalScroll(rememberScrollState())
    ) {
        UserInfoBlock()
        Spacer(modifier = Modifier.height(26.dp))
        Text(
            text = "Ваши записи",
            style = androidx.compose.ui.text.TextStyle(
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = colorResource(id = R.color.light_text_primary)
            ),
            modifier = Modifier.padding(start = 8.dp)
        )
        RecordCardsBlock(appointments, isLoading, navController)
        Spacer(modifier = Modifier.height(26.dp))
        Text(
            text = "Выбери доктора",
            style = androidx.compose.ui.text.TextStyle(
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = colorResource(id = R.color.light_text_primary)
            ),
            modifier = Modifier.padding(start = 8.dp)
        )
        DoctorSelectionBlock(doctors, services, isLoading)
        Spacer(modifier = Modifier.height(26.dp))
        Text(
            text = "Ваши поликлиники",
            style = androidx.compose.ui.text.TextStyle(
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = colorResource(id = R.color.light_text_primary)
            ),
            modifier = Modifier.padding(start = 8.dp)
        )
        ClinicsBlock(clinics, isLoading)
    }
}

@Composable
fun UserInfoBlock() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Иван Иванов", style = androidx.compose.ui.text.TextStyle(
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = colorResource(id = R.color.light_text_primary)
                )
            )

            Row() {
                Text(
                    text = "Москва", style = androidx.compose.ui.text.TextStyle(
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = colorResource(id = R.color.light_text_primary)
                    )
                )
                Text(text = " ", style = MaterialTheme.typography.bodySmall)
                Text(
                    text = "35 лет", style = androidx.compose.ui.text.TextStyle(
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = colorResource(id = R.color.light_text_primary)
                    )
                )
            }
        }
        Icon(
            imageVector = Icons.Default.Face,
            contentDescription = "Avatar",
            tint = colorResource(id = R.color.light_text_primary),
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
fun RecordCardsBlock(
    appointments: List<Appointment>,
    isLoading: Boolean,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (isLoading) {
            repeat(3) { // Показываем 3 карточки с шиммером во время загрузки
                ShimmerLoadingView()
            }
        } else {
            appointments?.forEach { appointment ->
                RecordCard(appointment, navController)
            }
        }
    }
}


@Composable
fun RecordCard(appointment: Appointment, navController: NavController) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.light_bg_secondary)
        ),
        modifier = Modifier
            .size(width = 240.dp, height = 120.dp)
            .clickable {
                navController.navigate("aboutAppointment/${appointment.id}")
            },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberAsyncImagePainter(appointment.doctor.image),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = appointment.doctor.fullName,
                        style = androidx.compose.ui.text.TextStyle(
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = colorResource(id = R.color.light_text_primary)
                        )
                    )
                    Text(
                        text = appointment.doctor.specialty,
                        style = androidx.compose.ui.text.TextStyle(
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Light,
                            fontSize = 12.sp,
                            color = colorResource(id = R.color.light_text_primary)
                        )
                    )
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = appointment.date, style = androidx.compose.ui.text.TextStyle(
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.light_text_primary)
                    )
                )
                Text(
                    text = appointment.timeRange, style = androidx.compose.ui.text.TextStyle(
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        color = colorResource(id = R.color.light_text_primary)
                    )
                )
            }
        }
    }
}


@Composable
fun DoctorSelectionBlock(doctors: List<Doctor>, services: List<Service>, isLoading: Boolean) {
    Column {

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (isLoading) {
                repeat(3) { // Показываем 3 карточки с шиммером во время загрузки
                    ShimmerLoadingViewService()
                }
            } else {
                services.map { service ->
                    SpecialityChip(service.name, service.icon)
                }
            }

        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (isLoading) {
                repeat(3) {
                    ShimmerLoadingViewDoctor()
                }
            } else {
                doctors.map { doctor ->
                    DoctorCard(doctor)
                }
            }
        }
    }
}


@Composable
fun SpecialityChip(name: String, icon: Int) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = colorResource(id = R.color.light_bg_secondary),
        modifier = Modifier.padding(4.dp)
    ) {
        ShimmerEffect(modifier = Modifier.fillMaxSize())
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = name, style = androidx.compose.ui.text.TextStyle(
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.light_text_primary)
                )
            )
        }
    }
}

@Composable
fun DoctorCard(doctor: Doctor) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.light_bg_secondary)
        ),

        modifier = Modifier
            .size(width = 380.dp, height = 70.dp),
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(doctor.image),
                contentDescription = "Avatar",

                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )
            Spacer(Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = doctor.fullName, style = androidx.compose.ui.text.TextStyle(
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.light_text_primary)
                    )
                )
                Text(
                    text = doctor.specialty, style = androidx.compose.ui.text.TextStyle(
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = colorResource(id = R.color.light_text_primary)
                    )
                )
            }
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Default.MailOutline, contentDescription = "Message", tint = colorResource(id = R.color.light_text_primary))
            }
        }
    }
}


@Composable
fun ClinicsBlock(clinics: List<Clinic>, isLoading: Boolean) {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (isLoading) {
            repeat(3) {
                ShimmerLoadingView()
            }
        } else {
            clinics.map { clinic ->
                ClinicCard(clinic)
            }
        }
    }
}

@Composable
fun ClinicCard(clinic: Clinic) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.light_bg_secondary)
        ),
        modifier = Modifier
            .size(width = 290.dp, height = 152.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = clinic.image,
                    contentDescription = "Clinic Image",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = clinic.name,
                        style = androidx.compose.ui.text.TextStyle(
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = colorResource(id = R.color.light_text_primary)
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = clinic.city,
                        style = androidx.compose.ui.text.TextStyle(
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Light,
                            fontSize = 12.sp,
                            color = colorResource(id = R.color.light_text_primary)
                        ),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.width(10.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.metro),
                            contentDescription = "Metro",
                            tint = Color.Red,
                            modifier = Modifier.size(17.dp)
                        )
                        Spacer(Modifier.width(2.dp))
                        Text(
                            text = clinic.metro,
                            style = androidx.compose.ui.text.TextStyle(
                                fontFamily = Montserrat,
                                fontWeight = FontWeight.Light,
                                fontSize = 12.sp,
                                color = colorResource(id = R.color.light_text_primary)
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween, // Распределяет элементы по краям
                modifier = Modifier.fillMaxWidth()
            ) {
                Row {
                    repeat(3) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = colorResource(id = R.color.light_text_primary)
                        )
                    }
                }
                IconButton(onClick = { /* действие при клике */ }) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Info",
                        tint = colorResource(id = R.color.light_text_primary)
                    )
                }
            }
        }
    }
}

