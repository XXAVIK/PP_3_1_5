package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.UserRequest;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ru.kata.spring.boot_security.demo.service.UserService.COLOR_RESET;
import static ru.kata.spring.boot_security.demo.service.UserService.YELLOW;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class RestController {

    private final UserService userService;
    private final RoleRepository roleRepository;


    public static final FieldError error = new FieldError("username", "username", "Username already exists");

    @GetMapping("/secure/users")
    public List<User> showAllUsers() {
        List<User> userList = userService.listUsers();
        return userList;
    }

    @GetMapping("/secure/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id) {
        User user = userService.userById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @PostMapping("/secure/users")
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
//        log.info(roleId.toString());
        User user = userRequest.getUser();
        Long[] roleIds = userRequest.getRoleIds();
        Set<Role> roleSet = new HashSet<>();
        for (Long roleId : roleIds) {
            roleSet.add(roleRepository.getById(roleId));
        }
        user.setRoles(roleSet);
        if (!userService.save(user)) {
            return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }  //сюда надо передать binding result
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/secure/users")
    public ResponseEntity<?> updateUser(@RequestBody UserRequest userRequest) {
        User user = userRequest.getUser();
        Long[] roleIds = userRequest.getRoleIds();
        Set<Role> roleSet = new HashSet<>();
        for (Long roleId : roleIds) {
            roleSet.add(roleRepository.getById(roleId));
        }
        user.setRoles(roleSet);
        if (!userService.edit(user)) {
            return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }  //сюда надо передать binding result
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/secure/users/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        if (!userService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok("user " + id + " deleted");
        }
    }

    @GetMapping("/secure/roles")
    public List<Role> showAllRoles() {
        return userService.getRoles();
    }
    @GetMapping("/user")
    public ResponseEntity<?> getUserInfo(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        // Дополнительная логика для получения информации о пользователе
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
