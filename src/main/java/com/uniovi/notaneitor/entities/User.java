package com.uniovi.notaneitor.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true)
    private String dni;
    private String name;
    private String lastName;
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Mark> marks;

    public User(String dni, String name, String lastName){
        super();
        this.dni = dni;
        this.name = name;
        this.lastName = lastName;
    }

    public User(){

    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setMarks(Set<Mark> marks) {
        this.marks = marks;
    }

    public long getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRole() {
        return role;
    }

    public Set<Mark> getMarks() {
        return marks;
    }
}
