package com.example.examen2ejercicio1.GestionClases;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreferencesManager {
    private static final String PREF_NAME = "user_preferences";
    private static final String KEY_CLASES = "clases";
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public PreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveClase(Clase clase) {
        Map<String, List<Clase>> clasesMap = loadClases();
        if (!clasesMap.containsKey(clase.getDia())) {
            clasesMap.put(clase.getDia(), new ArrayList<>());
        }
        clasesMap.get(clase.getDia()).add(clase);
        saveClases(clasesMap);
    }

    public Map<String, List<Clase>> loadClases() {
        String json = sharedPreferences.getString(KEY_CLASES, null);
        Type type = new TypeToken<HashMap<String, List<Clase>>>() {}.getType();
        return json != null ? gson.fromJson(json, type) : new HashMap<>();
    }

    private void saveClases(Map<String, List<Clase>> clasesMap) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(clasesMap);
        editor.putString(KEY_CLASES, json);
        editor.apply();
    }
}