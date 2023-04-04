package com.example.tallerpractico1dsm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.tallerpractico1dsm.datos.Estudiante
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddEstudianteActivity : AppCompatActivity() {

    private var edtPromedio: EditText? = null
    private var edtNombre: EditText? = null
    private var key = ""
    private var nombre = ""
    private var promedio = ""
    private var accion = ""
    private lateinit var  database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_estudiante)
        inicializar()
    }

    private fun inicializar() {
        edtNombre = findViewById<EditText>(R.id.edtNombre)
        edtPromedio = findViewById<EditText>(R.id.edtPromedio)

        val edtNombre = findViewById<EditText>(R.id.edtNombre)
        val edtPromedio = findViewById<EditText>(R.id.edtPromedio)

        // Obtención de datos que envia actividad anterior
        val datos: Bundle? = intent.getExtras()
        if (datos != null) {
            key = datos.getString("key").toString()
        }
        if (datos != null) {
            edtNombre.setText(intent.getStringExtra("nombre").toString())
        }
        if (datos != null) {
            edtPromedio.setText(intent.getStringExtra("promedio").toString())
        }
        if (datos != null) {
            accion = datos.getString("accion").toString()
        }

    }

    fun guardar(v: View?) {
        val nombre: String = edtNombre?.text.toString()
        val promedio: String = edtPromedio?.text.toString()

        database= FirebaseDatabase.getInstance().getReference("estudiantes")

        // Se forma objeto persona
        val estudiante = Estudiante(nombre, promedio)

        if (accion == "a") { //Agregar registro
            database.child(nombre).setValue(estudiante).addOnSuccessListener {
                Toast.makeText(this,"Se guardo con exito", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this,"Failed ", Toast.LENGTH_SHORT).show()
            }
        } else  // Editar registro
        {
            val key = database.child("nombre").push().key
            if (key == null) {
                Toast.makeText(this,"Llave vacia", Toast.LENGTH_SHORT).show()
            }
            val estudiantesValues = estudiante.toMap()
            val childUpdates = hashMapOf<String, Any>(
                "$nombre" to estudiantesValues
            )
            database.updateChildren(childUpdates)
            Toast.makeText(this,"Se actualizo con exito", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    fun cancelar(v: View?) {
        finish()
    }

}