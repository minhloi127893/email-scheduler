package com.javatest.email_scheduler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.javatest.email_scheduler.dto.UsersDTO;
import com.javatest.email_scheduler.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login"; // view login.html
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("usersDto", new UsersDTO());
        return "register"; // view register.html
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("usersDto") UsersDTO usersDto, Model model) {
        if(userService.existsByUsername(usersDto.getName())) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }

        userService.createUser(usersDto.getName(), usersDto.getPassword(), "USER");
        return "redirect:/login?registered";
    }
}


