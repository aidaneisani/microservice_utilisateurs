package com.gestionstock.microservice_utilisateur.controller;

import com.gestionstock.microservice_utilisateur.model.User;
import com.gestionstock.microservice_utilisateur.repository.UserRepository;
import com.gestionstock.microservice_utilisateur.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/test")
    public String test() {
        return "API is working!";
    }

    @PostMapping
    public ResponseEntity<String> createUser(@ModelAttribute User user) {
        userService.createUser(user);
        return ResponseEntity.ok("User added successfully!"); // بازگشت به صفحه‌ی add-user با پیام موفقیت
    }

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
        try {
            // فراخوانی متد updateUser از سرویس
            User updatedUser = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(updatedUser);  // بازگرداندن کاربر به‌روزرسانی شده
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);  // اگر کاربر یافت نشد
        }
    }


    // حذف کاربر

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);  // از متد `getUserById` در سرویس استفاده کنید

        if (user.isPresent()) {
            userService.deleteUser(id);  // از متد `deleteUser` در سرویس استفاده کنید
            return ResponseEntity.ok("User deleted successfully!");
        } else {
            return ResponseEntity.status(404).body("User not found!");
        }
    }


}
