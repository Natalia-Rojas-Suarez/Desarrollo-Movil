package com.example.movildev

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.activity.viewModels
import com.example.movildev.viewmodels.ModificarTratamientoViewModel

class ModificarTratamientoActivity : BaseActivity() {

    private val viewModel: ModificarTratamientoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_tratamiento)

        val btnGuardar = findViewById<Button>(R.id.btnGuardarDescripcion)
        btnGuardar.setOnClickListener {
            viewModel.onGuardarClicked()
        }

        viewModel.showDialog.observe(this) { dialogData ->
            showCustomDialog(
                title = dialogData.title,
                message = dialogData.message,
                confirmText = dialogData.confirmText,
                cancelText = dialogData.cancelText,
                onConfirm = dialogData.onConfirmAction,
                onCancel = dialogData.onCancelAction
            )
        }

        viewModel.navigateToMainActivity.observe(this) {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    private fun showCustomDialog(
        title: String,
        message: String,
        confirmText: String,
        cancelText: String,
        onConfirm: () -> Unit,
        onCancel: (() -> Unit)?
    ) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_tratamiento, null)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val tvTitle = dialogView.findViewById<TextView>(R.id.dialog_title)
        val tvMessage = dialogView.findViewById<TextView>(R.id.dialog_message)
        val btnCancel = dialogView.findViewById<Button>(R.id.btn_cancel)
        val btnConfirm = dialogView.findViewById<Button>(R.id.btn_confirm)

        tvTitle.text = title
        tvMessage.text = message
        btnCancel.text = cancelText
        btnConfirm.text = confirmText

        btnCancel.setOnClickListener {
            dialog.dismiss()
            onCancel?.invoke()
        }

        btnConfirm.setOnClickListener {
            dialog.dismiss()
            onConfirm()
        }

        dialog.show()
    }
}