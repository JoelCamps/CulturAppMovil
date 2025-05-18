package com.example.culturapp.activities

import Users
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.culturapp.Encrypt
import com.example.culturapp.R
import com.example.culturapp.api.calls.UsersCall
import kotlinx.coroutines.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Pantalla completa, sin barra de navegación ni estado
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN

        // Inicializa vistas principales
        val txtNombre: EditText = findViewById(R.id.txtNombre)
        val txtApellidos: EditText = findViewById(R.id.txtApellidos)
        val txtCorreo: EditText = findViewById(R.id.txtCorreo)
        val txtContra: EditText = findViewById(R.id.txtContra)
        val txtConfirmar: EditText = findViewById(R.id.txtConfirmar)
        val btnIniciar: Button = findViewById(R.id.btnIniciar)
        val lblInicio: TextView = findViewById(R.id.lblInicio)
        val bar: ProgressBar = findViewById(R.id.bar)

        // Validación de campos, comparación de contraseñas y registro con API
        btnIniciar.setOnClickListener {
            if (txtNombre.text.isEmpty() || txtApellidos.text.isEmpty() || txtCorreo.text.isEmpty() ||
                txtContra.text.isEmpty() || txtConfirmar.text.isEmpty()) {
                Toast.makeText(this, getString(R.string.completa), Toast.LENGTH_SHORT).show()
            } else {
                if (txtContra.text.toString() == txtConfirmar.text.toString()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val password = Encrypt().encriptar(txtContra.text.trim().toString())

                            val user = Users(
                                null,
                                txtNombre.text.trim().toString(),
                                txtApellidos.text.trim().toString(),
                                txtCorreo.text.trim().toString(),
                                password,
                                type = "basic",
                                active = true
                            )

                            UsersCall().postUser(user)

                            withContext(Dispatchers.Main) {
                                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                                startActivity(intent)
                                btnIniciar.visibility = View.GONE
                                bar.visibility = View.VISIBLE
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@RegisterActivity, getString(R.string.errorRegistro), Toast.LENGTH_SHORT).show()
                            }
                            bar.visibility = View.GONE
                            btnIniciar.visibility = View.VISIBLE
                        }
                    }
                } else {
                    Toast.makeText(this, getString(R.string.noCoincide), Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Volver a la pantalla de login
        lblInicio.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}