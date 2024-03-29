package com.uniovi.notaneitor.controllers;

import com.uniovi.notaneitor.services.RolesService;
import com.uniovi.notaneitor.services.SecurityService;
import com.uniovi.notaneitor.validators.SignUpFormValidator;
import com.uniovi.notaneitor.validators.UserFormValidator;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.uniovi.notaneitor.entities.*;
import com.uniovi.notaneitor.services.UsersService;

@Controller
public class UsersController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private SignUpFormValidator signUpFormValidator;
    @Autowired
    private UserFormValidator userFormValidator;
    @Autowired
    private RolesService rolesService;

    @RequestMapping("/user/list")
    public String getListado(Model model) {
        model.addAttribute("usersList", usersService.getUsers());
        return "user/list";
    }

    @RequestMapping(value = "/user/add")
    public String getUser(Model model) {
        model.addAttribute("roleList", rolesService.getRoles());
        return "user/add";
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public String setUser(@ModelAttribute User user) {
        usersService.addUser(user); return "redirect:/user/list";
    }

    @RequestMapping("/user/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("user", usersService.getUser(id));
        return "user/details";
    }

    @RequestMapping("/user/delete/{id}")
    public String delete(@PathVariable Long id) {
        usersService.deleteUser(id);
        return "redirect:/user/list";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model){
        model.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Validated User user, BindingResult result){
        signUpFormValidator.validate(user, result);
        if (result.hasErrors()){
            return "signup";
        }

        user.setRole(rolesService.getRoles()[0]);
        usersService.addUser(user);
        securityService.autoLogin(user.getDni(), user.getPasswordConfirm());
        return "redirect:home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model){
        return "login";
    }

    @RequestMapping(value = {"/home"}, method = RequestMethod.GET)
    public String home(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String dni = auth.getName();
        User activeUser = usersService.getUserByDni(dni);
        model.addAttribute("markList", activeUser.getMarks());
        return "home";
    }

    @RequestMapping(value="/user/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id){
        model.addAttribute("user", usersService.getUser(id));
        model.addAttribute("usersList", usersService.getUsers());
        return "/user/edit"; //Si lo hacemos asi estamos haciendo que el motor de plantillas devuelva directamente un html al navegador
    }

    @RequestMapping(value="/user/edit/{id}", method=RequestMethod.POST)
    public String setEdit(@ModelAttribute User user, @PathVariable Long id, BindingResult result){
        //Esto está hecho asi porque se supone que a la hora de añadir un usuario también está validado y ya es correcto cuando editas
        User originalUser = usersService.getUser(id); //Así solo se comprueba si modificas el dni, sino no hace la validación
        if (!user.getDni().equals(originalUser.getDni())){
            userFormValidator.validate(user, result);

            if (result.hasErrors()){
                return "/user/edit";
            }
        }
        originalUser.setDni(user.getDni());
        originalUser.setName(user.getName());
        originalUser.setLastName(user.getLastName());
        usersService.addUser(originalUser);
        return "redirect:/user/details/" + id; //Con redirect estamos haciendo que el navegador haga una peticion GET
    }

    @RequestMapping("/user/list/update")
    public String updateList(Model model){
        model.addAttribute("usersList", usersService.getUsers());
        return "/user/list::tableUsers";
    }

}