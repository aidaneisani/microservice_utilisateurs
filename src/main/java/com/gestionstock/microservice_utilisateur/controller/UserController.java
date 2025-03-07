package com.gestionstock.microservice_utilisateur.controller;

import com.gestionstock.microservice_utilisateur.model.User;
import com.gestionstock.microservice_utilisateur.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // ایجاد کاربر جدید
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // دریافت تمام کاربران
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // دریافت کاربر بر اساس ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // به‌روزرسانی کاربر
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return ResponseEntity.ok(userService.updateUser(id, userDetails));
    }

    // حذف کاربر
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
