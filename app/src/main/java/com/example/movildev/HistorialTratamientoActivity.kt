package com.example.movildev

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels

import com.example.movildev.viewmodels.HistorialTratamientoViewModel

class HistorialTratamientoActivity : BaseActivity() {

    private val viewModel: HistorialTratamientoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_tratamiento)


      //  viewModel.historialInfo.observe(this) { info ->
        //    tvHistorialInfo.text = info
       // }

        val btnVolverTratamientos = findViewById<Button>(R.id.btnVolverTratamientos)
        btnVolverTratamientos.setOnClickListener {
            viewModel.onVolverTratamientosClicked()
        }

        viewModel.navigateToTratamientos.observe(this) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}