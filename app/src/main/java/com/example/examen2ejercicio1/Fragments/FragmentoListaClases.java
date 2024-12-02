package com.example.examen2ejercicio1.Fragments;

import android.annotation.SuppressLint;
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
import java.util.ArrayList;
import java.util.List;

//Fragmento que muestra la lista de clases según el día de la semana seleccionado
public class FragmentoListaClases extends Fragment {

    //Variables
    private static final String ARG_DIA_SEMANA = "dia_semana";
    private RecyclerView recyclerView;
    private AdaptadorClase adaptadorClase;
    private List<Clase> listaClases;
    private PreferencesManager preferencesManager;

    public static FragmentoListaClases newInstance(String diaSemana) {
        FragmentoListaClases fragment = new FragmentoListaClases();
        Bundle args = new Bundle();
        args.putString(ARG_DIA_SEMANA, diaSemana);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_lista_clases, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_clases);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        preferencesManager = new PreferencesManager(getContext());

        if (getArguments() != null) {
            String diaSemana = getArguments().getString(ARG_DIA_SEMANA);
            cargarClases(diaSemana);
        }

        return view;
    }

    private void cargarClases(String diaSemana) {
        List<Clase> listaClases = preferencesManager.loadClases().get(diaSemana);
        adaptadorClase = new AdaptadorClase(getContext(), listaClases != null ? listaClases : new ArrayList<>());
        recyclerView.setAdapter(adaptadorClase);
    }
}