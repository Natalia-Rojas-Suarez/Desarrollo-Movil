package com.example.movildev.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SingleLiveEvent<T> : MutableLiveData<T>() {
    private var pending = false

    override fun observe(owner: androidx.lifecycle.LifecycleOwner, observer: androidx.lifecycle.Observer<in T>) {
        super.observe(owner) { t ->
            if (pending) {
                pending = false
                observer.onChanged(t)
            }
        }
    }

    override fun setValue(value: T?) {
        pending = true
        super.setValue(value)
    }

    fun call() {
        value = null
    }
}


class MainNavigationViewModel : ViewModel() {

    private val _navigateTo = SingleLiveEvent<Int>()
    val navigateTo: LiveData<Int> = _navigateTo

    private val _currentNavSelection = MutableLiveData<Int>()
    val currentNavSelection: LiveData<Int> = _currentNavSelection

    fun onNavigationItemSelected(itemId: Int) {
        _currentNavSelection.value = itemId
        _navigateTo.value = itemId
    }

}