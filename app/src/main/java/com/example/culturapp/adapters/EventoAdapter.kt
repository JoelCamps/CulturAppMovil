package com.example.culturapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.culturapp.R
import com.example.culturapp.clases.Events
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class EventoAdapter(
    private val events: List<Events>,
    private val listener: OnItemClickListener,
                   ) : RecyclerView.Adapter<EventoAdapter.EventoViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(event: Events)
    }

    class EventoViewHolder(view: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView = view.findViewById(R.id.txtNombreEvento)
        val txtFecha: TextView = view.findViewById(R.id.txtFechaEvento)
        val txtTipo: TextView = view.findViewById(R.id.txtTipoEvento)

        init {
            view.setOnClickListener {
                listener.onItemClick(itemView.tag as Events)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_evento, parent, false)
        return EventoViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: EventoViewHolder, position: Int) {
        val inputFormat = SimpleDateFormat("MMM dd yyyy h:mma", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

        val formattedDate = try {
            val date = inputFormat.parse(events[position].start_datetime)
            outputFormat.format(date!!)
        } catch (e: ParseException) {
            "Fecha inválida"
        }

        val evento = events[position]
        holder.txtNombre.text = evento.title
        holder.txtFecha.text = formattedDate
        holder.txtTipo.text = evento.type_event?.name

        holder.itemView.tag = evento
    }

    override fun getItemCount() = events.size
}

