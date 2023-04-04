package com.example.tallerpractico1dsm.datos

class Estudiante {

    fun key(key: String?) {
    }

    var nombre: String? = null
    var promedio: String? = null
    var key: String? = null
    var per: MutableMap<String, Boolean> = HashMap()

    constructor() {}
    constructor(nombre: String?, promedio: String?) {
        this.promedio = promedio
        this.nombre = nombre
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "nombre" to nombre,
            "promedio" to promedio,
            "key" to key,
            "per" to per
        )
    }
}