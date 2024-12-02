package com.example.examen2ejercicio1.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.examen2ejercicio1.GestionClases.*;
import com.example.examen2ejercicio1.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//Fragmento para agregar una nueva clase al horario
public class FragmentoAgregarClase extends Fragment {

    //Variables
    private EditText nombre;
    private Spinner dia;
    private TimePicker hora;
    private PreferencesManager preferencesManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_agregar_clase, container, false);

        nombre = view.findViewById(R.id.et_nombre_asignatura);
        dia = view.findViewById(R.id.sp_dia_semana);
        hora = view.findViewById(R.id.tp_hora_asignatura);
        hora.setIs24HourView(true);
        preferencesManager = new PreferencesManager(getContext());

        //Configuramos el TimePicker para que solo se puedan seleccionar horas en punto
        hora.setOnTimeChangedListener((view1, hourOfDay, minute) -> {
            if (minute != 0) {
                hora.setMinute(0);
            }
        });

        //Configuramos el Spinner con los días laborales de la semana
        List<String> diasSemana = new ArrayList<>();
        diasSemana.add("Lunes");
        diasSemana.add("Martes");
        diasSemana.add("Miércoles");
        diasSemana.add("Jueves");
        diasSemana.add("Viernes");

        //Creamos un adaptador para el Spinner y lo configuramos
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, diasSemana);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dia.setAdapter(adapter);

        //Configuramos los botones
        Button btnAgregar = view.findViewById(R.id.btn_agregar);
        Button btnCancelar = view.findViewById(R.id.btn_cancelar);
        btnAgregar.setOnClickListener(v -> agregarClase());
        btnCancelar.setOnClickListener(v -> cancelar());

        return view;
    }

    private void agregarClase() {
        String nombreAsignatura = nombre.getText().toString().trim();
        String diaSemana = dia.getSelectedItem().toString();
        int hora = this.hora.getHour();

        if (nombreAsignatura.isEmpty() || diaSemana.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Clase nuevaClase = new Clase(nombreAsignatura, diaSemana, hora + ":00");

        //Aseguramos que no haya otra clase a la misma hora
        Map<String, List<Clase>> clasesMap = preferencesManager.loadClases();
        List<Clase> clasesDelDia = clasesMap.get(diaSemana);
        if (clasesDelDia != null) {
            for (Clase clase : clasesDelDia) {
                if (clase.getHora().equals(nuevaClase.getHora())) {
                    Toast.makeText(getContext(), "Ya existe una clase a esa hora", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        new SaveClaseTask().execute(nuevaClase);
    }

    private class SaveClaseTask extends AsyncTask<Clase, Void, Void> {
        @Override
        protected Void doInBackground(Clase... clases) {
            preferencesManager.saveClase(clases[0]);
            return null;
        }

        //Enseñamos un mensaje de confirmación y volvemos al fragmento inicial
        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getContext(), "Clase agregada al horario", Toast.LENGTH_SHORT).show();
            getParentFragmentManager().popBackStack();
        }
    }

    //Metodo para cancelar la operación y volver al fragmento inicial
    private void cancelar() {
        getParentFragmentManager().popBackStack();
    }
}