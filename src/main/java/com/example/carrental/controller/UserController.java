package com.example.carrental.controller;

import com.example.carrental.entity.User;
import com.example.carrental.exception.DuplicateEmailException;
import com.example.carrental.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/registration")
    public String userRegisterChoose() {
        return "registration";
    }

    //CLIENTS REGISTRATION
    @GetMapping("/client/registration")
    public String userRegistrationPage() {
        return "clientRegistration";
    }

    @PostMapping("/client/registration")
    public String userRegistration(@ModelAttribute User user, @RequestParam("userImage") MultipartFile file) throws DuplicateEmailException, IOException, MessagingException {
        userService.saveUserAsClient(user, file);
        return "redirect:/";
    }

    //DRIVERS REGISTRATION
    @GetMapping("/driver/registration")
    public String driverRegistrationPage() {
        return "driverRegistration";
    }

    @PostMapping("/driver/registration")
    public String driverRegistrationPage(@ModelAttribute User user, @RequestParam("userImage") MultipartFile file) throws DuplicateEmailException, IOException, MessagingException {
        userService.saveUserAsDriver(user, file);
        return "redirect:/driver";
    }

    //DEALERS REGISTRATION
    @GetMapping("/dealer/registration")
    public String dealerRegistrationPage() {
        return "dealerRegistration";
    }

    @PostMapping("/dealer/registration")
    public String dealerRegistration(@ModelAttribute User user, @RequestParam("userImage") MultipartFile file) throws DuplicateEmailException, IOException, MessagingException {
        userService.saveUserAsDealer(user, file);
        return "redirect:/";
    }

    //VERIFICATION OF USERS
    @GetMapping("/users/verify")
    public String verify(@RequestParam(name = "email") String email, @RequestParam(name = "token") String token) throws Exception {
        userService.verifyUser(email, token);
        return "redirect:/";
    }
}
