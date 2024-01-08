package com.example.fixify.controllers;

import com.example.fixify.models.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);



    @GetMapping("/login")
    public String showLoginPage(Model model) {

        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    @PostMapping("/login")
    public String loginError(Usuario usuario) {
        logger.info("Usuario: {}, Correo: {}", usuario.getUsername(), usuario.getPassword());

        return "login";
    }
}