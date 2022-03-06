package com.uniovi.notaneitor.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Professor {

    @Id
    @GeneratedValue
    private Long id;
    private String dni;
    private String name;
    private String surname;
    private String category;

    public Professor(){

    }

    public Professor(String dni, String nombre, String apellido, String categoria) {
        this.dni = dni;
        this.name = nombre;
        this.surname = apellido;
        this.category = categoria;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "dni='" + dni + '\'' +
                ", nombre='" + name + '\'' +
                ", apellido='" + surname + '\'' +
                ", categoria='" + category + '\'' +
                '}';
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setName(String nombre) {
        this.name = nombre;
    }

    public void setSurname(String apellido) {
        this.surname = apellido;
    }

    public void setCategory(String categoria) {
        this.category = categoria;
    }

    public String getDni() {
        return dni;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCategory() {
        return category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
