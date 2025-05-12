package com.example.culturapp.activities

import Users
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.culturapp.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN

        val txtNombre: EditText = findViewById(R.id.txtNombre)
        val txtApellidos: EditText = findViewById(R.id.txtApellidos)
        val txtCorreo: EditText = findViewById(R.id.txtCorreo)
        val txtContra: EditText = findViewById(R.id.txtContra)
        val txtConfirmar: EditText = findViewById(R.id.txtConfirmar)
        val btnIniciar: Button = findViewById(R.id.btnIniciar)
        val lblInicio: TextView = findViewById(R.id.lblInicio)

        btnIniciar.setOnClickListener{
            if (txtNombre.text.isEmpty() || txtApellidos.text.isEmpty() || txtCorreo.text.isEmpty() ||
                txtContra.text.isEmpty() || txtConfirmar.text.isEmpty()) {
                Toast.makeText(this, "Debes completar todos los campos", Toast.LENGTH_SHORT).show() //guardar texto
            }
//            else if (users?.any { it.email == txtCorreo.text.toString() } == true) {
//                Toast.makeText(this, "Este usuario ya existe", Toast.LENGTH_SHORT).show()
//            }
            else {
                if (txtContra.text == txtConfirmar.text) {
                    val intent = Intent(this, EventosActivity::class.java)
                    startActivity(intent)
                }
                else {
                    Toast.makeText(this, "La contraseña no coincide", Toast.LENGTH_SHORT).show()
                }
            }
        }

        lblInicio.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}