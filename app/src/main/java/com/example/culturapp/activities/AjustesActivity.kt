package com.example.culturapp.activities

import Users
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.example.culturapp.Encrypt
import com.example.culturapp.R
import com.example.culturapp.api.calls.UsersCall
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class AjustesActivity : AppCompatActivity() {
    private var user: Users? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = savedInstanceState?.getSerializable("userlogin") as? Users
            ?: intent.getSerializableExtra("userlogin") as? Users

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

        lblNombre.text = user?.name

        val adapter = ArrayAdapter.createFromResource(this, R.array.languages, R.layout.item_spinner)
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

                    setLocale(selectedLanguage, user)
                }

                override fun onNothingSelected(parentView: AdapterView<*>) {}
            }
        }

        val sharedPreferences = getSharedPreferences("user_preferences", MODE_PRIVATE)
        val modoOscuro = sharedPreferences.getBoolean("modo_oscuro", false)
        swModo.isChecked = modoOscuro

        swModo.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            sharedPreferences.edit().putBoolean("modo_oscuro", isChecked).apply()
        }

        btnCambiar.setOnClickListener {
            if (txtNombre.text.isEmpty() && txtApellidos.text.isEmpty() && txtCorreo.text.isEmpty()){
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show() //guardar texto
            }
            else{
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        user?.name = txtNombre.text.trim().toString()
                        user?.surname = txtApellidos.text.trim().toString()
                        user?.email = txtCorreo.text.trim().toString()

                        user?.id?.let { it1 -> UsersCall().putUser(it1, user!!) }

                        withContext(Dispatchers.Main) {
                            val intent = Intent(this@AjustesActivity, AjustesActivity::class.java).apply {
                                putExtra("userlogin", user)
                            }
                            startActivity(intent)
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Log.e("ERROR", "Excepción en PUT", e)
                            Toast.makeText(this@AjustesActivity, "Error al cambiar los nuevos datos", Toast.LENGTH_SHORT).show() //guardar texto
                        }
                    }
                }
            }
        }

        btnContra.setOnClickListener {
            if (txtContra.text.isEmpty() && txtConfirmar.text.isEmpty()){
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show() //guardar texto
            }
            else{
                if (txtContra.text.toString() == txtConfirmar.text.toString()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val password = Encrypt().encriptar(txtContra.text.trim().toString())
                            user?.email?.let { it1 -> UsersCall().putUsersPassword(it1, password) }

                            txtContra.text = null
                            txtConfirmar.text = null
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                Log.e("ERROR", "Excepción en PUT", e)
                                Toast.makeText(this@AjustesActivity, "Error al cambiar la contraseña", Toast.LENGTH_SHORT).show() //guardar texto
                            }
                        }
                    }
                }
                else{
                    Toast.makeText(this, "La contraseña no coincide", Toast.LENGTH_SHORT).show() //guardar texto
                }
            }
        }

        btnCerrar.setOnClickListener {
            val intent = Intent(this@AjustesActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        evento.setOnClickListener{
            val intent = Intent(this, EventosActivity::class.java).apply {
                putExtra("userlogin", user)
            }
            startActivity(intent)
        }

        chat.setOnClickListener{
            val intent = Intent(this, ChatActivity::class.java).apply {
                putExtra("userlogin", user)
            }
            startActivity(intent)
        }

        reserva.setOnClickListener{
            val intent = Intent(this, ReservasActivity::class.java).apply {
                putExtra("userlogin", user)
            }
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("userlogin", user)
    }

    private fun setLocale(idioma: String, user: Users?) {
        val locale = Locale(idioma)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val intent = Intent(this, this::class.java).apply {
            putExtra("userlogin", user)
        }
        startActivity(intent)
    }
}