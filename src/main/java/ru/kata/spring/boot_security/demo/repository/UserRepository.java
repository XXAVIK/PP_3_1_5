package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long>
{
        User findByUsername(String username);


//    void save(User user);
//
//    void edit(User user);
//
//    void deleteById(long id);
//
//    User findById(long id);
//
//    User findByUsername(String username);
//
//    List<User> findAll();
}
