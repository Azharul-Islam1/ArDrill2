package com.example.ardrill

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun DrillSelect(
    drills: List<Drill>,
    onStartARClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF8DAF8F),
                        Color(0xFF858283),
                        Color(0xFFBDC4EC)
                    )
                )
            )
    ) {
        var Index by remember { mutableStateOf(0) }
        var expanded by remember { mutableStateOf(false) }
        val Drill = drills[Index]

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.technicalsupport),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.Black),
                    modifier = Modifier.size(55.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Select a Drill",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                OutlinedTextField(
                    value = Drill.name,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Choose Drill") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    drills.forEachIndexed { index, drill ->
                        DropdownMenuItem(
                            text = { Text(drill.name) },
                            onClick = {
                                Index = index
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { if (Index > 0) Index-- },
                    enabled = Index > 0
                ) {
                    Text("Prev")
                }

                Button(
                    onClick = { if (Index < drills.lastIndex) Index++ },
                    enabled = Index < drills.lastIndex
                ) {
                    Text("Next")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            AnimatedContent(
                targetState = Drill,
                transitionSpec = {
                    fadeIn(tween(300)) with fadeOut(tween(200))
                },
                label = "DrillContentAnimation"
            ) { drill ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(6.dp, shape = RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F4F8))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = drill.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF333333)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = drill.description,
                            fontSize = 16.sp,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Start
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Tips:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = Color(0xFF444444)
                        )

                        drill.tips.forEach { tip ->
                            Text(
                                text = "• $tip",
                                fontSize = 15.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = { onStartARClick(Drill.name) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFA6C8F)),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(55.dp)
                    .fillMaxWidth(0.6f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Start AR Drill", fontSize = 18.sp, color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DrillSelectshow() {
    val sampleDrills = listOf(
        Drill(
            name = "Drill 1",
            description = "Place a solid cube to simulate volume-based object in real space.",
            tips = listOf("Tap on a flat surface to place the cube", "Rotate with two fingers")
        ),
        Drill(
            name = "Drill 2",
            description = "Place a rectangle to simulate shelf or table.",
            tips = listOf("Tap to place", "Use zoom to scale", "Rotate to align")
        ),
        Drill(
            name = "Drill 3",
            description = "Place a circular object like a ball or pipe.",
            tips = listOf("Tap center for symmetry", "Use multi-touch to rotate")
        )
    )
    DrillSelect(drills = sampleDrills, onStartARClick = {})
}
