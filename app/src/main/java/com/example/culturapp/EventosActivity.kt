package com.example.culturapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class EventosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN

        val lblTitulo: TextView = findViewById(R.id.lblTitulo)
        val imgEvento: ImageView = findViewById(R.id.imgEvento)
        val lblEvento: TextView = findViewById(R.id.lblEvento)
        val chat: LinearLayout = findViewById(R.id.chat)
        val reserva: LinearLayout = findViewById(R.id.reserva)
        val ajustes: LinearLayout = findViewById(R.id.ajustes)
        val seleccionado = ContextCompat.getColor(this, R.color.morado)

        lblTitulo.text = getString(R.string.eventos)
        imgEvento.setImageResource(R.drawable.lupa_seleccionada)
        lblEvento.setTextColor(seleccionado)

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