package com.example.culturapp.clases

import java.sql.Timestamp

data class Evento(
    val id: Int,
    val title: String,
    val fechaInicio: Timestamp,
    val capacidad: Int,
    val descripcion: String,
    val precio: Int,
    val fechaFin: Timestamp,
    val activo: Boolean,
    val sala_id: Int,
    val tipo_id: Int
)