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
import com.example.culturapp.fragments.ReservasFragment

class ReservasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservas)

        // Configura pantalla fullscreen y oculta navegación
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN

        // Inicializa vistas principales
        val lblTitulo: TextView = findViewById(R.id.lblTitulo)
        val imgReserva: ImageView = findViewById(R.id.imgReserva)
        val lblReserva: TextView = findViewById(R.id.lblReserva)
        val evento: LinearLayout = findViewById(R.id.evento)
        val chat: LinearLayout = findViewById(R.id.chat)
        val reserva: LinearLayout = findViewById(R.id.reserva)
        val ajustes: LinearLayout = findViewById(R.id.ajustes)
        val seleccionado = ContextCompat.getColor(this, R.color.morado)

        // Marca la pestaña de reservas como seleccionada
        lblTitulo.text = getString(R.string.reservas)
        imgReserva.setImageResource(R.drawable.calendario_seleccionado)
        lblReserva.setTextColor(seleccionado)

        val users = intent.getSerializableExtra("userlogin") as? Users

        // Carga fragmento de reservas con usuario
        val fragment = users?.let { ReservasFragment.newInstance(it) }
        if (fragment != null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        }

        // Recarga fragmento eventos
        reserva.setOnClickListener {
            if (fragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
            }
        }

        // Navegación a otras actividades
        evento.setOnClickListener {
            val intent = Intent(this, EventosActivity::class.java).apply {
                putExtra("userlogin", users)
            }
            startActivity(intent)
        }

        chat.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java).apply {
                putExtra("userlogin", users)
            }
            startActivity(intent)
        }

        ajustes.setOnClickListener {
            val intent = Intent(this, AjustesActivity::class.java).apply {
                putExtra("userlogin", users)
            }
            startActivity(intent)
        }
    }
}