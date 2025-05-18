package com.example.culturapp.activities

import Users
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
import com.example.culturapp.fragments.EventosFragment

class EventosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos)

        // Configura pantalla fullscreen y oculta navegación
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN

        // Inicializa vistas principales y selecciona sección activa
        val lblTitulo: TextView = findViewById(R.id.lblTitulo)
        val imgEvento: ImageView = findViewById(R.id.imgEvento)
        val lblEvento: TextView = findViewById(R.id.lblEvento)
        val evento: LinearLayout = findViewById(R.id.evento)
        val chat: LinearLayout = findViewById(R.id.chat)
        val reserva: LinearLayout = findViewById(R.id.reserva)
        val ajustes: LinearLayout = findViewById(R.id.ajustes)
        val seleccionado = ContextCompat.getColor(this, R.color.morado)

        lblTitulo.text = getString(R.string.eventos)
        imgEvento.setImageResource(R.drawable.lupa_seleccionada)
        lblEvento.setTextColor(seleccionado)

        val users = intent.getSerializableExtra("userlogin") as? Users

        // Inserta el fragmento de eventos con el usuario actual
        val fragment = users?.let { EventosFragment.newInstance(it) }
        if (fragment != null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        }

        // Recarga fragmento eventos
        evento.setOnClickListener {
            if (fragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
            }
        }

        // Navegación a otras actividades
        chat.setOnClickListener {
            startActivity(Intent(this, ChatActivity::class.java).apply { putExtra("userlogin", users) })
        }
        reserva.setOnClickListener {
            startActivity(Intent(this, ReservasActivity::class.java).apply { putExtra("userlogin", users) })
        }
        ajustes.setOnClickListener {
            startActivity(Intent(this, AjustesActivity::class.java).apply { putExtra("userlogin", users) })
        }
    }
}