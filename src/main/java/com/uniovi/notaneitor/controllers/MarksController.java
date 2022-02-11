package com.uniovi.notaneitor.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //Esto indica que la clase es un controlador que responde a peticiones REST
public class MarksController {

    @RequestMapping("/mark/list")
    public String getList(){
        return "GettingList";
    }

    @RequestMapping("/mark/add")
    public String setMark(){
        return "Adding Mark";
    }

    @RequestMapping("/mark/details")
    public String getDetail(){
        return "Getting Details";
    }

}
