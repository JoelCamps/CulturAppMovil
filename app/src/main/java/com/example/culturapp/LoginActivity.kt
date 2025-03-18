package com.example.culturapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val txtCorreo: EditText = findViewById(R.id.txtCorreo)
        val txtContra: EditText = findViewById(R.id.txtContra)
        val lblContra: TextView = findViewById(R.id.lblContra)
        val lblRegister: TextView = findViewById(R.id.lblRegister)
        val btnIniciar: Button = findViewById(R.id.btnIniciar)

        lblContra.setOnClickListener{
            val intent = Intent(this, ContraActivity::class.java)
            startActivity(intent)
        }

        btnIniciar.setOnClickListener {
//            val intent = Intent(this, EventosActivity::class.java)
//            startActivity(intent)
        }

        lblRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}