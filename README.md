# APLICACIÓN PARA GESTIONAR EL HORARIO

## OBJETIVO:
El objetivo de este ejercicio es realizar una aplicación que permita al usuario gestionar su propio horario.
Los usuarios podrán crear su propio horario introduciendo los días y horas en los que se imparten sus clases.
Además, la aplicación permitirá consultar fácilmente el horario completo de un día específico.
Por último, incluirá una funcionalidad que mostrará qué asignatura se está impartiendo en ese momento, tomando como referencia la fecha y hora actuales.

## ESTRUCTURA:

### Clases JAVA:

- **ActividadPrincipal.java**: Actividad principal de la aplicación que carga los fragmentos y permite la navegación entre ellos.
- **FragmentoClaseActual.java**: Fragmento que muestra la clase que se está impartiendo en el momento actual según la fecha y hora del usuario.
- **FragmentoAgregarClase.java**: Fragmento que permite al usuario agregar una nueva clase al horario.
- **FragmentoListaClases.java**: Fragmento que muestra la lista de clases según el día de la semana seleccionado.
- **FragmentoVerHorario.java**: Fragmento principal que muestra el horario de clases de la semana por días.
- **AdaptadorClase.java**: Adaptador para el RecyclerView que muestra la lista de clases.
- **Clase.java**: Clase que representa una asignatura con atributos como nombre, día y hora.
- **PreferencesManager.java**: Clase que gestiona las preferencias de la aplicación y almacena las clases en SharedPreferences.

### Archivos XML:

- **AndroidManifest.xml**: Archivo de manifiesto de la aplicación que define la estructura y componentes de la aplicación.
- **themes.xml**: Archivo que define el tema de la aplicación.
- **strings.xml**: Archivo que contiene las cadenas de texto utilizadas en la aplicación.
- **actividad_principal.xml**: Layout de la actividad principal que contiene el contenedor de fragmentos y los botones de navegación.
- **fragmento_clase_actual.xml**: Layout del fragmento que muestra la clase actual.
- **fragmento_agregar_clase.xml**: Layout del fragmento que permite agregar una nueva clase.
- **fragmento_lista_clases.xml**: Layout del fragmento que muestra la lista de clases.
- **fragmento_ver_horario.xml**: Layout del fragmento que muestra el horario de clases de la semana.
- **item_clase.xml**: Layout de cada elemento de la lista de clases en el RecyclerView.
- **item_background.xml**: Archivo que define el fondo de los elementos de la lista de clases.

## LINK:
https://github.com/Samuu10/Examen2Ejercicio1.git