package com.example.tareas_ejercicio3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tareas_ejercicio3.Procesos.Datos;
import com.example.tareas_ejercicio3.desarrollo.SQLiteConexion;

public class MainActivity extends AppCompatActivity {

    SQLiteConexion conexion;
    EditText txtnombre, txtapellido, txtedad, txtcorreo, txtdireccion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conexion = new SQLiteConexion(this, Datos.NameDataBase, null, 1);

        Button btnguardarPersona = (Button) findViewById(R.id.btnguardarPersona);
        Button btnverPersona = (Button) findViewById(R.id.btnverPersona);
        txtnombre = (EditText) findViewById(R.id.txtnombre);
        txtapellido = (EditText) findViewById(R.id.txtapellido);
        txtedad = (EditText) findViewById(R.id.txtedad);
        txtcorreo = (EditText) findViewById(R.id.txtcorreo);
        txtdireccion = (EditText) findViewById(R.id.txtdireccion);


        btnverPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityListadoPersonas.class);
                startActivity(intent);
            }
        });
        
        btnguardarPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AgregarContacto();
            }
        });
    }


    private void AgregarContacto() {
        int numeros = 0;
        if(txtnombre.getText().toString().isEmpty() || txtcorreo.toString().isEmpty()) {
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
                SQLiteConexion conexion = new SQLiteConexion(this, Datos.NameDataBase, null, 1);
                SQLiteDatabase db = conexion.getWritableDatabase();

                ContentValues valores = new ContentValues();
                valores.put(Datos.nombrePersona, txtnombre.getText().toString());
                valores.put(Datos.apellidoPersona, txtapellido.getText().toString());
                valores.put(Datos.edadPersona, txtedad.getText().toString());
                valores.put(Datos.correoPersona, txtcorreo.getText().toString());
                valores.put(Datos.direccionPersona, txtdireccion.getText().toString());


                Long resultado = db.insert(Datos.tablaPersona, Datos.idPersona, valores);
                Toast.makeText(getApplicationContext(), "Persona Guardada: "  + resultado.toString(), Toast.LENGTH_LONG).show();
                db.close();
                ClearScreen();
            }
        }
    }

    private void ClearScreen() {
        txtnombre.setText("");
        txtapellido.setText("");
        txtedad.setText("");
        txtcorreo.setText("");
        txtdireccion.setText("");
    }

    private void mostrarDialogoVacios() {
        new AlertDialog.Builder(this)
                .setTitle("Alerta Campos Vacios")
                .setMessage("No puede dejar ningún campo vacío")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }

    private void mostrarDialogoNumeros() {
        new AlertDialog.Builder(this)
                .setTitle("Alerta Números")
                .setMessage("No puede ingresar números en el campo de Nombre")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }
}