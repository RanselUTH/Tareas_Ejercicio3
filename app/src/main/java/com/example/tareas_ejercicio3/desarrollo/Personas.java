package com.example.tareas_ejercicio3.desarrollo;

public class Personas {

    private Integer idPersona;
    private String nombrePersona;
    private String apellidoPersona;
    private Integer edadPersona;
    private String correoPersona;
    private String direccionPersona;


    public Personas() {
    }

    public Personas(Integer idPersona, String nombrePersona, String apellidoPersona, Integer edadPersona, String correoPersona, String direccionPersona) {
        this.idPersona = idPersona;
        this.nombrePersona = nombrePersona;
        this.apellidoPersona = apellidoPersona;
        this.edadPersona = edadPersona;
        this.correoPersona = correoPersona;
        this.direccionPersona = direccionPersona;
    }

    public Integer getIdPersona() {
        return idPersona;
    }public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }
    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    public String getApellidoPersona() {
        return apellidoPersona;
    }
    public void setApellidoPersona(String apellidoPersona) {
        this.apellidoPersona = apellidoPersona;
    }

    public Integer getEdadPersona() {
        return edadPersona;
    }
    public void setEdadPersona(Integer edadPersona) {
        this.edadPersona = edadPersona;
    }

    public String getCorreoPersona() {
        return correoPersona;
    }
    public void setCorreoPersona(String correoPersona) {
        this.correoPersona = correoPersona;
    }

    public String getDireccionPersona() {
        return direccionPersona;
    }
    public void setDireccionPersona(String direccionPersona) {
        this.direccionPersona = direccionPersona;
    }
}
