package com.example.examen2ejercicio1.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.examen2ejercicio1.R;
import java.util.ArrayList;
import java.util.List;

//Fragmento principal que muestra el horario de clases de la semana
public class FragmentoVerHorario extends Fragment {

    //Variables
    private Spinner diaSemana;

    //Metodo para crear la vista del fragmento
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_ver_horario, container, false);

        //Inicializamos las variables
        diaSemana = view.findViewById(R.id.sp_dia_semana);

        // Configurar Spinner con los días de la semana
        List<String> diasSemana = new ArrayList<>();
        diasSemana.add("Lunes");
        diasSemana.add("Martes");
        diasSemana.add("Miércoles");
        diasSemana.add("Jueves");
        diasSemana.add("Viernes");

        //Configuramos un adaptador para el Spinner con los días de la semana
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, diasSemana);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diaSemana.setAdapter(adapter);

        //Mostrar la lista de clases del día seleccionado
        diaSemana.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String diaSeleccionado = diasSemana.get(position);
                mostrarListaClases(diaSeleccionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //Mostramos la lista de clases del dia de la semana actual
        mostrarListaClases(diasSemana.get(0));

        return view;
    }

    //Metodo para mostrar la lista de clases del día de la semana seleccionado
    private void mostrarListaClases(String diaSemana) {
        FragmentoListaClases fragmentoListaClases = FragmentoListaClases.newInstance(diaSemana);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_lista_clases, fragmentoListaClases);
        transaction.commit();
    }
}