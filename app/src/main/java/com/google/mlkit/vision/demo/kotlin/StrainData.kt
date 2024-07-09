package com.google.mlkit.vision.demo.kotlin

import android.util.Log

data class StrainInfo(
    val name: String,
    val type: String,
    val thc: String,
    val cbd: String,
    val rating: Float,
    val description: String
)

object StrainDatabase {
    private val strains = listOf(
        StrainInfo("Sour Diesel", "Hybrid", "19%", "0%", 4.3f, "A popular sativa-dominant hybrid..."),
        StrainInfo("Blue Dream", "Hybrid", "18%", "0.1%", 4.4f, "A balanced hybrid strain..."),
        StrainInfo("OG Kush", "Hybrid", "20%", "0%", 4.5f, "A potent hybrid strain..."),
        // Add more strains as needed
    )

    init {
        Log.d("StrainDatabase", "Initializing with ${strains.size} strains")
        strains.forEach { Log.d("StrainDatabase", "Strain: ${it.name}") }
    }

    fun findStrain(name: String): StrainInfo? {
        Log.d("StrainDatabase", "Searching for strain: $name")
        val result = strains.find { it.name.equals(name, ignoreCase = true) }
        Log.d("StrainDatabase", "Result: ${result?.name ?: "Not found"}")
        return result
    }
}