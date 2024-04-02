package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequest {
    private User user;
    private Long[] roleIds;
}
