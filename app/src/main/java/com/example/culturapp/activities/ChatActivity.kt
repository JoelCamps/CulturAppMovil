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
import com.example.culturapp.ChatSocket
import com.example.culturapp.R
import kotlinx.coroutines.launch

class ChatActivity : AppCompatActivity() {
    private val client = ChatSocket("10.0.2.2", 6400)

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
        val txtMensaje: EditText = findViewById(R.id.txtMensaje)
        val btnEnviar: ImageButton = findViewById(R.id.btnEnviar)

        lblTitulo.text = getString(R.string.chat)
        imgChat.setImageResource(R.drawable.chat_seleccionado)
        lblChat.setTextColor(seleccionado)

        val users = intent.getSerializableExtra("userlogin") as? Users

        lifecycleScope.launch {
            client.connect()
            launch { client.receiveMessages { msg -> runOnUiThread { appendToChat(msg) } } }
        }

        btnEnviar.setOnClickListener {
            val text = txtMensaje.text.toString()
            lifecycleScope.launch {
                if (users != null) {
                    client.sendMessage(users.id, text)
                }
            }

            txtMensaje.text = null
        }

        evento.setOnClickListener{
            val intent = Intent(this, EventosActivity::class.java).apply {
                putExtra("userlogin", users)
            }
            startActivity(intent)
        }

        reserva.setOnClickListener{
            val intent = Intent(this, ReservasActivity::class.java).apply {
                putExtra("userlogin", users)
            }
            startActivity(intent)
        }

        ajustes.setOnClickListener{
            val intent = Intent(this, AjustesActivity::class.java).apply {
                putExtra("userlogin", users)
            }
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        client.disconnect()
    }

    private fun appendToChat(msg: String) {
        val lblMensaje: TextView = findViewById(R.id.lblMensaje)

        lblMensaje.append("${msg}\n")
    }
}