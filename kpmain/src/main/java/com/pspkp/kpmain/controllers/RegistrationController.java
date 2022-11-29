package com.pspkp.kpmain.controllers;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import com.pspkp.kpmain.models.Role;
import com.pspkp.kpmain.models.User;
import com.pspkp.kpmain.repo.UserRepository;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration(Model model){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.addAttribute("message", "User exists!");
            return "registration";
        }

        user.setActive(true);
        if (user.getUsername().equals("admin")){
            user.setRoles(Collections.singleton(Role.ADMIN));
        }
        else user.setRoles(Collections.singleton(Role.USER));
        user.setStatus("Рядовой");
        user.setPassed(0);
        user.setCreated(0);
        userRepository.save(user);

        return "redirect:/login";
    }
}
