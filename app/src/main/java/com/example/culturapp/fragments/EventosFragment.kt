package com.example.culturapp.fragments

import Users
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.culturapp.R
import com.example.culturapp.adapters.EventsAdapter
import com.example.culturapp.api.calls.EventsCall
import com.example.culturapp.clases.Events
import kotlinx.coroutines.*

class EventosFragment : Fragment() {

    private lateinit var user: Users

    // Recupera el usuario pasado como argumento
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.getSerializable("userlogin") as Users
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_eventos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Referencias a vistas
        val svBuscar = view.findViewById<SearchView>(R.id.svBuscar)
        val spFiltro = view.findViewById<Spinner>(R.id.spFiltro)
        val btnCrear = view.findViewById<Button>(R.id.btnCrear)
        val rvEvento = view.findViewById<RecyclerView>(R.id.RVEvento)

        btnCrear.visibility = if (user.type == "organizator") View.VISIBLE else View.GONE

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val events: List<Events> = EventsCall().getEvents()

                val eventosFiltrados = events.toMutableList()
                val tipos = events.map { it.type_event?.name }.distinct().toMutableList()
                tipos.add(0, "Todos")

                withContext(Dispatchers.Main) {
                    // Configura RecyclerView y abre a ReservarEventosFragment al hacer clic
                    rvEvento.layoutManager = LinearLayoutManager(requireContext())
                    val adapter = EventsAdapter(eventosFiltrados, object : EventsAdapter.OnItemClickListener {
                        override fun onItemClick(event: Events) {
                            val reservarEventosFragment = ReservarEventosFragment()
                            val bundle = Bundle()
                            bundle.putSerializable("event", event)
                            bundle.putSerializable("user", user)
                            reservarEventosFragment.arguments = bundle

                            requireActivity().supportFragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, reservarEventosFragment)
                                .addToBackStack(null)
                                .commit()
                        }
                    })
                    rvEvento.adapter = adapter

                    // Filtro de búsqueda por texto
                    svBuscar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean = false
                        override fun onQueryTextChange(newText: String?): Boolean {
                            val texto = newText.orEmpty().lowercase()
                            val listaFiltrada = events.filter {
                                it.title.lowercase().contains(texto) ||
                                        it.description.lowercase().contains(texto)
                            }
                            eventosFiltrados.clear()
                            eventosFiltrados.addAll(listaFiltrada)
                            adapter.updateList(eventosFiltrados)
                            return true
                        }
                    })

                    // Adaptador para el filtro por tipo de evento
                    val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tipos)
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spFiltro.adapter = spinnerAdapter

                    // Filtrado por tipo de evento
                    spFiltro.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                            val tipoSeleccionado = tipos[position]
                            val listaFiltrada = if (tipoSeleccionado == "Todos") {
                                events
                            } else {
                                events.filter { it.type_event?.name == tipoSeleccionado }
                            }
                            eventosFiltrados.clear()
                            eventosFiltrados.addAll(listaFiltrada)
                            adapter.updateList(eventosFiltrados)
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    val context = this@EventosFragment.context ?: return@withContext
                    Toast.makeText(context, "Error al mostrar los eventos", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Acción del botón "Crear Evento"
        btnCrear.setOnClickListener {
            val crearEventos = CrearEventosFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, crearEventos)
                .commit()
        }
    }

    companion object {
        // Abre el formulario para crear un nuevo evento
        fun newInstance(users: Users): EventosFragment {
            val fragment = EventosFragment()
            val args = Bundle()
            args.putSerializable("userlogin", users)
            fragment.arguments = args
            return fragment
        }
    }
}