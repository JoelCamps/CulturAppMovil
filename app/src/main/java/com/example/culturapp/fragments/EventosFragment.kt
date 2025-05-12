package com.example.culturapp.fragments

import Users
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.culturapp.R
import com.example.culturapp.adapters.EventoAdapter
import com.example.culturapp.clases.Evento
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
        val btnCrear = view.findViewById<Button>(R.id.btnCrear)
        val rvEvento = view.findViewById<RecyclerView>(R.id.RVEvento)

        if (user.type.equals("organizator")) {
            btnCrear.visibility = VISIBLE
        } else {
            btnCrear.visibility = GONE
        }

        val listaEventos = listOf(
            Evento(1, "Charla Motivacional", Timestamp.valueOf("2025-12-22 00:00:00.000"), 100, "Una charla inspiradora para motivar a los asistentes.", 50, Timestamp.valueOf("2025-12-22 00:00:00.000"), true, 1, 1),
            Evento(2, "Concierto en Vivo", Timestamp.valueOf("2007-07-01 00:00:00.000"), 500, "Un concierto en vivo de música popular.", 100, Timestamp.valueOf("2007-07-01 00:00:00.000"), true, 2, 2),
            Evento(3, "Partido de Fútbol", Timestamp.valueOf("2021-09-17 00:00:00.000"), 300, "Un emocionante partido de fútbol entre dos grandes equipos.", 40, Timestamp.valueOf("2021-09-17 00:00:00.000"), true, 3, 3),
            Evento(4, "Torneo de eSports", Timestamp.valueOf("2033-02-13 00:00:00.000"), 200, "Un torneo de eSports donde los mejores jugadores competirán.", 30, Timestamp.valueOf("2033-02-13 00:00:00.000"), true, 4, 4)
                                 )

        rvEvento.layoutManager = LinearLayoutManager(requireContext())
        rvEvento.adapter = EventoAdapter(listaEventos)

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