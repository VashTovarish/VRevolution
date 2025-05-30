package com.example.backend.repository;

import com.example.backend.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    /**
     * Ищет всех сотрудников с данным логином
     *
     * @param username логин
     * @return сотрудника с таким логином
     */
    Optional<EmployeeEntity> findByUsername(String username);

    /**
     * Ищет сотрудника по электронной почте
     *
     * @param email электронная почта пользователя
     * @return сотрудник с заданной электронной почтой
     */
    Optional<EmployeeEntity> findByEmail(String email);

    /**
     * Проверяет сотрудник ли сотрудник с заданным логином
     *
     * @param username логин
     * @return true - пользователь существует, иначе false
     */
    Boolean existsByUsername(String username);

    /**
     * Проверяет сотрудник ли сотрудник с заданной электронная почта
     *
     * @param email электронная почта
     * @return true - пользователь существует, иначе false
     */
    Boolean existsByEmail(String email);
}
