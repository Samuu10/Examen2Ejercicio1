package com.example.examen2ejercicio1.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.examen2ejercicio1.GestionClases.Clase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Clase PreferencesManager que se utiliza para gestionar las preferencias de la aplicación y almacenar las clases en el SharedPreferences
public class PreferencesManager {

    //Variables
    private static final String PREF_NAME = "user_preferences";
    private static final String KEY_CLASES = "clases";
    private SharedPreferences sharedPreferences;
    private Gson gson;

    //Constructor
    public PreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    //Metodo para guardar una clase en SharedPreferences
    public void guardarClase(Clase clase) {
        Map<String, List<Clase>> clasesMap = cargarClases();
        if (!clasesMap.containsKey(clase.getDia())) {
            clasesMap.put(clase.getDia(), new ArrayList<>());
        }
        clasesMap.get(clase.getDia()).add(clase);
        almacenarClases(clasesMap);
    }

    //Metodo para cargar las clases de SharedPreferences
    public Map<String, List<Clase>> cargarClases() {
        String json = sharedPreferences.getString(KEY_CLASES, null);
        Type type = new TypeToken<HashMap<String, List<Clase>>>() {}.getType();
        return json != null ? gson.fromJson(json, type) : new HashMap<>();
    }

    //Metodo para almacenar las clases en SharedPreferences
    private void almacenarClases(Map<String, List<Clase>> clasesMap) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(clasesMap);
        editor.putString(KEY_CLASES, json);
        editor.apply();
    }

    public void eliminarClase(Clase clase, String diaSemana) {
        Map<String, List<Clase>> clasesMap = cargarClases();
        List<Clase> clasesDelDia = clasesMap.get(diaSemana);
        if (clasesDelDia != null) {
            clasesDelDia.remove(clase);
            if (clasesDelDia.isEmpty()) {
                clasesMap.remove(diaSemana);
            }
            almacenarClases(clasesMap);
        }
    }
}