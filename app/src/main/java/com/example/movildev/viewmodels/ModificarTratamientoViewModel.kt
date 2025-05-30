package com.example.movildev.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movildev.model.Tratamiento
import com.example.movildev.utils.SingleLiveEvent
import kotlinx.coroutines.launch

data class DialogData(
    val title: String,
    val message: String,
    val confirmText: String = "Confirmar",
    val cancelText: String = "Cancelar",
    val onConfirmAction: () -> Unit,
    val onCancelAction: (() -> Unit)? = null
)

class ModificarTratamientoViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _tratamientoAModificar = MutableLiveData<Tratamiento>()
    val tratamientoAModificar: LiveData<Tratamiento> = _tratamientoAModificar

    private val _showDialog = SingleLiveEvent<DialogData>()
    val showDialog: LiveData<DialogData> = _showDialog

    private val _navigateToMainActivity = SingleLiveEvent<Unit>()
    val navigateToMainActivity: LiveData<Unit> = _navigateToMainActivity

    init {
        val fecha = savedStateHandle.get<String>("fecha")
        val especialidad = savedStateHandle.get<String>("especialidad")
        val doctor = savedStateHandle.get<String>("doctor")

        if (fecha != null && especialidad != null && doctor != null) {
            _tratamientoAModificar.value = Tratamiento(fecha, especialidad, doctor)
        }
    }

    fun onGuardarClicked() {
        _showDialog.setValue(
            DialogData(
                title = "Confirmación",
                message = "¿Desea guardar los cambios?",
                onConfirmAction = { onConfirmSave() }
            )
        )
    }

    private fun onConfirmSave() {
        viewModelScope.launch {
            val isSuccess = true

            if (isSuccess) {
                _showDialog.setValue(
                    DialogData(
                        title = "Haz guardado exitosamente",
                        message = "¿Desea volver a Tratamientos o continuar modificando?",
                        confirmText = "Volver",
                        cancelText = "Modificar",
                        onConfirmAction = { onNavigateBackToMain() },
                        onCancelAction = { onContinueModifying() }
                    )
                )
            } else {
                _showDialog.setValue(
                    DialogData(
                        title = "Error",
                        message = "No se pudieron guardar los cambios. Intente de nuevo.",
                        confirmText = "OK",
                        cancelText = "",
                        onConfirmAction = {}
                    )
                )
            }
        }
    }

    private fun onContinueModifying() {
    }

    fun onNavigateBackToMain() {
        _navigateToMainActivity.setValue(Unit)
    }
}