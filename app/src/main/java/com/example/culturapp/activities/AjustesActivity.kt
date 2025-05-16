package com.example.culturapp.activities

import Users
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.example.culturapp.R
import java.util.Locale

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

        val users = intent.getSerializableExtra("userlogin") as? Users

        val lblNombre: TextView = findViewById(R.id.lblNombre)
        val spIdioma: Spinner = findViewById(R.id.spIdioma)
        val swModo: Switch = findViewById(R.id.swModo)
        val txtNombre: EditText = findViewById(R.id.txtNombre)
        val txtApellidos: EditText = findViewById(R.id.txtApellidos)
        val txtCorreo: EditText = findViewById(R.id.txtCorreo)
        val btnCambiar: AppCompatButton = findViewById(R.id.btnCambiar)
        val txtContra: EditText = findViewById(R.id.txtContra)
        val txtConfirmar: EditText = findViewById(R.id.txtConfirmar)
        val btnContra: AppCompatButton = findViewById(R.id.btnContra)
        val btnCerrar: AppCompatButton = findViewById(R.id.btnCerrar)

        val languages = resources.getStringArray(R.array.languages)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spIdioma.adapter = adapter

        spIdioma.onItemSelectedListener = null
        spIdioma.setSelection(0)

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

        btnCerrar.setOnClickListener {
            val intent = Intent(this@AjustesActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        evento.setOnClickListener{
            val intent = Intent(this, EventosActivity::class.java).apply {
                putExtra("userlogin", users)
            }
            startActivity(intent)
        }

        chat.setOnClickListener{
            val intent = Intent(this, ChatActivity::class.java).apply {
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
    }

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