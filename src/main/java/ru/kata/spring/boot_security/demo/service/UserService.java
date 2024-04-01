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


    // Declaring the color
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
        String usernameFromInput = user.getUsername(); //Получаю имя из инпута
        String usernameFromDB = (userRepository.       //Получаю имя юзера с тем же айди из БД
                findById(user.getId()).orElse(null).getUsername());
        //Получаю всего пользователя по имени
        User userFromDB = userRepository.findByUsername(usernameFromInput);

        //Проверяю существует ли юзернэйм из инпута в БД
        //Если уже существует, значит это либо пользователь без изменений, либо имя оставили без изменений
        if (usernameFromInput.equals(usernameFromDB)) {
            log.info(YELLOW + "Редактирование пользователя не меняя имени " + COLOR_RESET + user.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user); //поменять на edit если hibernate
            return true;
        }
        //Если новое имя из инпута совпадает со старым, то оно попадет в этот фильтр
        if (userFromDB != null) {
            log.info(YELLOW + "Попытка создания дубликата юзернэйма v REDACTOR " + COLOR_RESET + user.getUsername());
            return false;
        }
        //Если юзернейм новый, но поменялись любые другие поля, то пользователь попадает сюда
        log.info(YELLOW + "Создание нового юзера v REDACTOR " + COLOR_RESET + user.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user); //поменять на edit если hibernate

        //Я чет не смог придумать лучше способа отлавливать случаи,когда в редакторе вводится уже сущ имя

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
    public List<Role> getRoles(){
        return roleRepository.findAll();
    }
}
