package com.gestionstock.microservice_utilisateur.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // نام فایل HTML بدون پسوند
    }
}