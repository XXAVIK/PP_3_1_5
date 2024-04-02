package ru.kata.spring.boot_security.demo.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

import static ru.kata.spring.boot_security.demo.service.UserService.COLOR_RESET;
import static ru.kata.spring.boot_security.demo.service.UserService.YELLOW;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final UserService userService;

    @GetMapping()
    public String startUp() {
        return "admin";
    }

    @GetMapping("admin-info")
    public String info(Model model, Principal principal) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "admin-info";
    }

}
