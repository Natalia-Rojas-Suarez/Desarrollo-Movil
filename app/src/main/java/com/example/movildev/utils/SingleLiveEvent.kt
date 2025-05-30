// app/src/main/java/com/example/movildev/utils/SingleLiveEvent.kt
package com.example.movildev.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.LifecycleOwner

class SingleLiveEvent<T> : MutableLiveData<T>() {

    private var pending = false

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        // Observa como de costumbre, pero sólo dispara si está pendiente.
        super.observe(owner) { t ->
            if (pending) {
                pending = false
                observer.onChanged(t)
            }
        }
    }

    // El método setValue ya se encarga de marcarlo como pendiente y notificar.
    // Sobreescribimos setValue para incluir la lógica 'pending'.
    override fun setValue(value: T?) {
        pending = true
        super.setValue(value)
    }

    // NOTA: ¡HE ELIMINADO EL MÉTODO 'fun call(value: T)' de aquí!
    // Y HE ELIMINADO EL MÉTODO 'fun call()' sin argumentos si existía.
    // Solo usamos setValue para emitir eventos.
}