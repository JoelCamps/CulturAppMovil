package com.example.culturapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.culturapp.R
import com.example.culturapp.clases.Bookings
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class CancelarReservaFragment : Fragment() {

    private lateinit var booking: Bookings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            booking = it.getSerializable("booking") as Bookings
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
                             ): View? {
        return inflater.inflate(R.layout.fragment_cancelar_reserva, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bundle = Bundle()

        // Formateo de fechas desde el formato original al estándar
        val inputFormat = SimpleDateFormat("MMM dd yyyy h:mma", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

        val formattedStartDate = try {
            val date = inputFormat.parse(booking.events?.start_datetime)
            outputFormat.format(date!!)
        } catch (e: ParseException) {
            "Fecha inválida"
        }

        val formattedEndDate = try {
            val date = inputFormat.parse(booking.events?.end_datetime)
            outputFormat.format(date!!)
        } catch (e: ParseException) {
            "Fecha inválida"
        }

        // Inicializa vistas principales
        val lblNombreEvento = view.findViewById<TextView>(R.id.lblNombreEvento)
        val lblFechaInicio = view.findViewById<TextView>(R.id.lblFechaInicio)
        val lblFechFinal = view.findViewById<TextView>(R.id.lblFechFinal)
        val lblTipo = view.findViewById<TextView>(R.id.lblTipo)
        val lblPrecio = view.findViewById<TextView>(R.id.lblPrecio)
        val lblSala = view.findViewById<TextView>(R.id.lblSala)
        val lblEntradas = view.findViewById<TextView>(R.id.lblEntradas)
        val lblDescripcion = view.findViewById<TextView>(R.id.lblDescripcion)
        val btnCancelar = view.findViewById<TextView>(R.id.btnCancelar)

        // Asignación de datos a la interfaz
        lblNombreEvento.text = booking.events?.title
        lblFechaInicio.text = formattedStartDate
        lblFechFinal.text = formattedEndDate
        lblTipo.text = booking.events?.type_event?.name
        lblPrecio.text = booking.events?.price.toString()
        lblSala.text = booking.events?.rooms?.name
        lblEntradas.text = booking.quantity.toString()
        lblDescripcion.text = booking.events?.description

        // Abre el fragmento de confirmación de cancelación
        btnCancelar.setOnClickListener {
            val confCancelarFragment = ConfCancelarFragment()

            bundle.putSerializable("booking", booking)

            confCancelarFragment.arguments = bundle

            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, confCancelarFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}