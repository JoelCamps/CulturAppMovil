package com.example.culturapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.culturapp.Encrypt
import com.example.culturapp.R
import com.example.culturapp.api.calls.UsersCall
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContraActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contra)

        // Configura pantalla fullscreen y oculta navegación
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN

        // Inicializa vistas principales
        val txtCorreo: EditText = findViewById(R.id.txtCorreo)
        val txtContra: EditText = findViewById(R.id.txtNueva)
        val txtConfirmar: EditText = findViewById(R.id.txtConfirmar)
        val btnCambiar: Button = findViewById(R.id.btnCambiar)
        val lblInicio: TextView = findViewById(R.id.lblInicio)
        val bar: ProgressBar = findViewById(R.id.bar)

        // Al pulsar el botón cambiar contraseña
        btnCambiar.setOnClickListener {
            if (txtCorreo.text.isEmpty() && txtContra.text.isEmpty() && txtConfirmar.text.isEmpty()){
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                if (txtContra.text.toString() == txtConfirmar.text.toString()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val password = Encrypt().encriptar(txtContra.text.trim().toString())
                            UsersCall().putUsersPassword(txtCorreo.text.toString(), password)

                            withContext(Dispatchers.Main) {
                                // Muestra barra de progreso y oculta botón
                                btnCambiar.visibility = View.GONE
                                bar.visibility = View.VISIBLE

                                // Navega al login tras cambiar la contraseña
                                val intent = Intent(this@ContraActivity, LoginActivity::class.java)
                                startActivity(intent)
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                Log.e("ERROR", "Excepción en PUT", e)
                                Toast.makeText(this@ContraActivity, "Error al cambiar la contraseña", Toast.LENGTH_SHORT).show()

                                // Oculta barra y muestra botón de nuevo
                                bar.visibility = View.GONE
                                btnCambiar.visibility = View.VISIBLE
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, "La contraseña no coincide", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Navegar a pantalla de login
        lblInicio.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}