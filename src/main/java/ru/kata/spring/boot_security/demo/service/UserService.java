package ru.kata.spring.boot_security.demo.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.UserRequest;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public static final String COLOR_RESET = "\u001B[0m";
    public static final String YELLOW = "\u001B[33m";

    public User findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    public User userById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public boolean save(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            log.info(YELLOW + "Попытка создания дубликата юзернэйма " + COLOR_RESET + user.getUsername());

            return false;
        }
        log.info(YELLOW + "Создание нового юзера " + COLOR_RESET + user.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Transactional
    public boolean edit(User user) {
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        if (existingUser == null) {
            log.error("Пользователь с ID " + user.getId() + " не найден в базе данных");
            return false;
        }
        String usernameFromInput = user.getUsername();
        String usernameFromDB = existingUser.getUsername();
        if (!usernameFromInput.equals(usernameFromDB)) {
            User userWithSameUsername = userRepository.findByUsername(usernameFromInput);
            if (userWithSameUsername != null) {
                log.info("Попытка создания дубликата пользователя с именем: " + usernameFromInput);
                return false;
            }
        }
        log.info("Редактирование пользователя: " + usernameFromDB);
        existingUser.setUsername(usernameFromInput);
        existingUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(existingUser);
        return true;
    }


    @Transactional
    public boolean delete(Long userId) {
        if (userById(userId) != null) {
            userRepository.deleteById(userId);
            log.info(YELLOW + "Удаление юзера" + COLOR_RESET);
            return true;
        }
        return false;
    }

    @Transactional
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Transactional
    public User setRolesFromUserRequest(UserRequest userRequest) {
        User user = userRequest.getUser();
        Long[] roleIds = userRequest.getRoleIds();
        Set<Role> roleSet = new HashSet<>();
        for (Long roleId : roleIds) {
            roleSet.add(roleRepository.getById(roleId));
        }
        user.setRoles(roleSet);
        return user;
    }
}
