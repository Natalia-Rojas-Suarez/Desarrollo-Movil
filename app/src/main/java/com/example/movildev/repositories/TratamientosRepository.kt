package com.example.movildev.repositories

import com.example.movildev.model.Tratamiento

class TratamientosRepository {

    private val _tratamientos = mutableListOf(
        Tratamiento("25/04/25 - 09:45 AM", "Electroterapia", "Cathalina Cañón"),
        Tratamiento("20/04/25 - 10:45 AM", "Ejercicios terapéuticos", "Cathalina Cañón"),
        Tratamiento("25/04/25 - 09:45 AM", "Termoterapia", "Cathalina Cañón")
    )

    fun getHardcodedTratamientos(): List<Tratamiento> {
        return _tratamientos.toList()
    }

    fun addTratamiento(tratamiento: Tratamiento) {
        _tratamientos.add(tratamiento)
    }

    fun addTratamientos(tratamientos: List<Tratamiento>) {
        _tratamientos.addAll(tratamientos)
    }

    fun clearTratamientos() {
        _tratamientos.clear()
    }
}