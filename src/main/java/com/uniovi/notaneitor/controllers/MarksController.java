package com.uniovi.notaneitor.controllers;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.entities.User;
import com.uniovi.notaneitor.services.MarksService;
import com.uniovi.notaneitor.services.UsersService;
import com.uniovi.notaneitor.validators.MarksFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.LinkedList;

@Controller
public class MarksController {

    @Autowired
    private MarksService marksService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private MarksFormValidator marksFormValidator;
    @Autowired
    private HttpSession httpSession;

    @RequestMapping("/mark/list")
    public String getList(Model model, Pageable pageable, Principal principal, @RequestParam(value = "", required = false) String searchText){
        String dni = principal.getName();
        User user = usersService.getUserByDni(dni);
        Page<Mark> marks = new PageImpl<Mark>(new LinkedList<Mark>());

        if (searchText != null && !searchText.isEmpty()){
            marks = marksService.searchMarksByDescriptionAndNameForUser(pageable, searchText, user);
        }
        else{
            marks = marksService.getMarksForUser(pageable, user);
        }

        model.addAttribute("markList", marks.getContent());
        model.addAttribute("page", marks);

        return "/mark/list";
    }

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
    public String updateList(Model model, Pageable pageable, Principal principal){
        String dni = principal.getName();
        User user = usersService.getUserByDni(dni);
        Page<Mark> marks = marksService.getMarksForUser(pageable, user);

        model.addAttribute("markList", marks.getContent());
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

    @RequestMapping(value = "/mark/{id}/resend", method = RequestMethod.GET)
    public String setResentTrue(Model model, @PathVariable Long id){
        marksService.setMarkResend(true, id);
        return "redirect:/mark/list";
    }

    @RequestMapping(value = "/mark/{id}/noresend", method = RequestMethod.GET)
    public String setResentFalse(Model model, @PathVariable Long id){
        marksService.setMarkResend(false, id);
        return "redirect:/mark/list";
    }

    @RequestMapping("/mark/list/updateHome")
    public String updateList(Model model, Principal principal){
        String dni = principal.getName();
        User user = usersService.getUserByDni(dni);
        model.addAttribute("markList", marksService.getMarksForUser(user));

        return "/mark/list::tableMarks";
    }

}
