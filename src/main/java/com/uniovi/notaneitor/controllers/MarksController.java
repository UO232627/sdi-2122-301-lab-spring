package com.uniovi.notaneitor.controllers;

import org.springframework.web.bind.annotation.*;
import com.uniovi.notaneitor.entities.*;

@RestController
public class MarksController {

    @RequestMapping("/mark/list")
    public String getList(){
        return "Getting List";
    }

    @RequestMapping(value = "/mark/add", method = RequestMethod.POST)
    public String setMark(@ModelAttribute Mark mark){
        return "Added: " + mark.getDescription()
                + " with score " + mark.getScore()
                + " and id " + mark.getId();
    }

    @RequestMapping("/mark/details/{id}")
    public String getDetails(@PathVariable Long id){
        return "Getting Details => " + id;
    }

}
