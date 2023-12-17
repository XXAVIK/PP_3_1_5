package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kata.spring.boot_security.demo.model.Role;
import java.util.List;
import java.util.Set;
// extends JpaRepository<Role,Long>
public interface RoleRepository extends JpaRepository<Role,Long>{

//    @Query("SELECT r FROM Role r WHERE r.name = ?1")
//    public Set<Role> findByName(String name);
//    void save(Role role);
//
//    void edit(Role role);
//
//    void deleteById(long id);
//
//    Role findById(long id);
//
//    List<Role> findAll();
//
//    Role findByName(String role);
//    public Role convertSR(String name);
//    public Set<Role> convertSS(String name);
//
//    public Long convertSL(String id);


}