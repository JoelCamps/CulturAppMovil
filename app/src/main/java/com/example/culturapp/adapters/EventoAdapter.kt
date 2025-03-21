package com.example.culturapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.culturapp.R
import com.example.culturapp.clases.Evento

class EventoAdapter(private val eventos: List<Evento>) : RecyclerView.Adapter<EventoAdapter.EventoViewHolder>() {

    class EventoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView = view.findViewById(R.id.txtNombreEvento)
        val txtFecha: TextView = view.findViewById(R.id.txtFechaEvento)
        val txtTipo: TextView = view.findViewById(R.id.txtTipoEvento)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_evento, parent, false)
        return EventoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventoViewHolder, position: Int) {
        val evento = eventos[position]
        holder.txtNombre.text = evento.nombre
        holder.txtFecha.text = evento.fecha
        holder.txtTipo.text = evento.tipo
    }

    override fun getItemCount() = eventos.size
}
