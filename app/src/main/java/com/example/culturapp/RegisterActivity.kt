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

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val txtNombre: EditText = findViewById(R.id.txtNombre)
        val txtApellidos: EditText = findViewById(R.id.txtApellidos)
        val txtCorreo: EditText = findViewById(R.id.txtCorreo)
        val txtContra: EditText = findViewById(R.id.txtContra)
        val txtConfirmar: EditText = findViewById(R.id.txtConfirmar)
        val btnIniciar: Button = findViewById(R.id.btnIniciar)
        val lblInicio: TextView = findViewById(R.id.lblInicio)

        lblInicio.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}