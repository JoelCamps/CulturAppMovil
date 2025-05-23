package com.example.culturapp.activities

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.culturapp.Encrypt
import com.example.culturapp.R
import com.example.culturapp.api.calls.UsersCall
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Recupera la preferencia del modo oscuro
        val sharedPreferences = getSharedPreferences("user_preferences", MODE_PRIVATE)
        val modoOscuro = sharedPreferences.getBoolean("modo_oscuro", false)
        if (modoOscuro) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        setContentView(R.layout.activity_login)

        // Configura pantalla fullscreen y oculta navegación
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN

        // Inicializa vistas principales
        val txtCorreo: EditText = findViewById(R.id.txtCorreo)
        val txtContra: EditText = findViewById(R.id.txtContra)
        val spIdioma: Spinner = findViewById(R.id.spIdioma)
        val lblContra: TextView = findViewById(R.id.lblContra)
        val lblRegister: TextView = findViewById(R.id.lblRegister)
        val btnIniciar: Button = findViewById(R.id.btnIniciar)
        val bar: ProgressBar = findViewById(R.id.bar)

        // Ir a recuperación de contraseña
        lblContra.setOnClickListener {
            val intent = Intent(this, ContraActivity::class.java)
            startActivity(intent)
        }

        // Validación de campos, encriptación y login con API
        btnIniciar.setOnClickListener {
            val password = Encrypt().encriptar(txtContra.text.trim().toString())

            if (txtCorreo.text.isEmpty() || txtContra.text.isEmpty()) {
                Toast.makeText(this, getString(R.string.correoContra), Toast.LENGTH_SHORT).show()
            }
            else {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val user = UsersCall().getUserLogin(txtCorreo.text.trim().toString(), password)

                        withContext(Dispatchers.Main) {
                            val intent = Intent(this@LoginActivity, EventosActivity::class.java).apply {
                                putExtra("userlogin", user)
                            }
                            startActivity(intent)

                            btnIniciar.visibility = View.GONE
                            bar.visibility = View.VISIBLE
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@LoginActivity, getString(R.string.errorLogin), Toast.LENGTH_SHORT).show()
                            txtContra.text = null
                            bar.visibility = View.GONE
                            btnIniciar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        // Ir a pantalla de registro
        lblRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Carga opciones del spinner de idioma
        val adapter = ArrayAdapter.createFromResource(this, R.array.languages, R.layout.item_spinner)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spIdioma.adapter = adapter

        spIdioma.onItemSelectedListener = null
        spIdioma.setSelection(0)

        // Cambia idioma y guarda preferencia
        spIdioma.post {
            spIdioma.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parentView: AdapterView<*>, view: View?, position: Int, id: Long) {
                    val selectedLanguage = parentView.getItemAtPosition(position).toString().lowercase()

                    val sharedPreferences = getSharedPreferences("user_preferences", MODE_PRIVATE)
                    sharedPreferences.edit().putString("language", selectedLanguage).apply()

                    setLocale(selectedLanguage)
                }

                override fun onNothingSelected(parentView: AdapterView<*>) {}
            }
        }
    }

    // Aplica configuración de idioma y reinicia actividad
    private fun setLocale(idioma: String) {
        val locale = Locale(idioma)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val intent = Intent(this, this::class.java)
        startActivity(intent)
    }
}