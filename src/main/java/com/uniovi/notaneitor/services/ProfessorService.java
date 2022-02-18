package com.uniovi.notaneitor.services;

import com.uniovi.notaneitor.entities.Professor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProfessorService {

    List<Professor> professors = new LinkedList<Professor>();

    @PostConstruct
    public void init(){
        professors.add(new Professor("1", "a", "a", "a"));
    }

    public void addProfessor(Professor profesor){
        professors.add(profesor);
    }

    public void deleteProfessor(String dni){
        professors.removeIf(profesor -> profesor.getDni().equals(dni));
    }

    public String editProfessor(Professor profesor){
        Professor profesorAuxiliar = professors.stream().filter(profesorAux -> profesorAux.getDni().equals(profesor.getDni())).findFirst().get();

        if (profesorAuxiliar == null){
            return "No se ha encontrado el profesor";
        }

        professors.remove(profesorAuxiliar);
        profesorAuxiliar.setNombre(profesor.getNombre());
        profesorAuxiliar.setApellido(profesor.getApellido());
        profesorAuxiliar.setCategoria(profesor.getCategoria());
        professors.add(profesorAuxiliar);

        return "Nuevos datos del profesor: dni: " + profesor.getDni() + ", nombre: " + profesor.getNombre() + ", apellido: " + profesor.getApellido() + ", categoria: " + profesor.getCategoria();
    }

    public String detailProfessor(String dni){
        return professors.stream().filter(profesor -> profesor.getDni().equals(dni)).findFirst().get().toString();


    }

}
