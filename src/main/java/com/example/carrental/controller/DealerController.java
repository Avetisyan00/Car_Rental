package com.example.carrental.controller;

import com.example.carrental.entity.User;
import com.example.carrental.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class DealerController {
    private final UserService userService;

    //DEALERS REGISTRATION
    @GetMapping("/registration/dealer")
    public String dealerRegistrationPage() {
        return "dealerRegistration";
    }

    @PostMapping("/registration/dealer")
    public String dealerRegistration(@ModelAttribute User user, @RequestParam("userImage") MultipartFile file) {
        userService.saveUserAsDealer(user, file);
        return "redirect:/";
    }
}
