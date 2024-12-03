package com.example.examen2ejercicio1.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.examen2ejercicio1.GestionClases.*;
import com.example.examen2ejercicio1.R;
import com.example.examen2ejercicio1.Utils.PreferencesManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Fragmento que muestra la lista de clases según el día de la semana seleccionado
public class FragmentoListaClases extends Fragment {

    //Variables
    private static final String ARG_DIA_SEMANA = "dia_semana";
    private RecyclerView recyclerView;
    private AdaptadorClase adaptadorClase;
    private List<Clase> listaClases;
    private PreferencesManager preferencesManager;

    //Metodo para crear una nueva instancia del fragmento
    public static FragmentoListaClases newInstance(String diaSemana) {
        FragmentoListaClases fragment = new FragmentoListaClases();
        Bundle args = new Bundle();
        args.putString(ARG_DIA_SEMANA, diaSemana);
        fragment.setArguments(args);
        return fragment;
    }

    //Metodo para crear la vista del fragmento
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_lista_clases, container, false);

        //Inicializamos las variables
        recyclerView = view.findViewById(R.id.recycler_view_clases);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        preferencesManager = new PreferencesManager(getContext());

        //Cargamos las clases del día de la semana seleccionado
        if (getArguments() != null) {
            String diaSemana = getArguments().getString(ARG_DIA_SEMANA);
            cargarClases(diaSemana);
        }

        return view;
    }

    //Metodo para cargar las clases del día de la semana seleccionado en el RecyclerView del fragmento
    private void cargarClases(String diaSemana) {
        listaClases = preferencesManager.cargarClases().get(diaSemana);
        if (listaClases == null) {
            listaClases = new ArrayList<>();
        } else {
            Collections.sort(listaClases, (clase1, clase2) -> {
                int hora1 = Integer.parseInt(clase1.getHora().split(":")[0]);
                int hora2 = Integer.parseInt(clase2.getHora().split(":")[0]);
                return Integer.compare(hora1, hora2);
            });
        }

        adaptadorClase = new AdaptadorClase(getContext(), listaClases);
        recyclerView.setAdapter(adaptadorClase);

        adaptadorClase.setOnItemClickListener(clase -> mostrarDialogoConfirmacion(clase, diaSemana));
    }

    private void mostrarDialogoConfirmacion(Clase clase, String diaSemana) {
        new AlertDialog.Builder(getContext())
                .setTitle("Eliminar Clase")
                .setMessage("¿Estás seguro de que deseas eliminar esta clase del horario?")
                .setPositiveButton("Aceptar", (dialog, which) -> eliminarClase(clase, diaSemana))
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void eliminarClase(Clase clase, String diaSemana) {
        listaClases.remove(clase);
        preferencesManager.eliminarClase(clase, diaSemana);
        adaptadorClase.notifyDataSetChanged();
    }
}