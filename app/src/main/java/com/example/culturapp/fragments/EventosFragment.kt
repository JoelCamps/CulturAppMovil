package com.example.culturapp.fragments

import Users
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.inputmethod.CorrectionInfo
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
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
import java.sql.Timestamp

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

                withContext(Dispatchers.Main) {
                    rvEvento.layoutManager = LinearLayoutManager(requireContext())
                    val adapter = EventoAdapter(events, object : EventoAdapter.OnItemClickListener {
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
                }
            }
            catch (e: Exception){
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error al mostrar los eventos", Toast.LENGTH_SHORT).show() //guardar texto
                }
            }
        }

        btnCrear.setOnClickListener {
            val crearEventos = CrearEventosFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("userlogin", user)
                }
            }

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