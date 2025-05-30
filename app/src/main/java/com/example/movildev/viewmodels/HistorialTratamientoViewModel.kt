package com.example.movildev.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.movildev.utils.SingleLiveEvent

class HistorialTratamientoViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _historialInfo = MutableLiveData<String>()
    val historialInfo: LiveData<String> = _historialInfo

    private val _navigateToTratamientos = SingleLiveEvent<Unit>()
    val navigateToTratamientos: LiveData<Unit> = _navigateToTratamientos

    init {
        val fecha = savedStateHandle.get<String>("fecha")
        val especialidad = savedStateHandle.get<String>("especialidad")
        val doctor = savedStateHandle.get<String>("doctor")

        if (fecha != null && especialidad != null && doctor != null) {
            _historialInfo.value = "Detalles completos del historial de ${especialidad} del ${fecha} con el Dr./Dra. ${doctor}."
        } else {
            _historialInfo.value = "Detalles completos del historial del tratamiento (sin información específica pasada)."
        }
    }

    fun onVolverTratamientosClicked() {
        _navigateToTratamientos.setValue(Unit)
    }
}