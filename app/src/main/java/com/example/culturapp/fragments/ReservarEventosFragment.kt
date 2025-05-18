package com.example.culturapp.fragments

import Users
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.culturapp.R
import com.example.culturapp.api.calls.BookingsCall
import com.example.culturapp.clases.Bookings
import com.example.culturapp.clases.Events
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class ReservarEventosFragment : Fragment() {

    private lateinit var event: Events
    private lateinit var user: Users

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
        // Calcula y muestra en la interfaz la cantidad de entradas disponibles para el evento.
        CoroutineScope(Dispatchers.IO).launch {
            val bundle = Bundle()

            try {
                val bookings: List<Bookings>? = event.id?.let { BookingsCall().getBookingEvent(it) }

                withContext(Dispatchers.Main) {
                    var totalQuantity = 0
                    bookings?.forEach { booking ->
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
                        requireContext(), getString(R.string.errorEntradasDispo), Toast.LENGTH_SHORT
                    ).show()
                }
            }

            // Formatear las fechas del evento para mostrar en la UI
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

            val formattedStartDate = try {
                val date = inputFormat.parse(event.start_datetime)
                outputFormat.format(date!!)
            } catch (e: ParseException) {
                Toast.makeText(requireContext(), requireContext().getString(R.string.errorFecha), Toast.LENGTH_SHORT).show()
            }

            val formattedEndDate = try {
                val date = inputFormat.parse(event.end_datetime)
                outputFormat.format(date!!)
            } catch (e: ParseException) {
                Toast.makeText(requireContext(), requireContext().getString(R.string.errorFecha), Toast.LENGTH_SHORT).show()
            }

            withContext(Dispatchers.Main) {
                // Referencias a vistas para mostrar la información del evento
                val lblNombreEvento = view.findViewById<TextView>(R.id.lblNombreEvento)
                val lblFechaInicio = view.findViewById<TextView>(R.id.lblFechaInicio)
                val lblFechFinal = view.findViewById<TextView>(R.id.lblFechFinal)
                val lblTipo = view.findViewById<TextView>(R.id.lblTipo)
                val lblPrecio = view.findViewById<TextView>(R.id.lblPrecio)
                val lblSala = view.findViewById<TextView>(R.id.lblSala)
                val lblEntradas = view.findViewById<TextView>(R.id.lblEntradas)
                val lblDescripcion = view.findViewById<TextView>(R.id.lblDescripcion)
                val btnReservar = view.findViewById<TextView>(R.id.btnReservar)

                // Mostrar la información del evento en las vistas correspondientes
                lblNombreEvento.text = event.title
                lblFechaInicio.text = formattedStartDate.toString()
                lblFechFinal.text = formattedEndDate.toString()
                lblTipo.text = event.type_event?.name
                lblPrecio.text = event.price.toString()
                lblSala.text = event.rooms?.name
                lblEntradas.text = event.capacity.toString()
                lblDescripcion.text = event.description

                // Verifica si el usuario ya tiene reserva y, si no, abre el fragmento para para reservar.
                btnReservar.setOnClickListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val bookings: List<Bookings>? = event.id?.let { BookingsCall().getBookingEvent(it) }

                            withContext(Dispatchers.Main) {
                                if (bookings?.any { it.user_id == user.id } == true) {
                                    Toast.makeText(context, getString(R.string.yaTienes), Toast.LENGTH_SHORT).show()
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
                                Toast.makeText(context, getString(R.string.errorVerificar), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }
}
