package com.example.culturapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ReservasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservas)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN

        val lblTitulo: TextView = findViewById(R.id.lblTitulo)
        val imgReserva: ImageView = findViewById(R.id.imgReserva)
        val lblReserva: TextView = findViewById(R.id.lblReserva)
        val evento: LinearLayout = findViewById(R.id.evento)
        val chat: LinearLayout = findViewById(R.id.chat)
        val ajustes: LinearLayout = findViewById(R.id.ajustes)
        val seleccionado = ContextCompat.getColor(this, R.color.morado)

        lblTitulo.text = getString(R.string.reservas)
        imgReserva.setImageResource(R.drawable.calendario_seleccionado)
        lblReserva.setTextColor(seleccionado)

        evento.setOnClickListener{
            val intent = Intent(this, EventosActivity::class.java)
            startActivity(intent)
        }

        chat.setOnClickListener{
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }

        ajustes.setOnClickListener{
            val intent = Intent(this, AjustesActivity::class.java)
            startActivity(intent)
        }
    }
}