package com.example.tareas_ejercicio3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tareas_ejercicio3.Procesos.Datos;
import com.example.tareas_ejercicio3.desarrollo.Personas;
import com.example.tareas_ejercicio3.desarrollo.SQLiteConexion;

import java.util.ArrayList;

public class ActivityListadoPersonas extends AppCompatActivity {

    SQLiteConexion conexion;
    ListView listapersonas;
    ArrayList<Personas> listaP;
    ArrayList<String> personasArrayList;
    private String  idPersona,nombrepersona, apellidoPersona, edadPersona, correoPersona,direccionPersona;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_personas);

        EditText txtBuscar = (EditText) findViewById(R.id.txtBuscar);


        conexion = new SQLiteConexion(this, Datos.NameDataBase, null, 1);
        listapersonas = (ListView) findViewById(R.id.Listviewlistapersonas);

        ObtenerListaPersonas();
        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, personasArrayList);
        listapersonas.setAdapter(adp);

        txtBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adp.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        listapersonas.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                idPersona = listaP.get(position).getIdPersona().toString();
                nombrepersona = listaP.get(position).getNombrePersona();
                apellidoPersona = listaP.get(position).getApellidoPersona();
                edadPersona = listaP.get(position).getEdadPersona().toString();
                correoPersona = listaP.get(position).getCorreoPersona();
                direccionPersona = listaP.get(position).getDireccionPersona();
                Toast.makeText(getApplicationContext(), "Se selecciono la persona: "+nombrepersona, Toast.LENGTH_LONG).show();
            }
        });

        Button btnEditar = (Button) findViewById(R.id.btnEditar);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(idPersona != null){
                    Intent intent = new Intent(getApplicationContext(), ActivityEditarPersona.class);
                    intent.putExtra("idPersona", String.valueOf(idPersona));
                    startActivity(intent);
                }else{
                    mostrarDialogoSeleccion();
                }
            }
        });
        Button btnvolver = (Button) findViewById(R.id.btnVolver);
        btnvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void ObtenerListaPersonas(){
        SQLiteDatabase db = conexion.getReadableDatabase();
        Personas listapersonas = null;
        listaP = new ArrayList<Personas>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+Datos.tablaPersona, null);

        while (cursor.moveToNext()){
            listapersonas = new Personas();
            listapersonas.setIdPersona(cursor.getInt(0));
            listapersonas.setNombrePersona(cursor.getString(1));
            listapersonas.setApellidoPersona(cursor.getString(2));
            listapersonas.setEdadPersona(cursor.getInt(3));
            listapersonas.setCorreoPersona(cursor.getString(4));
            listapersonas.setDireccionPersona(cursor.getString(5));
            listaP.add(listapersonas);
        }
        fillList();
    }

    private void fillList(){
        personasArrayList = new ArrayList<String>();
        SQLiteDatabase db = conexion.getReadableDatabase();
        for(int i = 0; i<listaP.size(); i++){
            String [] params = {listaP.get(i).getIdPersona().toString()};
            String [] fields = {Datos.idPersona,Datos.nombrePersona};
            String whereCon = Datos.idPersona + "=?";
            Cursor cData = db.query(Datos.tablaPersona, fields, whereCon, params, null, null, null, null);
            cData.moveToFirst();
            personasArrayList.add(cData.getString(1)+" "+ listaP.get(i).getApellidoPersona()+" //Email- "+ listaP.get(i).getCorreoPersona());
            cData.close();
        }
    }

    private void mostrarDialogoSeleccion() {
        new AlertDialog.Builder(this)
                .setTitle("Alerta de Selección")
                .setMessage("Seleccione un contacto de la lista")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }

}