package com.gestionstock.microservice_utilisateur.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserManagementController {

    @GetMapping("/add-user")
    public String showAddUserPage() {
        return "add-user"; // نام فایل HTML بدون پسوند
    }
}
