package com.example.culturapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import com.example.culturapp.R
import com.example.culturapp.api.calls.EventsCall
import com.example.culturapp.api.calls.RoomsCall
import com.example.culturapp.api.calls.TypeEventCall
import com.example.culturapp.clases.Events
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CrearEventosFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
                             ): View? {
        return inflater.inflate(R.layout.fragment_crear_evento, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val txtNombreEvento = view.findViewById<EditText>(R.id.txtNombreEvento)
        val txtFechaInicio = view.findViewById<EditText>(R.id.txtFechaInicio)
        val txtFechaFinal = view.findViewById<EditText>(R.id.txtFechaFinal)
        val spTipo = view.findViewById<Spinner>(R.id.spTipo)
        val txtPrecio = view.findViewById<EditText>(R.id.txtPrecio)
        val spSala = view.findViewById<Spinner>(R.id.spSala)
        val txtEntradas = view.findViewById<EditText>(R.id.txtEntradas)
        val txtDescripcion = view.findViewById<EditText>(R.id.txtDescripcion)
        val btnCrear = view.findViewById<AppCompatButton>(R.id.btnCrear)

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val typeEvent = TypeEventCall().getTypeEvent()
                val room = RoomsCall().getRooms()

                val typeEventNames = typeEvent.map { it.name }
                val roomNames = room.map { it.name }

                val typeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, typeEventNames)
                typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spTipo.adapter = typeAdapter

                val roomAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, roomNames)
                roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spSala.adapter = roomAdapter

                btnCrear.setOnClickListener {
                    val title = txtNombreEvento.text.trim().toString()
                    val startDate = txtFechaInicio.text.trim().toString()
                    val endDate = txtFechaFinal.text.trim().toString()
                    val entradas = txtEntradas.text.toString().toIntOrNull()
                    val description = txtDescripcion.text.trim().toString()
                    val price = txtPrecio.text.toString().toIntOrNull()

                    val selectedType = spTipo.selectedItemPosition
                    val selectedRoom = spSala.selectedItemPosition

                    val type_id = typeEvent[selectedType].id
                    val room_id = room[selectedRoom].id

                    if (title.isNotBlank() && startDate.isNotBlank() && endDate.isNotBlank() && entradas != null && description.isNotBlank() && price != null) {
                        val newEvent = Events(
                            null,
                            title,
                            startDate,
                            endDate,
                            entradas,
                            description,
                            price,
                            active = true,
                            room_id,
                            type_id,
                            null,
                            null )

                        CoroutineScope(Dispatchers.Main).launch {
                            try {
                                EventsCall().postEvent(newEvent)
                                requireActivity().recreate()
                            }
                            catch (e: Exception) {
                                context?.let { safeContext ->
                                    Toast.makeText(
                                        safeContext,
                                        "Error al crear evento",
                                        Toast.LENGTH_SHORT
                                                  ).show()
                                }
                            }
                        }
                    }
                    else {
                        context?.let { safeContext ->
                            Toast.makeText(
                                safeContext,
                                "Completa todos los campos",
                                Toast.LENGTH_SHORT
                                          ).show()
                        }
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                context?.let { safeContext ->
                    Toast.makeText(
                        safeContext,
                        "Error al cargar los datos",
                        Toast.LENGTH_SHORT
                                  ).show()
                }
            }
        }
    }
}