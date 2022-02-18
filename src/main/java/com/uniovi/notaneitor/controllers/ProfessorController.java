package com.uniovi.notaneitor.controllers;

import com.uniovi.notaneitor.entities.Professor;
import com.uniovi.notaneitor.services.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfessorController {

    @Autowired
    ProfessorService professorService;

    @RequestMapping(value = "/professor/add", method = RequestMethod.POST)
    public String addProfessor(@ModelAttribute Professor profesor){
        professorService.addProfessor(profesor);
        return "Se ha añadido el profesor correctamente";
    }

    @RequestMapping("/professor/delete/{dni}")
    public String deleteProfessor(@PathVariable String dni){
        professorService.deleteProfessor(dni);
        return "Se ha borrado el profesor correctamente";
    }

    @RequestMapping(value = "/professor/edit", method = RequestMethod.POST)
    public String editProfessor(@ModelAttribute Professor profesor){
        return professorService.editProfessor(profesor);
    }

    @RequestMapping("/professor/detail/{dni}")
    public String detailProfessor(@PathVariable String dni){
        return professorService.detailProfessor(dni);
    }

}
