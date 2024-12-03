package com.example.examen2ejercicio1.GestionClases;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadcastClase extends BroadcastReceiver {
    public static final String ACTION_CLASES_UPDATED = "com.example.examen2ejercicio1.ACTION_CLASES_UPDATED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_CLASES_UPDATED.equals(intent.getAction())) {
            // Notify listeners about the update
            if (context instanceof BroadcastListener) {
                ((BroadcastListener) context).onClasesUpdated();
            }
        }
    }

    public interface BroadcastListener {
        void onClasesUpdated();
    }
}