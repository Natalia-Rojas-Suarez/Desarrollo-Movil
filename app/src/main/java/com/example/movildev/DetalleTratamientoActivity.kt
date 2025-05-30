package com.example.movildev

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import com.example.movildev.viewmodels.DetalleTratamientoViewModel

class DetalleTratamientoActivity : BaseActivity() {

    // Se inyecta el ViewModel usando viewModels(), que automáticamente maneja SavedStateHandle
    private val viewModel: DetalleTratamientoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_tratamiento)

        val tvFecha = findViewById<TextView>(R.id.tvFechaDetalle)
        val tvEspecialidad = findViewById<TextView>(R.id.tvEspecialidadDetalle)
        val tvDoctor = findViewById<TextView>(R.id.tvDoctorDetalle)

        // Observa los cambios en el objeto Tratamiento dentro del ViewModel
        // El ViewModel ya obtiene estos datos de SavedStateHandle al inicializarse
        viewModel.tratamiento.observe(this) { tratamiento ->
            tvFecha.text = tratamiento.fecha
            tvEspecialidad.text = tratamiento.especialidad
            tvDoctor.text = tratamiento.doctor
        }

        val btnHistorial = findViewById<Button>(R.id.btnHistorial)
        btnHistorial?.setOnClickListener {
            // Se elimina la llamada a onHistorialButtonClicked() del ViewModel
            // y la lógica de diálogo. Navegamos directamente.
            viewModel.tratamiento.value?.let { currentTratamiento ->
                val intent = Intent(this, HistorialTratamientoActivity::class.java).apply {
                    putExtra("fecha", currentTratamiento.fecha)
                    putExtra("especialidad", currentTratamiento.especialidad)
                    putExtra("doctor", currentTratamiento.doctor)
                }
                startActivity(intent)
            }
        }

        val btnModificar = findViewById<Button>(R.id.btnModificar)
        btnModificar?.setOnClickListener {
            // Se elimina la llamada a onModificarButtonClicked() del ViewModel
            // y la lógica de diálogo. Navegamos directamente.
            viewModel.tratamiento.value?.let { currentTratamiento ->
                val intent = Intent(this, ModificarTratamientoActivity::class.java).apply {
                    putExtra("fecha", currentTratamiento.fecha)
                    putExtra("especialidad", currentTratamiento.especialidad)
                    putExtra("doctor", currentTratamiento.doctor)
                }
                startActivity(intent)
            }
        }

        // --- Se eliminan todos los Observables relacionados con diálogos y navegación desde el ViewModel ---
        // viewModel.showHistorialDialog.observe(...)
        // viewModel.showModificarDialog.observe(...)
        // viewModel.navigateToHistorial.observe(...) // Ya no es necesario si la navegación es directa desde el botón
        // viewModel.navigateToModificar.observe(...) // Ya no es necesario si la navegación es directa desde el botón
    }

    // Si la función showCustomDialog no se utiliza en ninguna otra parte de esta clase, puedes eliminarla.
    // private fun showCustomDialog(...) { ... }
}