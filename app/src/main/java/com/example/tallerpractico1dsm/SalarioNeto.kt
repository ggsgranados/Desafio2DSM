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
import com.example.tallerpractico1dsm.datos.Empleado
import com.example.tallerpractico1dsm.datos.Estudiante
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SalarioNeto : AppCompatActivity() {

    //Variables globales
    private lateinit var  database: DatabaseReference
    private var nombre = ""
    private var salario = ""
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salario_neto)

        val nombre = findViewById<EditText>(R.id.txtNombre)
        val salario = findViewById<EditText>(R.id.txtSalario)
        val button = findViewById<Button>(R.id.btnCalcular)
        val tvResultado = findViewById<TextView>(R.id.textViewResultado)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        button.setOnClickListener {
            try {
                if (salario.text.toString().toDouble() > 0.0 && !nombre.text.isNullOrBlank()) {
                    tvResultado.text =
                        "Nombre: ${nombre.text} \n" +
                                "ISSS: \$${
                                    String.format(
                                        "%.2f",
                                        salario.text.toString().toDouble() * 0.03
                                    )
                                }\n" +
                                "AFP: \$${
                                    String.format(
                                        "%.2f",
                                        salario.text.toString().toDouble() * 0.04
                                    )
                                }\n" +
                                "Renta: \$${
                                    String.format(
                                        "%.2f",
                                        salario.text.toString().toDouble() * 0.05
                                    )
                                }\n" +
                                "Total de descuentos: \$${
                                    String.format(
                                        "%.2f",
                                        salario.text.toString().toDouble() * 0.12
                                    )
                                }\n" +
                                "SALARIO NETO: \$${
                                    String.format(
                                        "%.2f",
                                        salario.text.toString().toDouble() * 0.88
                                    )
                                }"

                    //Haciendo visible el boton para guardar en Firebase
                    btnGuardar.setVisibility(View.VISIBLE)
                    //Almacenando los datos en las variables para luego guardarlas en Firebase
                    this.nombre = nombre.text.toString()
                    this.salario = String.format(
                                        "%.2f",
                                        salario.text.toString().toDouble() * 0.88
                                    )
                    
                } else
                    Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_LONG).show()

            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Los campos no pueden estar vacíos", Toast.LENGTH_LONG).show()
            }

        }

    }


    //Funcion que permite guardar los datos calculados en Firebase
    fun guardar(v: View?){
        database= FirebaseDatabase.getInstance().getReference("empleados")

        // Se forma objeto persona
        val empleado = Empleado(nombre, salario)


        database.child(nombre).setValue(empleado).addOnSuccessListener {
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
        /*
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