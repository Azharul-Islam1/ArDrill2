package com.example.ardrill

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        val drills = listOf(
            Drill("Drill 1", "Place a solid cube to simulate volume-based object in real space", listOf("Tap on the flat surface to place the cube","Rotate it using two fingers","Ideal for box-shaped product previews")),
            Drill("Drill 2", "Place a rectangular block to mimic shelves, tables, or walls", listOf(
                    "Use longer axis for wall or shelf simulation", "Tap again to adjust length/width","Zoom out to view full scale")),
            Drill("Drill 3", "Place a circular object like ball, column, or pipe base", listOf("Center the tap to get symmetry", "Use for pipes, balls, or round structures","Tap again to create multiple copies"))
        )

        setContent {
            val context = LocalContext.current

            DrillSelect(
                drills = drills,
                onStartARClick = { selectedDrillName ->
                    val intent = Intent(context, ARActivity::class.java)
                    intent.putExtra("drill_name", selectedDrillName)
                    context.startActivity(intent)
                }
            )
        }
    }
}
