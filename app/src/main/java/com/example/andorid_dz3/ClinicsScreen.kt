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

@Composable
fun ClinicsScreen() {
    Column(modifier = Modifier.padding(it)) {
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
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = null,
                tint = Color.Yellow,
                modifier = Modifier.size(16.dp)
            )
        }
        repeat(5 - rating) {
            Icon(
                painter = painterResource(id = R.drawable.ic_star_outline),
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun ClinicCard(imageRes: Int, address: String, metro: String, stars: Int) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
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
                imageRes = when (index) {
                    0 -> R.drawable.ic_gun_1
                    1 -> R.drawable.ic_gun_2
                    2 -> R.drawable.ic_gun_3
                    else -> R.drawable.ic_gun_case
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
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
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
