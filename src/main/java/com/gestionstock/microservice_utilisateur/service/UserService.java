package com.gestionstock.microservice_utilisateur.service;

import com.gestionstock.microservice_utilisateur.model.User;
import com.gestionstock.microservice_utilisateur.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // ایجاد کاربر جدید
    public User createUser(User user) {
        return userRepository.save(user); // ذخیره کاربر جدید در دیتابیس
    }

    // دریافت تمام کاربران
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // دریافت کاربر بر اساس ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // به‌روزرسانی کاربر

    public User updateUser(Long id, User userDetails) {
        // پیدا کردن کاربر بر اساس ID
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        // به‌روزرسانی اطلاعات کاربر
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setEmail(userDetails.getEmail());

        // ذخیره کردن کاربر به‌روزرسانی شده در دیتابیس
        return userRepository.save(user);
    }



    // حذف کاربر
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}