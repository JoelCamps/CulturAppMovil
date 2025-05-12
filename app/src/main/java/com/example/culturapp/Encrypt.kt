package com.example.culturapp

import java.security.MessageDigest

class Encrypt {
    fun encriptar(password: String): String {
        val bytes = MessageDigest
            .getInstance("SHA-256")
            .digest(password.toByteArray())

        return bytes.joinToString("") { "%02x".format(it) }
    }
}