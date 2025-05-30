package com.example.backend.repository;

import com.example.backend.entity.RoleEntity;
import com.example.backend.entity.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    /**
     * Получает роль по названию
     *
     * @param name название роли
     * @return найденная роль
     */
    Optional<RoleEntity> findByName(ERole name);
}

