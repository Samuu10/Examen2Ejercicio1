package com.example.examen2ejercicio1.GestionClases;

import android.os.Parcel;
import android.os.Parcelable;

//Clase que representa un objeto Clase y que implementa Parcelable para pasar objetos entre actividades y fragmentos
public class Clase implements Parcelable {

    //Atributos
    private String nombre;
    private String dia;
    private String hora;

    //Constructor sin parámetros
    public Clase() {}

    //Constructor con parámetros
    public Clase(String nombre, String dia, String hora) {
        this.nombre = nombre;
        this.dia = dia;
        this.hora = hora;
    }

    //Constructor Parcelable
    protected Clase(Parcel in) {
        nombre = in.readString();
        dia = in.readString();
        hora = in.readString();
    }

    //Metodo CREATOR implementado por Parcelable
    public static final Parcelable.Creator<Clase> CREATOR = new Parcelable.Creator<Clase>() {
        @Override
        public Clase createFromParcel(Parcel in) {
            return new Clase(in);
        }

        @Override
        public Clase[] newArray(int size) {
            return new Clase[size];
        }
    };

    //Getters & Setters
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDia() {
        return dia;
    }
    public void setDia(String dia) {
        this.dia = dia;
    }
    public String getHora() {
        return hora;
    }
    public void setHora(String hora) {
        this.hora = hora;
    }

    //Metodo describeContents implementado por Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    //Metodo writeToParcel implementado por Parcelable
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(dia);
        dest.writeString(hora);
    }
}