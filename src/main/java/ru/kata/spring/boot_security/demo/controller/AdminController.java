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
    public static final FieldError error = new FieldError("username", "username", "Username already exists");
    private final UserService userService;
    private final RoleRepository roleRepository;


    @GetMapping()
    public String startUp(Model model,Principal principal) {
        List<User> userList = userService.listUsers();
        model.addAttribute("users", userList);
        model.addAttribute("logged", userService.findByUsername(principal.getName()));
        model.addAttribute("user", new User());
        model.addAttribute("roles_list", roleRepository.findAll());
        log.info(YELLOW + roleRepository.findAll().toString() + COLOR_RESET);
        return "admin";
    }
    @GetMapping("admin-info")
    public String info(Model model,Principal principal) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "admin-info";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.info(YELLOW + "Ошибки в bindingResult" + COLOR_RESET);
            log.info(YELLOW + bindingResult.toString()+bindingResult.getObjectName()+ COLOR_RESET);
            return "redirect:/admin";
        }
        if (!userService.save(user)) {
            bindingResult.addError(error);
            log.info(YELLOW + "Попытка дубликата - лог пишется из пост контроллера" + COLOR_RESET);
            log.info(YELLOW + bindingResult.toString()+bindingResult.getObjectName()+ COLOR_RESET);

            return "redirect:/admin";
        }
        return "redirect:/admin";
    }


    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id,Model model) {
        log.info(YELLOW+userService.userById(id)+"Redact conmtroller get"+COLOR_RESET);
        model.addAttribute("user", userService.userById(id));
        return "admin";
    }

    @PatchMapping()
    public String edit(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,Model model) {
        log.info(YELLOW+"редактор юзера патч к"+user+COLOR_RESET);

        if (bindingResult.hasErrors()) {
            log.info(YELLOW + "Ошибки в bindingResult - патч контроллер" + COLOR_RESET);
            log.warn(bindingResult.toString());
            return "redirect:/admin";
        }
        if (!userService.edit(user)) {
            log.info(YELLOW + "Попытка дубликата - лог пишется из патч контроллера" + COLOR_RESET);
            bindingResult.addError(error);
            return "redirect:/admin";
        }
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
