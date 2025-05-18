package com.example.culturapp.fragments

import Users
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.culturapp.R
import com.example.culturapp.adapters.BookingAdapter
import com.example.culturapp.api.calls.BookingsCall
import com.example.culturapp.clases.Bookings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReservasFragment : Fragment() {

    private lateinit var user: Users

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.getSerializable("userlogin") as Users
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reservas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Referencias a vistas
        val svBuscar = view.findViewById<SearchView>(R.id.svBuscar)
        val spFiltro = view.findViewById<Spinner>(R.id.spFiltro)
        val rvReserva = view.findViewById<RecyclerView>(R.id.RVReserva)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Obtener reservas del usuario y tipos para filtro
                val bookings: List<Bookings>? = user.id?.let { BookingsCall().getBookingUser(it) }
                val reservasFiltrados = bookings?.toMutableList()
                val tipos = bookings?.mapNotNull { it.events?.type_event?.name }?.distinct()?.toMutableList()
                tipos?.add(0, "Todos")

                withContext(Dispatchers.Main) {
                    rvReserva.layoutManager = LinearLayoutManager(requireContext())

                    if (reservasFiltrados != null) {
                        val adapter = BookingAdapter(reservasFiltrados, object : BookingAdapter.OnItemClickListener {
                            override fun onItemClick(booking: Bookings) {
                                // Abrir fragmento para cancelar reserva
                                val cancelarReservaFragment = CancelarReservaFragment()
                                val bundle = Bundle()
                                bundle.putSerializable("booking", booking)
                                cancelarReservaFragment.arguments = bundle

                                requireActivity().supportFragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, cancelarReservaFragment)
                                    .addToBackStack(null)
                                    .commit()
                            }
                        })

                        rvReserva.adapter = adapter

                        // Buscar reservas por texto en título o descripción
                        svBuscar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                            override fun onQueryTextSubmit(query: String?) = false

                            override fun onQueryTextChange(newText: String?): Boolean {
                                val texto = newText.orEmpty().lowercase()
                                val listaFiltrada = bookings.filter {
                                    it.events?.title?.contains(texto, true) == true ||
                                            it.events?.description?.contains(texto, true) == true
                                }
                                reservasFiltrados.clear()
                                reservasFiltrados.addAll(listaFiltrada)
                                adapter.updateList(reservasFiltrados)
                                return true
                            }
                        })

                        // Configurar filtro por tipo de evento
                        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tipos ?: mutableListOf())
                        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spFiltro.adapter = spinnerAdapter

                        spFiltro.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                                val tipoSeleccionado = tipos?.get(position)
                                val listaFiltrada = if (tipoSeleccionado == "Todos") bookings else bookings.filter { it.events?.type_event?.name == tipoSeleccionado }
                                reservasFiltrados.clear()
                                reservasFiltrados.addAll(listaFiltrada)
                                adapter.updateList(reservasFiltrados)
                            }

                            override fun onNothingSelected(parent: AdapterView<*>) {}
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error al mostrar las reservas", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        // Crea una nueva instancia de ReservasFragment y pasa el objeto Users
        fun newInstance(users: Users): ReservasFragment {
            val fragment = ReservasFragment()
            val args = Bundle()
            args.putSerializable("userlogin", users)
            fragment.arguments = args
            return fragment
        }
    }
}