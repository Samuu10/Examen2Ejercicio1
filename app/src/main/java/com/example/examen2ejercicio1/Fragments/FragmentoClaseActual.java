package com.example.examen2ejercicio1.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.examen2ejercicio1.GestionClases.Clase;
import com.example.examen2ejercicio1.Utils.PreferencesManager;
import com.example.examen2ejercicio1.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

//Fragmento que muestra la fecha y hora actuales y muestra que clase se está impartiendo en ese momento
public class FragmentoClaseActual extends Fragment {

    //Variables
    private TextView tvHoraActual;
    private TextView tvNombreClase;
    private TextView tvHorarioClase;
    private PreferencesManager preferencesManager;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //Actualizamos la hora cada minuto
            new GetDateTimeTask().execute();
            handler.postDelayed(this, 60000);
        }
    };

    //Metodo para crear la vista del fragmento
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_clase_actual, container, false);

        //Inicializamos las variables
        tvHoraActual = view.findViewById(R.id.tv_hora_actual);
        tvNombreClase = view.findViewById(R.id.tv_nombre_clase);
        tvHorarioClase = view.findViewById(R.id.tv_horario_clase);
        preferencesManager = new PreferencesManager(getContext());

        handler.post(runnable);

        return view;
    }

    //Clase para obtener la fecha y hora actuales en segundo plano
    private class GetDateTimeTask extends AsyncTask<Void, Void, String[]> {
        @Override
        protected String[] doInBackground(Void... voids) {
            String dateTime = obtenerFechaHora();
            Clase claseActual = obtenerClaseActual();
            String nombreClase = claseActual != null ? claseActual.getNombre() : "No tienes clase ahora";
            String horarioClase = claseActual != null ? claseActual.getHora() + " - " + (Integer.parseInt(claseActual.getHora().split(":")[0]) + 1) + ":00" : "";
            return new String[]{dateTime, nombreClase, horarioClase};
        }

        @Override
        protected void onPostExecute(String[] result) {
            tvHoraActual.setText(result[0]);
            tvNombreClase.setText(result[1]);
            tvHorarioClase.setText(result[2]);
        }
    }

    //Metodo para obtener la clase que se está impartiendo en ese momento
    private Clase obtenerClaseActual() {
        Calendar calendar = Calendar.getInstance();
        String diaSemana = new SimpleDateFormat("EEEE", new Locale("es", "ES")).format(calendar.getTime());
        int horaActual = calendar.get(Calendar.HOUR_OF_DAY);

        //Obtenemos las clases del día actual
        Map<String, List<Clase>> clasesMap = preferencesManager.cargarClases();
        List<Clase> clasesDelDia = clasesMap.get(diaSemana);
        if (clasesDelDia != null) {
            for (Clase clase : clasesDelDia) {
                int horaClase = Integer.parseInt(clase.getHora().split(":")[0]);
                if (horaClase == horaActual) {
                    return clase;
                }
            }
        }
        return null;
    }

    //Metodo para obtener la fecha y hora actuales y aplicarle un formato
    private String obtenerFechaHora() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE d 'de' MMMM HH:mm", new Locale("es", "ES"));
        String formattedDate = dateFormat.format(calendar.getTime());
        return capitalize(formattedDate);
    }

    //Metodo para capitalizar la primera letra de la cadena
    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    //Metodo para iniciar la actualización de la hora cuando el fragmento sea visible
    @Override
    public void onResume() {
        super.onResume();
        handler.post(runnable);
    }

    //Metodo para detener la actualización de la hora cuando el fragmento no sea visible
    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }
}