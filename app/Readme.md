## Documento Explicativo del Código de la Aplicación Android


### 1. Propósito de la Aplicación 

Esta aplicación Android, llamada "FisioCare", está diseñada para mostrar y gestionar información básica sobre tratamientos. Permite a los usuarios ver un listado de tratamientos, acceder a sus detalles específicos, revisar un historial asociado a cada uno y, si es necesario, modificar cierta información.

### 2. Estructura y Componentes Principales

La aplicación está organizada para separar sus diferentes partes, aunque algunas lógicas aún residen directamente en las pantallas (Actividades). Los componentes clave son:

* **Actividades (Screens):** Son las pantallas que el usuario ve y con las que interactúa.
    * **`MainActivity.kt`**: Es la pantalla principal de la aplicación. Muestra una lista de tratamientos (ejemplo de datos). Al hacer clic en un tratamiento, se abre un diálogo de confirmación para ver sus detalles.
    * **`DetalleTratamientoActivity.kt`**: Muestra la información detallada de un tratamiento específico. Incluye opciones para ver el historial o modificar el tratamiento, usando diálogos de confirmación.
    * **`HistorialTratamientoActivity.kt`**: Muestra un resumen o detalles del historial de un tratamiento.
    * **`ModificarTratamientoActivity.kt`**: Permite al usuario simular la modificación de un tratamiento, incluyendo diálogos para confirmar y gestionar el guardado de cambios.
    * **`BaseActivity.kt`**: Una actividad de la que heredan las demás. Configura una estructura de pantalla común, que incluye un espacio para el contenido y una barra de navegación inferior.

* **Layouts (Diseños de Pantalla):** Archivos `.xml` que definen cómo se ve cada pantalla.
    * `activity_tratamientos.xml`: El diseño principal de `MainActivity` para mostrar la lista de tratamientos.
    * `activity_detalle_tratamiento.xml`: Diseño para la pantalla de detalles del tratamiento.
    * `activity_historial_tratamiento.xml`: Diseño para la pantalla del historial del tratamiento.
    * `activity_modificar_tratamiento.xml`: Diseño para la pantalla de modificación del tratamiento.
    * `dialog_tratamiento.xml`: Diseño para los diálogos de confirmación personalizados.
    * `card_tratamiento.xml`: Diseño de una única "tarjeta" de tratamiento que se repite en la lista principal.

* **Modelos de Datos (`model/`):**
    * **`Tratamiento.kt`**: Una clase simple que define cómo se representa un tratamiento (fecha, especialidad, doctor).

* **ViewModels (`viewmodels/`):** Son las clases que ayudan a separar la lógica de las pantallas.
    * **`DetalleTratamientoViewModel.kt`**: Gestiona los datos y la lógica para la pantalla de detalles del tratamiento.
    * **`HistorialTratamientoViewModel.kt`**: Gestiona los datos y la lógica para la pantalla del historial del tratamiento.
    * **`ModificarTratamientoViewModel.kt`**: Gestiona los datos y la lógica para la pantalla de modificación del tratamiento.

* **Utilidades (`utils/`):**
    * **`SingleLiveEvent.kt`**: Una herramienta especial para enviar "eventos" (como una navegación o un diálogo) desde los ViewModels a las pantallas de forma segura, asegurando que solo se activen una vez.

### 3. Cómo Funciona el Código

La aplicación sigue un enfoque que empieza a usar el patrón **MVVM (Model-View-ViewModel)**. Esto significa que:

* **Las Actividades (pantallas) se encargan de lo visual:** Muestran los elementos en pantalla (texto, botones) y detectan cuando el usuario interactúa con ellos (hace clic).
* **Los ViewModels se encargan de la lógica:** Cuando el usuario hace clic en un botón, la Actividad le informa al ViewModel. El ViewModel decide qué hacer (ej. "mostrar un diálogo", "guardar datos", "ir a otra pantalla"). Si el ViewModel necesita datos, los "simula" o, en una aplicación más grande, los pediría a una base de datos.
* **El ViewModel le dice a la Actividad qué hacer con la UI:** Si el ViewModel decide que debe mostrar un diálogo o navegar a otra pantalla, no lo hace directamente. En su lugar, envía un "evento" a través de un `LiveData` (o `SingleLiveEvent`) que la Actividad está observando. Cuando la Actividad recibe este evento, entonces ella es la que ejecuta la acción visual (muestra el diálogo, inicia la nueva Actividad).

**Ejemplo de Flujo (Detalle de Tratamiento):**

1.  **Usuario en `DetalleTratamientoActivity`:** Ve los detalles de un tratamiento y hace clic en "Ver Historial".
2.  **`DetalleTratamientoActivity` (View):** Detecta el clic y le dice a su ViewModel: `viewModel.onHistorialButtonClicked()`.
3.  **`DetalleTratamientoViewModel` (ViewModel):** Recibe la llamada. Decide que debe pedir a la pantalla que muestre un diálogo de confirmación. Envía un evento: `_showHistorialDialog.setValue(tratamientoActual)`.
4.  **`DetalleTratamientoActivity` (View):** Está observando `showHistorialDialog`. Cuando recibe el evento, llama a su función `showCustomDialog()` para mostrar el diálogo.
5.  **Usuario en el Diálogo:** Hace clic en "Confirmar".
6.  **`DetalleTratamientoActivity` (View):** Llama a otro método del ViewModel: `viewModel.onHistorialDialogConfirmed()`.
7.  **`DetalleTratamientoViewModel` (ViewModel):** Recibe la confirmación. Decide que la pantalla debe navegar al historial. Envía un evento: `_navigateToHistorial.setValue(tratamientoActual)`.
8.  **`DetalleTratamientoActivity` (View):** Está observando `navigateToHistorial`. Cuando recibe el evento, crea un `Intent` para `HistorialTratamientoActivity` y la inicia.

### 4. Tecnologías Clave

* **Kotlin:** El lenguaje de programación utilizado.
* **Android Jetpack:** Un conjunto de librerías de Google que facilitan el desarrollo, incluyendo:
    * **`LiveData` y `ViewModel`:** Para implementar el patrón MVVM.
    * **`SavedStateHandle`:** Una herramienta para que los ViewModels recuperen datos pasados entre pantallas y sobrevivan a cambios de configuración.
    * **Material Design:** Para el diseño visual moderno de la interfaz de usuario.
* **Gradle:** Herramienta que compila la aplicación.

### 5. Cómo Ejecutar la Aplicación

1.  Abre el proyecto en **Android Studio**.
2.  Deja que Gradle sincronice las dependencias.
3.  Conecta un dispositivo Android o inicia un emulador.
4.  Haz clic en el botón **"Run"** (el triángulo verde) en Android Studio.

---