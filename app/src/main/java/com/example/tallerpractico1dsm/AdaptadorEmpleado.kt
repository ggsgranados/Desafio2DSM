package com.example.tallerpractico1dsm

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.tallerpractico1dsm.datos.Empleado

class AdaptadorEmpleado (private val context: Activity, var empleados: List<Empleado>) :

    ArrayAdapter<Empleado?>(context, R.layout.empleado_layout, empleados) {
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        // Método invocado tantas veces como elementos tenga la coleccion personas
        // para formar a cada item que se visualizara en la lista personalizada
        val layoutInflater = context.layoutInflater
        var rowview: View? = null
        // optimizando las diversas llamadas que se realizan a este método
        // pues a partir de la segunda llamada el objeto view ya viene formado
        // y no sera necesario hacer el proceso de "inflado" que conlleva tiempo y
        // desgaste de bateria del dispositivo
        rowview = view ?: layoutInflater.inflate(R.layout.empleado_layout, null)
        val tvNombre = rowview!!.findViewById<TextView>(R.id.tvNombre)
        val tvSalario = rowview.findViewById<TextView>(R.id.tvSalario)
        tvNombre.text = "Nombre : " + empleados[position].nombre
        tvSalario.text = "Salario : $" + empleados[position].salario
        return rowview
    }
}
