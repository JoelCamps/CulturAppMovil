package com.example.culturapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.culturapp.R
import com.example.culturapp.api.calls.BookingsCall
import com.example.culturapp.clases.Bookings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ConfCancelarFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_conf_cancelar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa vistas principales
        val btnSalir = view.findViewById<AppCompatButton>(R.id.btnSalir)
        val btnCancelar = view.findViewById<AppCompatButton>(R.id.btnCancelar)

        // Cierra el fragmento
        btnSalir.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        // Cancela la reserva y actualiza
        btnCancelar.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    booking.active = false
                    BookingsCall().putBooking(booking)

                    requireActivity().recreate()
                } catch (e: Exception) {
                    Log.e("API Error", "Excepción: ${e.message}")
                    Toast.makeText(requireContext(), "Error al realizar la solicitud", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}