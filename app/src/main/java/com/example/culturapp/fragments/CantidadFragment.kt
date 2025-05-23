package com.example.culturapp.fragments

import Users
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.culturapp.R
import com.example.culturapp.api.calls.BookingsCall
import com.example.culturapp.clases.Bookings
import com.example.culturapp.clases.Events
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class CantidadFragment : Fragment() {

    private lateinit var event: Events
    private lateinit var user: Users
    private var quantityDispo by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            event = it.getSerializable("event") as Events
            user = it.getSerializable("user") as Users
            quantityDispo = it.getInt("quantityDispo")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
                             ): View? {
        return inflater.inflate(R.layout.fragment_cantidad, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa vistas principales
        val notification = MediaPlayer.create(requireContext(), R.raw.notification_sound)
        val txtCantidad = view.findViewById<EditText>(R.id.txtCantidad)
        val btnReservar = view.findViewById<AppCompatButton>(R.id.btnReservar)
        val btnCancelar = view.findViewById<AppCompatButton>(R.id.btnCancelar)

        // Valida la cantidad y realiza la reserva si es posible
        btnReservar.setOnClickListener {
            CoroutineScope(Dispatchers.Main). launch {
                val booking: Int? = txtCantidad.text.toString().toIntOrNull()

                if (booking != null) {
                    if (booking <= 0) {
                        Toast.makeText(context, getString(R.string.entradasValido), Toast.LENGTH_SHORT).show()
                    } else{
                        if ((quantityDispo - booking) >= 0){

                            val newBooking = event.id?.let { it1 ->
                                user.id?.let { it2 ->
                                    Bookings(
                                        quantity = booking,
                                        user_id = it2,
                                        event_id = it1,
                                        active = true,
                                        events = null)
                                }
                            }

                            if (newBooking != null) {
                                BookingsCall().postBooking(newBooking)
                            }
                            notification.start()
                            requireActivity().recreate()
                        }
                        else{
                            Toast.makeText(context,getString(R.string.noQuedan), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else{
                    Toast.makeText(context,getString(R.string.ponEntradas), Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Cierra el fragmento
        btnCancelar.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}