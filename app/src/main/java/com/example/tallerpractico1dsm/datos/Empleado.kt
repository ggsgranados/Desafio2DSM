package com.example.tallerpractico1dsm.datos

class Empleado {

    fun key(key: String?) {
    }

    var salario: String? = null
    var nombre: String? = null
    var key: String? = null
    var per: MutableMap<String, Boolean> = HashMap()

    constructor() {}
    constructor(nombre: String?, salario: String?) {
        this.salario = salario
        this.nombre = nombre
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "salario" to salario,
            "nombre" to nombre,
            "key" to key,
            "per" to per
        )
    }
}