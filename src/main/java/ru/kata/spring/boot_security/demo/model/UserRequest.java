package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

//Класс обертка для запросов с веб страницы, за один запрос получается принять 2 json объекта
@Data
@NoArgsConstructor
public class UserRequest {
    private User user;
    private Long[] roleIds;
}
