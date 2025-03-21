package com.example.culturapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.culturapp.R

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN

        val lblTitulo: TextView = findViewById(R.id.lblTitulo)
        val imgChat: ImageView = findViewById(R.id.imgChat)
        val lblChat: TextView = findViewById(R.id.lblChat)
        val evento: LinearLayout = findViewById(R.id.evento)
        val reserva: LinearLayout = findViewById(R.id.reserva)
        val ajustes: LinearLayout = findViewById(R.id.ajustes)
        val seleccionado = ContextCompat.getColor(this, R.color.morado)

        lblTitulo.text = getString(R.string.chat)
        imgChat.setImageResource(R.drawable.chat_seleccionado)
        lblChat.setTextColor(seleccionado)

        evento.setOnClickListener{
            val intent = Intent(this, EventosActivity::class.java)
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