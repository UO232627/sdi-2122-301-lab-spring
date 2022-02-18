package com.uniovi.notaneitor.services;

import com.uniovi.notaneitor.entities.Professor;
import com.uniovi.notaneitor.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    public void addProfessor(Professor profesor){
        professorRepository.save(profesor);
    }

    public void deleteProfessor(String dni){
        professorRepository.deleteById(dni);
    }

    public String editProfessor(Professor profesor){
        addProfessor(profesor);

        return "Datos del profesor editados: Nombre: " + profesor.getNombre() + ", Apellido: " + profesor.getApellido() + ", Categoria: " + profesor.getCategoria();
    }

    public String detailProfessor(String dni){
        return professorRepository.findById(dni).toString();
    }

}
