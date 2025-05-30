package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Сотрудник с ограниченным количеством полей
 */
@Data
@AllArgsConstructor
public class EmployeeDto {
    private long id;
    private String username;
    private String fio;
    private String email;
}

