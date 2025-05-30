package com.example.movildev

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.movildev.viewmodels.MainNavigationViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

open class BaseActivity : AppCompatActivity() {

    protected val mainNavigationViewModel: MainNavigationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setContentView(layoutResID: Int) {
        val fullView = LayoutInflater.from(this).inflate(R.layout.activity_base, null)
        val contentFrame = fullView.findViewById<FrameLayout>(R.id.content_frame)
        LayoutInflater.from(this).inflate(layoutResID, contentFrame, true)
        super.setContentView(fullView)

        val backIcon = fullView.findViewById<ImageView>(R.id.back_icon)
        backIcon.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val bottomNav = fullView.findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNav.setOnItemSelectedListener { item ->
            mainNavigationViewModel.onNavigationItemSelected(item.itemId)
            true
        }

        mainNavigationViewModel.navigateTo.observe(this) { itemId ->
            when (itemId) {
                R.id.nav_inicio -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)
                }
                R.id.nav_historial -> {
                    startActivity(Intent(this, HistorialTratamientoActivity::class.java))
                }
            }
        }
    }
}