package com.example.culturapp.fragments

import Users
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
import com.example.culturapp.api.calls.BookingsCall
import com.example.culturapp.api.calls.EventsCall
import com.example.culturapp.clases.Bookings
import com.example.culturapp.clases.Events
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private lateinit var event: Events
private lateinit var user: Users

class ReservarEventosFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            event = it.getSerializable("event") as Events
            user = it.getSerializable("user") as Users
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
                             ): View? {
        return inflater.inflate(R.layout.fragment_reservar_eventos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        CoroutineScope(Dispatchers.IO). launch {
            val bundle = Bundle()

            try {
                val bookings: List<Bookings> = BookingsCall().getBookingEvent(event.id)

                withContext(Dispatchers.Main) {
                    var totalQuantity = 0

                    for (booking in bookings) {
                        totalQuantity += booking.quantity
                    }

                    val quantityDispo = event.capacity - totalQuantity
                    val lblEntradasDispo = view.findViewById<TextView>(R.id.lblEntradasDispo)

                    lblEntradasDispo.text = quantityDispo.toString()

                    bundle.putInt("quantityDispo", quantityDispo)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,"Error al obtener entradas disponibles", Toast.LENGTH_SHORT).show() //guardar texto
                }
            }

            val lblNombreEvento = view.findViewById<TextView>(R.id.lblNombreEvento)
            val lblFechaInicio = view.findViewById<TextView>(R.id.lblFechaInicio)
            val lblFechFinal = view.findViewById<TextView>(R.id.lblFechFinal)
            val lblTipo = view.findViewById<TextView>(R.id.lblTipo)
            val lblPrecio = view.findViewById<TextView>(R.id.lblPrecio)
            val lblSala = view.findViewById<TextView>(R.id.lblSala)
            val lblEntradas = view.findViewById<TextView>(R.id.lblEntradas)
            val lblDescripcion = view.findViewById<TextView>(R.id.lblDescripcion)
            val btnReservar = view.findViewById<TextView>(R.id.btnReservar)

            lblNombreEvento.text = event.title
            lblFechaInicio.text = event.start_datetime
            lblFechFinal.text = event.end_datetime
            lblTipo.text = event.type_event.name
            lblPrecio.text = event.price.toString()
            lblSala.text = event.rooms.name
            lblEntradas.text = event.capacity.toString()
            lblDescripcion.text = event.description

            btnReservar.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val bookings: List<Bookings> = BookingsCall().getBookingEvent(event.id)

                        withContext(Dispatchers.Main) {
                            if (bookings.find { it.user_id == user.id } != null) {
                                Toast.makeText(context, "Ya tienes una reserva para este evento.", Toast.LENGTH_SHORT).show()
                            } else {
                                val cantidadFragment = CantidadFragment()

                                bundle.putSerializable("event", event)
                                bundle.putSerializable("user", user)

                                cantidadFragment.arguments = bundle

                                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                                transaction.replace(R.id.fragment_container, cantidadFragment)
                                transaction.addToBackStack(null)
                                transaction.commit()
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context, "Error al verificar reservas", Toast.LENGTH_SHORT
                                          ).show()
                        }
                    }
                }
            }
        }
    }
}