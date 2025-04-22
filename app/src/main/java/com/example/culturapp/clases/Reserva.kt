package com.example.culturapp.clases

data class Reserva (
    val usuario_id: Int,
    val evento_id: Int,
    val cantidad: Int,
    val activo: Boolean
)