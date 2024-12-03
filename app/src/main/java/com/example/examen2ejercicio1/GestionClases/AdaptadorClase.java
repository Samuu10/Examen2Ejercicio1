package com.example.examen2ejercicio1.GestionClases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.examen2ejercicio1.R;
import java.util.List;

//Clase AdaptadorClase que extiende RecyclerView.Adapter y se utiliza para mostrar la lista de clases
public class AdaptadorClase extends RecyclerView.Adapter<AdaptadorClase.ClaseViewHolder> {

    //Variables
    private Context context;
    private List<Clase> clases;
    private OnItemClickListener onItemClickListener;

    //Constructor
    public AdaptadorClase(Context context, List<Clase> clases) {
        this.context = context;
        this.clases = clases;
    }

    //Interfaz OnItemClickListener que se utiliza para gestionar los eventos de click en los elementos de la lista
    public interface OnItemClickListener {
        void onItemClick(Clase clase);
    }

    //Metodo para establecer el listener de los eventos de click
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    //Metodo para crear una nueva vista
    @NonNull
    @Override
    public ClaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_clase, parent, false);
        return new ClaseViewHolder(view);
    }

    //Metodo para enlazar los datos de la lista con los elementos de la vista
    @Override
    public void onBindViewHolder(@NonNull ClaseViewHolder holder, int position) {
        Clase clase = clases.get(position);
        holder.nombreTextView.setText(clase.getNombre());
        holder.horaTextView.setText(clase.getHora() + "h");

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(clase);
            }
        });
    }

    //Metodo para obtener el número de elementos en la lista
    @Override
    public int getItemCount() {
        return clases.size();
    }

    //Clase ClaseViewHolder que extiende RecyclerView.ViewHolder y se utiliza para mantener las referencias de los elementos de la vista
    public static class ClaseViewHolder extends RecyclerView.ViewHolder {

        //Variables
        TextView nombreTextView;
        TextView horaTextView;

        //Constructor
        public ClaseViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.nombre_clase);
            horaTextView = itemView.findViewById(R.id.hora_clase);
        }
    }
}