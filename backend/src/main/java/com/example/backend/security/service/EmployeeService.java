package com.example.backend.security.service;

import com.example.backend.entity.EmployeeEntity;

import java.util.Optional;

public interface EmployeeService {
    /**
     * Регистрирует сотрудника
     *
     * @param employee регистрируемый сотрудник
     * @return id пользователя
     */
    long signUp(EmployeeEntity employee);

    /**
     * Ищет сотрудника по id
     *
     * @param employeeId id сотрудника
     * @return найденный сотрудник
     */
    Optional<EmployeeEntity> getUserById(long employeeId);
}
