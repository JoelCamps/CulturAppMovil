package com.example.culturapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.culturapp.R
import com.example.culturapp.adapters.EventoAdapter
import com.example.culturapp.api.calls.EventsCall
import com.example.culturapp.clases.Events
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private lateinit var event: Events

class ReservarEventosFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            event = it.getSerializable("event") as Events
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
                             ): View? {
        return inflater.inflate(R.layout.fragment_reservar_eventos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val lblNombreEvento = view.findViewById<TextView>(R.id.lblNombreEvento)
        val lblFechaInicio = view.findViewById<TextView>(R.id.lblFechaInicio)
        val lblFechFinal = view.findViewById<TextView>(R.id.lblFechFinal)
        val lblTipo = view.findViewById<TextView>(R.id.lblTipo)
        val lblPrecio = view.findViewById<TextView>(R.id.lblPrecio)
        val lblSala = view.findViewById<TextView>(R.id.lblSala)
        val lblEntradas = view.findViewById<TextView>(R.id.lblEntradas)
        val lblEntradasDispo = view.findViewById<TextView>(R.id.lblEntradasDispo)
        val lblDescripcion = view.findViewById<TextView>(R.id.lblDescripcion)
        val btnReservar = view.findViewById<TextView>(R.id.btnReservar)

        lblNombreEvento.text = event.title
        lblFechaInicio.text = event.start_datetime
        lblFechFinal.text = event.end_datetime
        lblTipo.text = event.type_event.name
        lblPrecio.text = event.price.toString()
        lblSala.text = event.rooms.name
        lblEntradas.text = event.capacity.toString()
//        lblEntradasDispo.text = event.
        lblDescripcion.text = event.description
    }
}