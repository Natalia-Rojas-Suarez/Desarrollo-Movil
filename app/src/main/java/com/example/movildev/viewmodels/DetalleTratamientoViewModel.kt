package com.example.movildev.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.movildev.model.Tratamiento
import com.example.movildev.utils.SingleLiveEvent

class DetalleTratamientoViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _tratamiento = MutableLiveData<Tratamiento>()
    val tratamiento: LiveData<Tratamiento> = _tratamiento

    private val _showHistorialDialog = SingleLiveEvent<Tratamiento>()
    val showHistorialDialog: LiveData<Tratamiento> = _showHistorialDialog

    private val _showModificarDialog = SingleLiveEvent<Tratamiento>()
    val showModificarDialog: LiveData<Tratamiento> = _showModificarDialog

    private val _navigateToHistorial = SingleLiveEvent<Tratamiento>()
    val navigateToHistorial: LiveData<Tratamiento> = _navigateToHistorial

    private val _navigateToModificar = SingleLiveEvent<Tratamiento>()
    val navigateToModificar: LiveData<Tratamiento> = _navigateToModificar

    init {
        val fecha = savedStateHandle.get<String>("fecha")
        val especialidad = savedStateHandle.get<String>("especialidad")
        val doctor = savedStateHandle.get<String>("doctor")

        if (fecha != null && especialidad != null && doctor != null) {
            _tratamiento.value = Tratamiento(fecha, especialidad, doctor)
        } else {
        }
    }

    fun onHistorialButtonClicked() {
        _tratamiento.value?.let { currentTratamiento ->
            _showHistorialDialog.setValue(currentTratamiento)
        }
    }

    fun onModificarButtonClicked() {
        _tratamiento.value?.let { currentTratamiento ->
            _showModificarDialog.setValue(currentTratamiento)
        }
    }

    fun onHistorialDialogConfirmed() {
        _tratamiento.value?.let { currentTratamiento ->
            _navigateToHistorial.setValue(currentTratamiento)
        }
    }

    fun onModificarDialogConfirmed() {
        _tratamiento.value?.let { currentTratamiento ->
            _navigateToModificar.setValue(currentTratamiento)
        }
    }
}