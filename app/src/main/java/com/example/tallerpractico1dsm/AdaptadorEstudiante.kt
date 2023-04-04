package com.example.tallerpractico1dsm

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.tallerpractico1dsm.datos.Estudiante

class AdaptadorEstudiante(private val context: Activity, var estudiantes: List<Estudiante>) :

    ArrayAdapter<Estudiante?>(context, R.layout.estudiante_layout, estudiantes) {
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        // Método invocado tantas veces como elementos tenga la coleccion personas
        // para formar a cada item que se visualizara en la lista personalizada
        val layoutInflater = context.layoutInflater
        var rowview: View? = null
        // optimizando las diversas llamadas que se realizan a este método
        // pues a partir de la segunda llamada el objeto view ya viene formado
        // y no sera necesario hacer el proceso de "inflado" que conlleva tiempo y
        // desgaste de bateria del dispositivo
        rowview = view ?: layoutInflater.inflate(R.layout.estudiante_layout, null)
        val tvNombre = rowview!!.findViewById<TextView>(R.id.tvNombre)
        val tvPromedio = rowview.findViewById<TextView>(R.id.tvPromedio)
        tvNombre.text = "Nombre : " + estudiantes[position].nombre
        tvPromedio.text = "Promedio : " + estudiantes[position].promedio
        return rowview
    }
}

