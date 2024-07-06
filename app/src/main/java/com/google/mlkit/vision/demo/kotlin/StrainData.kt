package com.google.mlkit.vision.demo.kotlin

// This file contains the data structure for strain information and a mock database
// of strains. It provides a method to find strains by name, which will be used
// by the StrainRecognitionProcessor to match recognized text with strain data.

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

    fun findStrain(name: String): StrainInfo? {
        return strains.find { it.name.equals(name, ignoreCase = true) }
    }
}
