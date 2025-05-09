package com.example.culturapp.activities

import Usuario
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

class AjustesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajustes)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN

        val lblTitulo: TextView = findViewById(R.id.lblTitulo)
        val imgAjustes: ImageView = findViewById(R.id.imgAjustes)
        val lblAjustes: TextView = findViewById(R.id.lblAjustes)
        val evento: LinearLayout = findViewById(R.id.evento)
        val chat: LinearLayout = findViewById(R.id.chat)
        val reserva: LinearLayout = findViewById(R.id.reserva)
        val seleccionado = ContextCompat.getColor(this, R.color.morado)

        lblTitulo.text = getString(R.string.ajustes)
        imgAjustes.setImageResource(R.drawable.ajustes_seleccionado)
        lblAjustes.setTextColor(seleccionado)

        val usuario = intent.getSerializableExtra("userlogin") as? Usuario

        evento.setOnClickListener{
            val intent = Intent(this, EventosActivity::class.java).apply {
                putExtra("userlogin", usuario)
            }
            startActivity(intent)
        }

        chat.setOnClickListener{
            val intent = Intent(this, ChatActivity::class.java).apply {
                putExtra("userlogin", usuario)
            }
            startActivity(intent)
        }

        reserva.setOnClickListener{
            val intent = Intent(this, ReservasActivity::class.java).apply {
                putExtra("userlogin", usuario)
            }
            startActivity(intent)
        }
    }
}