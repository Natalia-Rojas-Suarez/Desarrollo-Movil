package com.example.movildev.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movildev.model.Tratamiento
import com.example.movildev.repositories.TratamientosRepository
import kotlinx.coroutines.launch

class TratamientosViewModel(private val repository: TratamientosRepository) : ViewModel() {

    private val _tratamientos = MutableLiveData<List<Tratamiento>>()
    val tratamientos: LiveData<List<Tratamiento>> = _tratamientos

    private val _navigateToDetail = MutableLiveData<Tratamiento>()
    val navigateToDetail: LiveData<Tratamiento> = _navigateToDetail

    init {
        loadTratamientos()
    }

    private fun loadTratamientos() {
        viewModelScope.launch {
            _tratamientos.value = repository.getHardcodedTratamientos()
        }
    }

    fun onAccederClicked(tratamiento: Tratamiento) {
        _navigateToDetail.value = tratamiento
    }
}