package com.example.tallerpractico1dsm

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.tallerpractico1dsm.datos.Estudiante
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class EstudiantesActivity : AppCompatActivity() {

    // Ordenamiento para hacer la consultas a los datos
    var consultaOrdenada: Query = refEstudiantes.orderByChild("nombre")
    var estudiantes: MutableList<Estudiante>? = null
    var listaEstudiantes: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estudiantes)
        inicializar()
    }

    private fun inicializar() {
        val fab_agregar: FloatingActionButton = findViewById<FloatingActionButton>(R.id.fab_agregar)
        listaEstudiantes = findViewById<ListView>(R.id.ListaEstudiantes)

        // Cuando el usuario haga clic en la lista (para editar registro)
        listaEstudiantes!!.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                val intent = Intent(getBaseContext(), AddEstudianteActivity::class.java)
                intent.putExtra("accion", "e") // Editar
                intent.putExtra("key", estudiantes!![i].key)
                intent.putExtra("nombre", estudiantes!![i].nombre)
                intent.putExtra("promedio", estudiantes!![i].promedio)
                startActivity(intent)
            }
        })

        // Cuando el usuario hace un LongClic (clic sin soltar elemento por mas de 2 segundos)
        // Es por que el usuario quiere eliminar el registro
        listaEstudiantes!!.onItemLongClickListener = object : AdapterView.OnItemLongClickListener {
            override fun onItemLongClick(
                adapterView: AdapterView<*>?,
                view: View,
                position: Int,
                l: Long
            ): Boolean {
                // Preparando cuadro de dialogo para preguntar al usuario
                // Si esta seguro de eliminar o no el registro
                val ad = AlertDialog.Builder(this@EstudiantesActivity)
                ad.setMessage("Está seguro de eliminar registro?")
                    .setTitle("Confirmación")
                ad.setPositiveButton("Si"
                ) { dialog, id ->
                    estudiantes!![position].nombre?.let {
                        refEstudiantes.child(it).removeValue()
                    }
                    Toast.makeText(
                        this@EstudiantesActivity,
                        "Registro borrado!", Toast.LENGTH_SHORT
                    ).show()
                }
                ad.setNegativeButton("No", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id: Int) {
                        Toast.makeText(
                            this@EstudiantesActivity,
                            "Operación de borrado cancelada!", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
                ad.show()
                return true
            }
        }

        fab_agregar.setOnClickListener(View.OnClickListener { // Cuando el usuario quiere agregar un nuevo registro
            val i = Intent(getBaseContext(), PromedioNotas::class.java)
            i.putExtra("accion", "a") // Agregar
            i.putExtra("key", "")
            i.putExtra("nombre", "")
            i.putExtra("promedio", "")
            startActivity(i)
        })
        estudiantes = ArrayList<Estudiante>()

        // Cambiarlo refProductos a consultaOrdenada para ordenar lista
        consultaOrdenada.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Procedimiento que se ejecuta cuando hubo algun cambio
                // en la base de datos
                // Se actualiza la coleccion de personas
                estudiantes!!.removeAll(estudiantes!!)
                for (dato in dataSnapshot.getChildren()) {
                    val persona: Estudiante? = dato.getValue(Estudiante::class.java)
                    persona?.key(dato.key)
                    if (persona != null) {
                        estudiantes!!.add(persona)
                    }
                }
                val adapter = AdaptadorEstudiante(
                    this@EstudiantesActivity,
                    estudiantes as ArrayList<Estudiante>
                )
                listaEstudiantes!!.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    companion object {
        var database: FirebaseDatabase = FirebaseDatabase.getInstance()
        var refEstudiantes: DatabaseReference = database.getReference("estudiantes")
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