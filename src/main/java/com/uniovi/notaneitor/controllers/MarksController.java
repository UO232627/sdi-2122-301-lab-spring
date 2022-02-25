package com.uniovi.notaneitor.controllers;

import com.uniovi.notaneitor.services.MarksService;
import com.uniovi.notaneitor.services.UsersService;
import com.uniovi.notaneitor.validators.MarksFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.uniovi.notaneitor.entities.*;

@Controller
public class MarksController {

    @Autowired
    private MarksService marksService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private MarksFormValidator marksFormValidator;

    @RequestMapping("/mark/list")
    public String getList(Model model){
        model.addAttribute("markList", marksService.getMarks());
        return "/mark/list";
    }

    /*@RequestMapping(value = "/mark/add", method = RequestMethod.POST)
    public String setMark(@ModelAttribute Mark mark){
        marksService.addMark(mark);
        return "redirect:/mark/list";
    }*/

    @RequestMapping("/mark/details/{id}")
    public String getDetail(Model model, @PathVariable Long id){
        model.addAttribute("mark", marksService.getMark(id));
        return "/mark/details";
    }

    @RequestMapping("/mark/delete/{id}")
    public String deleteMark(@PathVariable Long id){
        marksService.deleteMark(id);
        return "redirect:/mark/list";
    }

    /*@RequestMapping("/mark/add")
    public String getMark(Model model){
        model.addAttribute("usersList", usersService.getUsers());
        return "/mark/add";
    }*/

    @RequestMapping(value="/mark/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id){
        model.addAttribute("mark", marksService.getMark(id));
        model.addAttribute("usersList", usersService.getUsers());
        return "/mark/edit";
    }

    @RequestMapping(value="/mark/edit/{id}", method=RequestMethod.POST)
    public String setEdit(@ModelAttribute Mark mark, @PathVariable Long id){
        Mark originalMark = marksService.getMark(id);
        originalMark.setScore(mark.getScore());
        originalMark.setDescription(mark.getDescription());
        marksService.addMark(originalMark);
        return "redirect:/mark/details/" + id;
    }

    @RequestMapping("/mark/list/update")
    public String updateList(Model model){
        model.addAttribute("markList", marksService.getMarks());
        return "/mark/list::tableMarks";
    }

    @RequestMapping(value="/mark/add", method=RequestMethod.GET)
    public String getMark(Model model){
        model.addAttribute("mark", new Mark());
        return "/mark/add";
    }

    @RequestMapping(value="/mark/add", method=RequestMethod.POST)
    public String setMark(@Validated Mark mark, BindingResult result){
        marksFormValidator.validate(mark, result);
        if (result.hasErrors()){
            return "/mark/add";
        }

        marksService.addMark(mark);
        return "/mark/add";
    }

}
