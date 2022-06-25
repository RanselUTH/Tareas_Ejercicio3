package com.example.tareas_ejercicio3.Procesos;

public class Datos {

    public static final String tablaPersona = "personas";

    public static final String idPersona = "idPersona";
    public static final String nombrePersona = "nombrePersona";
    public static final String apellidoPersona = "apellidoPersona";
    public static final String edadPersona = "edadPersona";
    public static final String correoPersona = "correoPersona";
    public static final String direccionPersona = "direccionPersona";


    public static final String CreateTablePersonas =
            "CREATE TABLE personas( idPersona INTEGER PRIMARY KEY AUTOINCREMENT, nombrePersona TEXT, apellidoPersona TEXT, edadPersona INTEGER, correoPersona TEXT, direccionPersona TEXT)";

    public static final String DropTablePersonas =
            "DROP TABLE IF EXITS personas";

    /* Base de Datos */
    public static final String NameDataBase = "DBTarea_Ejercicio3";
}

