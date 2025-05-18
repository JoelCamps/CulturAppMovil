package com.example.culturapp.fragments

import Users
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.SearchView
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.culturapp.R
import com.example.culturapp.adapters.EventsAdapter
import com.example.culturapp.api.calls.EventsCall
import com.example.culturapp.clases.Events
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventosFragment : Fragment() {

    private lateinit var user: Users

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.getSerializable("userlogin") as Users
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
                             ): View? {
        return inflater.inflate(R.layout.fragment_eventos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val svBuscar = view.findViewById<SearchView>(R.id.svBuscar)
        val spFiltro = view.findViewById<Spinner>(R.id.spFiltro)
        val btnCrear = view.findViewById<Button>(R.id.btnCrear)
        val rvEvento = view.findViewById<RecyclerView>(R.id.RVEvento)

        if (user.type.equals("organizator")) {
            btnCrear.visibility = VISIBLE
        } else {
            btnCrear.visibility = GONE
        }

        CoroutineScope(Dispatchers.IO). launch {
            try {
                val events: List<Events> = EventsCall().getEvents()

                val eventosFiltrados = events.toMutableList()
                val tipos = events.map { it.type_event?.name }.distinct().toMutableList()
                tipos.add(0, "Todos")

                withContext(Dispatchers.Main) {
                    rvEvento.layoutManager = LinearLayoutManager(requireContext())
                    val adapter = EventsAdapter(eventosFiltrados, object : EventsAdapter.OnItemClickListener {
                        override fun onItemClick(event: Events) {
                            val reservarEventosFragment = ReservarEventosFragment()

                            val bundle = Bundle()
                            bundle.putSerializable("event", event)
                            bundle.putSerializable("user", user)

                            reservarEventosFragment.arguments = bundle

                            val transaction = requireActivity().supportFragmentManager.beginTransaction()
                            transaction.replace(R.id.fragment_container, reservarEventosFragment)
                            transaction.addToBackStack(null)
                            transaction.commit()
                        }
                    })

                    rvEvento.adapter = adapter

                    svBuscar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

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

                    val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tipos)
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spFiltro.adapter = spinnerAdapter

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
                            // No hacer nada
                        }
                    }
                }
            }
            catch (e: Exception){
                withContext(Dispatchers.Main) {
                    val context = this@EventosFragment.context ?: return@withContext
                    Toast.makeText(context, "Error al mostrar los eventos", Toast.LENGTH_SHORT).show() //guardar texto
                }
            }
        }

        btnCrear.setOnClickListener {
            val crearEventos = CrearEventosFragment()

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, crearEventos)
                .commit()
        }
    }

    companion object {
        fun newInstance(users: Users): EventosFragment {
            val fragment = EventosFragment()
            val args = Bundle()
            args.putSerializable("userlogin", users)
            fragment.arguments = args
            return fragment
        }
    }
}