package com.example.movildev

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button
import androidx.activity.viewModels
import com.example.movildev.viewmodels.TratamientosViewModel
import com.example.movildev.repositories.TratamientosRepository

class MainActivity : BaseActivity() {

    private val tratamientosViewModel: TratamientosViewModel by viewModels {
        object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(TratamientosViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return TratamientosViewModel(TratamientosRepository()) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tratamientos)

        val contenedorTarjetas = findViewById<LinearLayout>(R.id.layout_interno)
        val inflater = LayoutInflater.from(this)

        tratamientosViewModel.tratamientos.observe(this) { tratamientos ->
            contenedorTarjetas.removeAllViews()
            tratamientos.forEach { tratamiento ->
                val tarjeta = inflater.inflate(R.layout.card_tratamiento, contenedorTarjetas, false)

                val tvFecha = tarjeta.findViewById<TextView>(R.id.tvFecha)
                val tvEspecialidad = tarjeta.findViewById<TextView>(R.id.tvEspecialidad)
                val tvDoctor = tarjeta.findViewById<TextView>(R.id.tvDoctor)
                val btnAcceder = tarjeta.findViewById<Button>(R.id.btnAcceder)

                tvFecha.text = tratamiento.fecha
                tvEspecialidad.text = tratamiento.especialidad
                tvDoctor.text = tratamiento.doctor

                btnAcceder.setOnClickListener {
                    val intent = Intent(this, DetalleTratamientoActivity::class.java).apply {
                        putExtra("fecha", tratamiento.fecha)
                        putExtra("especialidad", tratamiento.especialidad)
                        putExtra("doctor", tratamiento.doctor)
                    }
                    startActivity(intent)
                }
                contenedorTarjetas.addView(tarjeta)
            }
        }

    }
}