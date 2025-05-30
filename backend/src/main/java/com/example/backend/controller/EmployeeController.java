package com.example.backend.controller;

import com.example.backend.dto.EmployeeDto;
import com.example.backend.entity.EmployeeEntity;
import com.example.backend.security.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("employee")
public class EmployeeController {
    private final EmployeeService userService;

    @Autowired
    public EmployeeController(EmployeeService userService) {
        this.userService = userService;
    }

    /**
     * Регистрирует нового сотрудника
     *
     * @param user сотрудник
     * @return возвращает идентификатор зарегистрированного сотрудника
     */
    @PostMapping
    public ResponseEntity<?> signUp(@Valid @RequestBody EmployeeEntity user) {
        long userId = userService.signUp(user);
        log.info("Регистрация пользователя {}", user);
        return ResponseEntity.created(URI.create("employee/" + userId)).build();
    }

    /**
     * Находит пользователя по идентификатору
     *
     * @param id Уникальный идентификатор пользователя
     * @return Возвращает пользователя с ограниченным количеством полей
     */
    @PreAuthorize("hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getUserById(@PathVariable long id) {
        log.info("Выводим данные о пользователе с id: {}", id);
        Optional<EmployeeEntity> user = userService.getUserById(id);
        if (user.isPresent()) {
            EmployeeDto employeeDto = new EmployeeDto(user.get().getId(), user.get().getUsername(),
                    user.get().getFio(),
                    user.get().getEmail());

            return ResponseEntity.ok().body(employeeDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
