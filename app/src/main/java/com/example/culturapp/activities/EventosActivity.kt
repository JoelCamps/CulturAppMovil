package com.example.culturapp.activities

import Usuario
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.culturapp.R
import com.example.culturapp.adapters.EventoAdapter
import com.example.culturapp.clases.Evento
import java.sql.Timestamp

class EventosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN

        val btnCrear: AppCompatButton = findViewById(R.id.btnCrear)
        val lblTitulo: TextView = findViewById(R.id.lblTitulo)
        val imgEvento: ImageView = findViewById(R.id.imgEvento)
        val lblEvento: TextView = findViewById(R.id.lblEvento)
        val chat: LinearLayout = findViewById(R.id.chat)
        val reserva: LinearLayout = findViewById(R.id.reserva)
        val ajustes: LinearLayout = findViewById(R.id.ajustes)
        val rvEvento: RecyclerView = findViewById(R.id.RVEvento)
        val seleccionado = ContextCompat.getColor(this, R.color.morado)

        lblTitulo.text = getString(R.string.eventos)
        imgEvento.setImageResource(R.drawable.lupa_seleccionada)
        lblEvento.setTextColor(seleccionado)

        val usuario = intent.getSerializableExtra("userlogin") as? Usuario
        if (usuario?.tipo?.value == Usuario.Tipo.ORGANIZADOR.value) {
            btnCrear.visibility = VISIBLE
        }

        val listaEventos = listOf(
            Evento(1, "Charla Motivacional", Timestamp.valueOf("2025-12-22 00:00:00.000"), 100, "Una charla inspiradora para motivar a los asistentes.", 50, Timestamp.valueOf("2025-12-22 00:00:00.000"), true, 1, 1),
            Evento(2, "Concierto en Vivo", Timestamp.valueOf("2007-07-01 00:00:00.000"), 500, "Un concierto en vivo de música popular.", 100, Timestamp.valueOf("2007-07-01 00:00:00.000"), true, 2, 2),
            Evento(3, "Partido de Fútbol", Timestamp.valueOf("2021-09-17 00:00:00.000"), 300, "Un emocionante partido de fútbol entre dos grandes equipos.", 40, Timestamp.valueOf("2021-09-17 00:00:00.000"), true, 3, 3),
            Evento(4, "Torneo de eSports", Timestamp.valueOf("2033-02-13 00:00:00.000"), 200, "Un torneo de eSports donde los mejores jugadores competirán.", 30, Timestamp.valueOf("2033-02-13 00:00:00.000"), true, 4, 4)
                                 )

        rvEvento.layoutManager = LinearLayoutManager(this)
        rvEvento.adapter = EventoAdapter(listaEventos)
        //neceito la api

        chat.setOnClickListener{
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }

        reserva.setOnClickListener{
            val intent = Intent(this, ReservasActivity::class.java)
            startActivity(intent)
        }

        ajustes.setOnClickListener{
            val intent = Intent(this, AjustesActivity::class.java)
            startActivity(intent)
        }
    }
}