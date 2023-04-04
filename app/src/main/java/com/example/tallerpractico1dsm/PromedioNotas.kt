package com.example.tallerpractico1dsm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.tallerpractico1dsm.EstudiantesActivity.Companion.database
import com.example.tallerpractico1dsm.datos.Estudiante
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PromedioNotas : AppCompatActivity() {

    //Variables globales
    private lateinit var  database: DatabaseReference
    private var nombre = ""
    private var promedio = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promedio_notas)

        val nombre = findViewById<EditText>(R.id.txtEstudiante)
        val nota1 = findViewById<EditText>(R.id.txtNota1)
        val nota2 = findViewById<EditText>(R.id.txtNota2)
        val nota3 = findViewById<EditText>(R.id.txtNota3)
        val nota4 = findViewById<EditText>(R.id.txtNota4)
        val nota5 = findViewById<EditText>(R.id.txtNota5)
        val button = findViewById<Button>(R.id.btnPromedio)
        val tvPromedio = findViewById<TextView>(R.id.tvPromedio)
        val tvResultado = findViewById<TextView>(R.id.tvResultado)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        var auxPromedio: Double

        button.setOnClickListener {
            try {
                if (nota1.text.toString().toDouble() in 0.0..10.0 &&
                    nota2.text.toString().toDouble() in 0.0..10.0 &&
                    nota3.text.toString().toDouble() in 0.0..10.0 &&
                    nota4.text.toString().toDouble() in 0.0..10.0 &&
                    nota5.text.toString().toDouble() in 0.0..10.0 &&
                    !nombre.text.isNullOrBlank()
                ) {
                    //Haciendo visible el boton para guardar en Firebase
                    btnGuardar.setVisibility(View.VISIBLE)
                    auxPromedio = (nota1.text.toString().toDouble() +
                            nota2.text.toString().toDouble() +
                            nota3.text.toString().toDouble() +
                            nota4.text.toString().toDouble() +
                            nota5.text.toString().toDouble()) / 5

                    tvPromedio.text =
                        "Nombre: ${nombre.text}\n" +
                                "Promedio: ${String.format("%.2f", auxPromedio)}\n"

                    //Almacenando los datos en las variables para luego guardarlas en Firebase
                    this.nombre = nombre.text.toString()
                    this.promedio = String.format("%.2f", auxPromedio)

                    if (auxPromedio >= 6.0)
                        tvResultado.text = "Ha aprobado la asignatura"
                    else
                        tvResultado.text = "Ha reprobado la asignatura"
                } else
                    Toast.makeText(this, "Los campos son incorrectos o nulos", Toast.LENGTH_LONG)
                        .show()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Los campos no pueden estar vacíos", Toast.LENGTH_LONG).show()
            }

        }




    }

    //Funcion que permite guardar los datos calculados en Firebase
    fun guardar(v: View?){
        database= FirebaseDatabase.getInstance().getReference("estudiantes")

        // Se forma objeto persona
        val estudiante = Estudiante(nombre, promedio)


            database.child(nombre).setValue(estudiante).addOnSuccessListener {
                Toast.makeText(this,"Se guardo con exito", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(this,"Failed ", Toast.LENGTH_SHORT).show()
        }
        finish()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menuopciones, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.opcion1) {
            Toast.makeText(this, "Se seleccionó la primer opción", Toast.LENGTH_LONG).show()
            val intent = Intent(this, EstudiantesActivity::class.java)
            startActivity(intent)
        }
        if (id == R.id.opcion2) {
            Toast.makeText(this, "Se seleccionó la segunda opción", Toast.LENGTH_LONG).show()
            val intent = Intent(this, EmpleadosActivity::class.java)
            startActivity(intent)
        }

        /* Se ha deshabilitado la opcion de la calculadora

        if (id == R.id.opcion3) {
            Toast.makeText(this, "Se seleccionó la tercer opción", Toast.LENGTH_LONG).show()
            val intent = Intent(this, Calculadora::class.java)
            startActivity(intent)
        }*/

        if (id == R.id.sign_out) {
            FirebaseAuth.getInstance().signOut().also {
                Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_LONG).show()

                val intent = Intent(this,Registro::class.java)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}