package com.example.carrental.controller;

import com.example.carrental.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @GetMapping("/registration")
    public String userRegisterChoose() {
        return "registration";
    }

    //VERIFICATION OF USERS
    @GetMapping("/users/verify")
    public String verify(@RequestParam(name = "email") String email, @RequestParam(name = "token") String token) {
        userServiceImpl.verifyUser(email, token);
        return "redirect:/";
    }
}
