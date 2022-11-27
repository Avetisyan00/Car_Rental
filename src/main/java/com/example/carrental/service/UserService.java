package com.example.carrental.service;

import com.example.carrental.entity.Role;
import com.example.carrental.entity.User;
import com.example.carrental.exception.DuplicateEmailException;
import com.example.carrental.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MailService mailService;
    private final BCryptPasswordEncoder passwordEncoder;
    @Value("${car.rental.user.images.folder}")
    private String folderPath;

    public void saveUserAsClient(@ModelAttribute User user, MultipartFile file) throws DuplicateEmailException, IOException, MessagingException {
        checkEmail(user);
        user.setRole(Role.CLIENT);
        user.setEnabled(false);
        user.setVerifyToken(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        saveUsersImage(user, (file));
        userRepository.save(user);
        mailService.sendHtmlEmail(user.getEmail(), "Welcome", "Hello " + user.getName() + " " + user.getSurname() + ".\nVerify your account by clicking on this link " +
                "<a href=\"http://localhost:8080/users/verify?email=" + user.getEmail() + "&token=" + user.getVerifyToken() + "\">Activate</a>");
    }

    public void saveUserAsDriver(@ModelAttribute User user, MultipartFile file) throws DuplicateEmailException, IOException, MessagingException {
        checkEmail(user);
        user.setRole(Role.DRIVER);
        user.setEnabled(false);
        user.setVerifyToken(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        saveUsersImage(user, file);
        userRepository.saveAndFlush(user);
        mailService.sendHtmlEmail(user.getEmail(), "Welcome", "Hello " + user.getName() + " " + user.getSurname() + ".\nVerify your account by clicking on this link " +
                "<a href=\"http://localhost:8080/users/verify?email=" + user.getEmail() + "&token=" + user.getVerifyToken() + "\">Activate</a>");
    }

    public void saveUserAsDealer(@ModelAttribute User user, MultipartFile file) throws DuplicateEmailException, IOException, MessagingException {
        checkEmail(user);
        user.setRole(Role.DEALER);
        saveUsersImage(user, file);
        user.setEnabled(false);
        user.setVerifyToken(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        mailService.sendHtmlEmail(user.getEmail(), "Welcome", "Hello " + user.getName() + " " + user.getSurname() + ".\nVerify your account by clicking on this link " +
                "<a href=\"http://localhost:8080/users/verify?email=" + user.getEmail() + "&token=" + user.getVerifyToken() + "\">Activate</a>");
    }

    public void checkEmail(User user) throws DuplicateEmailException {
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateEmailException("User with this email already exists");
        }
    }

    public void saveUsersImage(User user, MultipartFile file) throws IOException {
        if (!file.isEmpty() && file.getSize() > 0) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File newFile = new File(folderPath + File.separator + fileName);
            file.transferTo(newFile);
            user.setPicUrl(fileName);
        } else {
            user.setPicUrl("C:\\Users\\Edgar\\IdeaProjects\\DiplomaProject\\Car Rental\\src\\main\\resources\\static\\supercars\\assets\\images\\defaultPic.jpg");
        }
    }

    public void verifyUser(String email, String token) throws Exception {
        Optional<User> userOptional = userRepository.findUserByEmailAndVerifyToken(email, token);
        if (userOptional.isEmpty()) {
            throw new Exception("User does with this email and token does not exists");
        }
        User user = userOptional.get();
        if (user.isEnabled()) {
            throw new Exception("User is already verified");
        }
        user.setEnabled(true);
        user.setVerifyToken(null);
        userRepository.save(user);
    }
}