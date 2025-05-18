package com.example.culturapp.activities

import Users
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.culturapp.ChatSocket
import com.example.culturapp.R
import com.example.culturapp.adapters.ChatAdapter
import com.example.culturapp.clases.Messages
import kotlinx.coroutines.launch

class ChatActivity : AppCompatActivity() {
    private val client = ChatSocket("10.0.2.2", 6400)
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // Configura pantalla fullscreen y oculta navegación
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN

        // Inicializa vistas principales y selecciona sección activa
        val lblTitulo: TextView = findViewById(R.id.lblTitulo)
        val imgChat: ImageView = findViewById(R.id.imgChat)
        val lblChat: TextView = findViewById(R.id.lblChat)
        val evento: LinearLayout = findViewById(R.id.evento)
        val reserva: LinearLayout = findViewById(R.id.reserva)
        val ajustes: LinearLayout = findViewById(R.id.ajustes)
        val seleccionado = ContextCompat.getColor(this, R.color.morado)
        val txtMensaje: EditText = findViewById(R.id.txtMensaje)
        val btnEnviar: ImageButton = findViewById(R.id.btnEnviar)

        lblTitulo.text = getString(R.string.chat)
        imgChat.setImageResource(R.drawable.chat_seleccionado)
        lblChat.setTextColor(seleccionado)

        // Configura RecyclerView para mostrar mensajes en orden
        val rvMensajes = findViewById<RecyclerView>(R.id.rvMensajes)
        chatAdapter = ChatAdapter(mutableListOf())
        rvMensajes.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }
        rvMensajes.adapter = chatAdapter

        val users = intent.getSerializableExtra("userlogin") as? Users

        // Establece conexión con el servidor y escucha mensajes entrantes
        lifecycleScope.launch {
            client.connect()
            launch {
                client.receiveMessages { msg ->
                    runOnUiThread {
                        chatAdapter.addMessage(Messages(msg))
                        rvMensajes.scrollToPosition(chatAdapter.itemCount - 1)
                    }
                }
            }
        }

        // Envía mensaje al servidor
        btnEnviar.setOnClickListener {
            val text = txtMensaje.text.trim().toString()

            if (text.isNotEmpty()) {
                lifecycleScope.launch {
                    if (users != null) {
                        users.id?.let { client.sendMessage(it, text) }
                    }
                }
                txtMensaje.text = null
            }
        }

        // Navegación a otras actividades
        evento.setOnClickListener {
            startActivity(Intent(this, EventosActivity::class.java).apply { putExtra("userlogin", users) })
        }
        reserva.setOnClickListener {
            startActivity(Intent(this, ReservasActivity::class.java).apply { putExtra("userlogin", users) })
        }
        ajustes.setOnClickListener {
            startActivity(Intent(this, AjustesActivity::class.java).apply { putExtra("userlogin", users) })
        }
    }

    // Desconecta el cliente al destruir la actividad
    override fun onDestroy() {
        super.onDestroy()
        client.disconnect()
    }
}