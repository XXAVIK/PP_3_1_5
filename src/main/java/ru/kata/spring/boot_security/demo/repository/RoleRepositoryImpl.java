//package ru.kata.spring.boot_security.demo.repository;
//
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//import ru.kata.spring.boot_security.demo.model.Role;
//import ru.kata.spring.boot_security.demo.model.User;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@Repository
//@Transactional
//public class RoleRepositoryImpl implements RoleRepository {
//    @PersistenceContext
//    private EntityManager em;
//
//    @Override
//    public void save(Role role) {
//        em.persist(role);
//        System.out.println("Созданa - " + role);
//    }
//
//    public Role convertSR(String name) {
//        return findByName(name);
//    }
//
//    public Set<Role> convertSS(String name) {
//        Set<Role> roles = new HashSet<>();
//        roles.add(findByName(name));
//        return roles;
//    }
//
//    public Long convertSL(String id) {
//
//        return Long.parseLong(id);
//    }
//
//    @Override
//    public void edit(Role role) {
//        em.merge(role);
//        System.out.println("Изменен - " + role);
//    }
//
//    @Override
//    public void deleteById(long id) {
//        em.remove(findById(id));
//        System.out.println("Удален юзер");
//    }
//
//    @Override
//    public Role findById(long id) {
//        return em.find(Role.class, id);
//    }
//
//
//    @Override
//    @SuppressWarnings("unchecked")
//    public List<Role> findAll() {
//        return em.createQuery("select r FROM Role r", Role.class).getResultList();
//    }
//
//    @Override
//    public Role findByName(String name) {
//
//        Role role = null;
//        try {
//            role = em.createQuery(
//                            "SELECT r from Role r WHERE r.name = :name", Role.class).
//                    setParameter("name", name).getSingleResult();
//            return role;
//        } catch (Exception e) {
//            return null;
//        }
//    }
//}
