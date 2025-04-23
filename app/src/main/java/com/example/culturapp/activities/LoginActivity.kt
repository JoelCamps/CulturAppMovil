package com.example.culturapp.activities

import Usuario
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.culturapp.R
import java.util.Locale

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN

        val txtCorreo: EditText = findViewById(R.id.txtCorreo)
        val txtContra: EditText = findViewById(R.id.txtContra)
        val spIdioma: Spinner = findViewById(R.id.spIdioma)
        val lblContra: TextView = findViewById(R.id.lblContra)
        val lblRegister: TextView = findViewById(R.id.lblRegister)
        val btnIniciar: Button = findViewById(R.id.btnIniciar)

        val listaUsuarios = listOf(
            Usuario(1,"Bob","Johnson","bob.johnson@example.com","1234",Usuario.Tipo.ORGANIZADOR,true),
            Usuario(2,"Charlie","Brown","charlie.brown@example.com","1234",Usuario.Tipo.BASICO,true)
        )

        lblContra.setOnClickListener {
            val intent = Intent(this, ContraActivity::class.java)
            startActivity(intent)
        }

        btnIniciar.setOnClickListener {
            val usuario = Usuario(1,"Bob","Johnson","bob.johnson@example.com","1234",Usuario.Tipo.ORGANIZADOR, true)

            val intent = Intent(this, EventosActivity::class.java).apply {
                putExtra("userlogin", usuario)
            }
            startActivity(intent)

//            if (listaUsuarios.find { it.email == txtCorreo.text.toString() } != null && listaUsuarios.find
//            { it.contra == txtContra.text.toString() } != null){
//                val usuario = Usuario(1,"Bob","Johnson","bob.johnson@example.com","1234",Usuario.Tipo.BASICO, true)
//
//                val intent = Intent(this, EventosActivity::class.java).apply {
//                    putExtra("userlogin", usuario)
//                }
//                startActivity(intent)
//            }
//            else if (txtCorreo.text.isEmpty() || txtContra.text.isEmpty()) {
//                Toast.makeText(this, "Debes poner un correo y una contraseña", Toast.LENGTH_SHORT).show() //guardar texto
//            }
//            else {
//                Toast.makeText(this, "Ese usuario no existe", Toast.LENGTH_SHORT).show() //guardar texto
//            }
        }

        lblRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java).apply {
                putExtra("usuarios_lista", ArrayList(listaUsuarios))
            }
            startActivity(intent)
        }

        val languages = resources.getStringArray(R.array.languages)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spIdioma.adapter = adapter

        spIdioma.onItemSelectedListener = null
        spIdioma.setSelection(0)

        spIdioma.post {
            spIdioma.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parentView: AdapterView<*>, view: View?, position: Int, id: Long) {
                    val selectedLanguage = when (position) {
                        0 -> "es"
                        1 -> "en"
                        2 -> "ca"
                        3 -> "zh"
                        4 -> "ja"
                        5 -> "it"
                        6 -> "pt"
                        7 -> "de"
                        8 -> "gl"
                        9 -> "ru"
                        10 -> "lt"
                        11 -> "tr"
                        else -> "es"
                    }
                    val sharedPreferences = getSharedPreferences("user_preferences", MODE_PRIVATE)
                    sharedPreferences.edit().putString("language", selectedLanguage).apply()

                    setLocale(selectedLanguage)
                }

                override fun onNothingSelected(parentView: AdapterView<*>) {
                }
            }
        }
    }

    private fun setLocale(idioma: String) {
        val locale = Locale(idioma)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        recreate()
    }
}