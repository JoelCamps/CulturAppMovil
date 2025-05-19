package com.example.culturapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.Socket

class ChatSocket (private val host: String, private val port: Int) {
    private var socket: Socket? = null
    private var writer: PrintWriter? = null
    private var reader: BufferedReader? = null

    // Establece la conexión con el servidor
    suspend fun connect() = withContext(Dispatchers.IO) {
        socket = Socket(host, port)
        writer = PrintWriter(OutputStreamWriter(socket!!.getOutputStream()), true)
        reader = BufferedReader(InputStreamReader(socket!!.getInputStream()))
    }

    // Envía un mensaje al servidor en el formato "userId:mensaje".
    suspend fun sendMessage(userId: Int, message: String) = withContext(Dispatchers.IO) {
        val payload = "${userId}:${message}"
        writer?.println(payload)
    }

    // Recibe mensajes del servidor de forma continua.
    suspend fun receiveMessages(onMessage: (String) -> Unit) = withContext(Dispatchers.IO) {
        var line: String? = ""
        while (socket != null && socket!!.isConnected && reader!!.readLine().also { line = it } != null) {
            onMessage(line!!)
        }
    }

    fun disconnect() {
        reader?.close()
        writer?.close()
        socket?.close()
    }
}