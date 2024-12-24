package com.example.andorid_dz3

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material3.PageIndicatorDefaults.backgroundColor
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter

@Composable
fun ClinicsScreen() {
    Column(modifier = Modifier.padding(10.dp)) {
        TitleSection()
        FilterSection()
        ClinicsList()
    }
}

@Composable
fun RatingBar(rating: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        repeat(rating) {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                tint = Color.Yellow,
                modifier = Modifier.size(16.dp)
            )
        }
        repeat(5 - rating) {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun ClinicCard(imageUrl: String, address: String, metro: String, stars: Int) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                placeholder = rememberAsyncImagePainter(R.drawable.ic_launcher_foreground), // Заглушка
                error = rememberAsyncImagePainter(R.drawable.ic_launcher_background), // При ошибке загрузки
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 16.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = address, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = "🚇 $metro", fontSize = 14.sp, color = Color.Gray)
                RatingBar(rating = stars)
            }
        }
    }
}

@Composable
fun ClinicsList() {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(4) { index ->
            ClinicCard(
                imageUrl = when (index) {
                    0 -> "https://www.google.com/url?sa=i&url=https%3A%2F%2Fyandex.ru%2Fmaps%2Forg%2Fcosmes_clinic%2F235647776240%2F&psig=AOvVaw3TF02kwqvLMxmAH6mnaX6U&ust=1735146564184000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCNDb1ObywIoDFQAAAAAdAAAAABAE"
                    1 -> "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.aha.org%2Faha-center-health-innovation-market-scan%2F2023-05-30-retail-clinics-target-chronic-diseases&psig=AOvVaw3TF02kwqvLMxmAH6mnaX6U&ust=1735146564184000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCNDb1ObywIoDFQAAAAAdAAAAABAJ"
                    2 -> "https://www.google.com/url?sa=i&url=https%3A%2F%2Fvirtuemedical.com.sg%2Fdifferent-types-of-clinics-and-the-services-they-provide%2F&psig=AOvVaw3TF02kwqvLMxmAH6mnaX6U&ust=1735146564184000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCNDb1ObywIoDFQAAAAAdAAAAABAY"
                    else -> "https://www.google.com/url?sa=i&url=https%3A%2F%2Faiht.edu%2Fblog%2Fdifference-between-hospital-and-clinic%2F&psig=AOvVaw3TF02kwqvLMxmAH6mnaX6U&ust=1735146564184000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCNDb1ObywIoDFQAAAAAdAAAAABAg"
                },
                address = "ул. Примерная $index",
                metro = when (index) {
                    0 -> "Northfields"
                    1 -> "Southwark"
                    2 -> "Aldgate East"
                    else -> "Gunnersbury"
                },
                stars = 3 + index
            )
        }
    }
}


@Composable
fun FilterSection() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(10) { index ->
            Button(
                onClick = { /* TODO: Implement filter action */ },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(Color.LightGray)
            ) {
                Text(text = "filter$index")
            }
        }
    }
}

@Composable
fun TitleSection() {
    Text(
        text = "Клиники",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        textAlign = TextAlign.Center
    )
}
