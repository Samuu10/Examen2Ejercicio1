package com.example.examen2ejercicio1.Activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.examen2ejercicio1.Fragments.FragmentoAgregarClase;
import com.example.examen2ejercicio1.Fragments.FragmentoClaseActual;
import com.example.examen2ejercicio1.Fragments.FragmentoVerHorario;
import com.example.examen2ejercicio1.R;

public class ActividadPrincipal extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);

        //Establecemos el fragmento del horario como el principal
        if (savedInstanceState == null) {
            loadFragment(new FragmentoVerHorario());
        }

        //Configuramos los botones para cambiar entre fragmentos
        findViewById(R.id.btn_agregar_clase).setOnClickListener(v -> loadFragment(new FragmentoAgregarClase()));
        findViewById(R.id.btn_ver_horario).setOnClickListener(v -> loadFragment(new FragmentoVerHorario()));
        findViewById(R.id.btn_clase_actual).setOnClickListener(v -> loadFragment(new FragmentoClaseActual()));
    }

    //Metodo para cargar fragmentos en el contenedor de fragmentos
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}