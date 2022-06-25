package com.example.tareas_ejercicio3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tareas_ejercicio3.Procesos.Datos;
import com.example.tareas_ejercicio3.desarrollo.SQLiteConexion;

public class ActivityEditarPersona extends AppCompatActivity {

    private String idPersona, nombre, apellido, edad, correo, direccion;
    EditText txtid, txtnombre, txtapellido, txtedad, txtcorreo, txtdireccion;
    SQLiteConexion conexion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_persona);
        conexion = new SQLiteConexion(this, Datos.NameDataBase, null, 1);

        Button btneliminar = (Button) findViewById(R.id.btneliminar);
        Button btnactualizar = (Button) findViewById(R.id.btnactualizar);
        Button btnvolver = (Button) findViewById(R.id.btnVolverE);

        txtid = (EditText) findViewById(R.id.txtid);
        txtnombre = (EditText) findViewById(R.id.nombre);
        txtapellido = (EditText) findViewById(R.id.apellido);
        txtedad = (EditText) findViewById(R.id.edad);
        txtcorreo = (EditText) findViewById(R.id.correo);
        txtdireccion = (EditText) findViewById(R.id.direccion);

        btnactualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActualizarPersona();
            }
        });

        btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EliminarContacto();
            }
        });

        btnvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        idPersona = intent.getStringExtra("idPersona");
        txtid.setText(idPersona);
        txtid.setKeyListener(null);
        BuscarPersona();


    }

    private void BuscarPersona() {
        SQLiteDatabase db = conexion.getWritableDatabase();
        String [] params = {idPersona};
        String [] fields = {Datos.nombrePersona,
                Datos.apellidoPersona,
                Datos.edadPersona,
                Datos.correoPersona,
                Datos.direccionPersona};
        String wherecond = Datos.idPersona + "=?";

        try{
            Cursor cdata = db.query(Datos.tablaPersona, fields, wherecond, params, null, null, null);
            cdata.moveToFirst();

            txtnombre.setText(cdata.getString(0));
            txtapellido.setText(cdata.getString(1));
            txtedad.setText(cdata.getString(2));
            txtcorreo.setText(cdata.getString(3));
            txtdireccion.setText(cdata.getString(4));


        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Persona no encontrada", Toast.LENGTH_SHORT).show();
        }
    }

    private void ActualizarPersona() {
        int numeros = 0;
        if(txtnombre.getText().toString().isEmpty() || txtapellido.getText().toString().isEmpty()) {
            mostrarDialogoVacios();
        } else {
            for (int i = 0; i < txtnombre.getText().toString().length(); i++) {
                if (Character.isDigit(txtnombre.getText().toString().charAt(i))) {
                    mostrarDialogoNumeros();
                    numeros = 1;
                    break;
                }
            }

            if (numeros == 0) {
                SQLiteDatabase db = conexion.getWritableDatabase();
                String [] params = {idPersona}; //Parametro de Busqueda

                ContentValues valores = new ContentValues();
                valores.put(Datos.nombrePersona, txtnombre.getText().toString());
                valores.put(Datos.apellidoPersona, txtapellido.getText().toString());
                valores.put(Datos.edadPersona, txtedad.getText().toString());
                valores.put(Datos.correoPersona, txtcorreo.getText().toString());
                valores.put(Datos.direccionPersona, txtdireccion.getText().toString());

                db.update(Datos.tablaPersona, valores, Datos.idPersona + "=?", params);
                Toast.makeText(getApplicationContext(), "!!Actualizado Con Exito!!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), ActivityListadoPersonas.class);
                startActivity(intent);
            }
        }

    }

    private void EliminarContacto() {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar Eliminación")
                .setMessage("¿Desea eliminar esta persona: " + txtnombre.getText() + "?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteDatabase db = conexion.getWritableDatabase();
                        String [] params = {idPersona}; //Parametro de Busqueda

                        db.delete(Datos.tablaPersona, Datos.idPersona + "=?", params);
                        Toast.makeText(getApplicationContext(), "Persona Eliminado", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), ActivityListadoPersonas.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "eliminación cancelada", Toast.LENGTH_LONG).show();
                    }
                }).show();
    }




    private void mostrarDialogoVacios() {
        new AlertDialog.Builder(this)
                .setTitle("Alerta de Vacíos")
                .setMessage("No puede dejar ningún campo vacío")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }

    private void mostrarDialogoNumeros() {
        new AlertDialog.Builder(this)
                .setTitle("Alerta de Números")
                .setMessage("No puede ingresar números en el campo de Nombre")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }
}